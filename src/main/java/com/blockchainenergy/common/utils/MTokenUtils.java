package com.blockchainenergy.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blockchainenergy.common.CommonConfig;
import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.model.Manager.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

public class MTokenUtils {

    private static final Logger log = LoggerFactory.getLogger(MTokenUtils.class);

    public static final int DEFAULT_EXPIRED_TIME = 10*1000;//毫秒

    public static final int EXPIRED_TIME = getExpiredTime();

    public static final String CLAIM_USERID_NAME = getClaimUseridName();

    private static String getClaimUseridName() {
        String claimUseridName = CommonConfig.getEnv().getProperty("sure.token.calim-userid-name");
        if(claimUseridName==null){
            return "UserID";
        }
        return claimUseridName;


    }

    private static int getExpiredTime(){
        String time = CommonConfig.getEnv().getProperty("sure.token.expired-time");
        if(time==null){
            return DEFAULT_EXPIRED_TIME;
        }
        try {
            return Integer.parseInt(time);
        } catch (Exception e) {
            e.printStackTrace();
            return DEFAULT_EXPIRED_TIME;
        }
    }




    private static class TokenCache{

        private LinkedHashMap<String,Long> expiredMap = new LinkedHashMap<>();
        private Map<String,String> tokenMap = new HashMap<>();

        private TokenCache(){
            Thread task = new Thread(){
                @Override
                public void run() {
                    for(;;){
                        try {
                            Thread.sleep(EXPIRED_TIME/10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("开始清理。。。");
                        clearExpiredToken(expiredMap.size()/5+1);
                        //System.out.println("清理结束。。。");
                    }

                }
            };
            task.setDaemon(true);
            task.start();
        }

        /**
         * 放入token
         * @param key
         * @param token
         */
        private synchronized void putToken(String key,String token){
            tokenMap.put(key,token);
            expiredMap.put(key,System.currentTimeMillis()+EXPIRED_TIME);
        }

        /**
         * 获取token
         * @param key
         * @return
         */
        private synchronized String getToken(String key){
            return tokenMap.get(key);
        }

        /**
         * 移除token
         * @param key
         */
        private synchronized void removeToekn(String key){
            tokenMap.remove(key);
            expiredMap.remove(key);
        }

        /**
         * 清除过期token
         */
        private synchronized void clearExpiredToken(int n){

            int cnt = 0;

            long now = System.currentTimeMillis();
            for(Iterator<Map.Entry<String,Long>> itr = expiredMap.entrySet().iterator();cnt <= n && itr.hasNext();cnt++){
                Map.Entry<String,Long> entry = itr.next();
                if(now > entry.getValue()){
                    String userId = JWT.decode(entry.getKey()).getClaim(CLAIM_USERID_NAME).asString();
                    itr.remove();
                    tokenMap.remove(entry.getKey());
                    log.info("用户"+userId+"下线！");
                }

            }
        }
    }


    private static TokenCache cache = new TokenCache();


    private static String sign(String userId,String password){
        Algorithm algorithm = Algorithm.HMAC256(password);
        String token = JWT.create()
                .withClaim(CLAIM_USERID_NAME,userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRED_TIME/2))
                .sign(algorithm);
        return token;
    }

    /**
     * 生成一个登录用户签名token
     * @param userId
     * @param password
     * @return
     */
    public static String loginSign(String userId,String password){
        String token = sign(userId,password);
        cache.putToken(token,token);
        return token;
    }

    /**
     * 获取用户信息
     * @param clientToken
     * @param commonService
     * @return
     */
    public static CurrentUser getUserInfo(String clientToken, CommonService commonService){
        DecodedJWT decodedJWT = JWT.decode(clientToken);
        String userId = decodedJWT.getClaim(CLAIM_USERID_NAME).asString();
        Manager manager = commonService.getManagerById(userId);
        return new CurrentUser(manager.getUserId(),manager.getUserPwd());
    }

    public static void removeToken(String clientToken){
        if(clientToken!=null){
            cache.removeToekn(clientToken);
        }
    }

    /**
     * 验证客户端传来token是否有效
     * 验证逻辑顺序如下：
     * 1. token是否为空
     * 2. token中账号是否存在
     * 3. 根据token中账号从数据库中获取真实密码等用户信息，并验证用户信息是否有效
     * 4.
     */
    public static void verifyToken(String clientToken,CommonService commonService){
        if(!StringUtils.hasText(clientToken)){
            //token为空
            throw new RuntimeException("无登录令牌！");
        }

        //从客户端登录令牌中获取当前用户账号
        String userId = JWT.decode(clientToken).getClaim(CLAIM_USERID_NAME).asString();
        if(!StringUtils.hasText(userId)){
            //token中账号不存在
            throw new RuntimeException("登录令牌失效！");
        }

        //取出缓存中的登录令牌
        String cacheToken = cache.getToken(clientToken);
        if(!StringUtils.hasText(cacheToken)){
            //缓存中没有登录令牌
            throw new RuntimeException("登录令牌失效！");
        }

        Manager user = commonService.getManagerById(userId);
        if(user==null){
            //用户不存在
            throw new RuntimeException("用户不存在！");
        }

        //验证Token有效性
        try{
            Algorithm algorithm = Algorithm.HMAC256(user.getUserPwd());
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim(CLAIM_USERID_NAME,userId).build();//构建验证器
            jwtVerifier.verify(cacheToken);
        }catch(TokenExpiredException e){
            //令牌过期，刷新令牌
            String newToken = sign(userId,user.getUserPwd());
            cache.putToken(clientToken,newToken);
        }catch(Exception e){
            e.printStackTrace();
            //令牌验证未通过
            throw new RuntimeException("令牌错误！请登录。");
        }
    }









}
