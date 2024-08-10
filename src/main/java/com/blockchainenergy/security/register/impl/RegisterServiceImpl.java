package com.blockchainenergy.security.register.impl;

import com.blockchainenergy.common.redis.RedisService;
import com.blockchainenergy.security.register.RegisterDto;
import com.blockchainenergy.security.register.RegisterParam;
import com.blockchainenergy.security.register.RegisterService;
import com.blockchainenergy.security.register.dao.RegisterDao;
import com.blockchainenergy.security.rsa.RSAEncrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RedisService redisService;

    @Resource
    private RegisterDao registerDao;

    @Override
    public RegisterParam addUser(RegisterDto dto) {
        //         该类用于生成公钥私钥，并且验证公钥私钥是否匹配
        //         使用方法：
        //         1.生成公钥私钥： 使用new RSAEncrypt()创建对象，之后调用get方法即可获取生成的公钥私钥（字符串）
        //         2.验证公钥私钥是否匹配： 调用静态方法verifyPublicAndPrivateKey()，传入公钥私钥字符串，返回boolean判断是否匹配
        RSAEncrypt rsaEncrypt = new RSAEncrypt();
        RegisterParam registerParam = new RegisterParam();
        String publicKeyStr = rsaEncrypt.getPublicKeyStr();
        while (registerDao.countUser_id(publicKeyStr) > 0) {
            // 和数据库中重复
            registerParam = new RegisterParam();
            publicKeyStr = rsaEncrypt.getPublicKeyStr();
        }
        registerParam.setPublic_key(publicKeyStr);
        registerParam.setPrivate_key(rsaEncrypt.getPrivateKeyStr());
        dto.setUser_id(publicKeyStr);
        if (registerDao.checkMoneyEntry()) {
            dto.setBalance(5.0);
        }
        // 更新数据库
        registerDao.insertUser(dto);
        registerDao.insertUserInfo(dto);

        redisService.addWalletLog(dto.getUser_id(), dto.getBalance(), 1);
        return registerParam;
    }

    @Override
    public boolean checkPhone(String phone_number) {
        return registerDao.checkPhone(phone_number);
    }

    @Override
    public boolean checkIdNumber(String id_number) {
        return registerDao.checkIdNumber(id_number);
    }
}
