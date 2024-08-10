package com.blockchainenergy.security.login.impl;

import com.blockchainenergy.security.login.Login;
import com.blockchainenergy.security.login.LoginDto;
import com.blockchainenergy.security.login.LoginService;
import com.blockchainenergy.security.login.dao.LoginDao;
import com.blockchainenergy.security.rsa.RSAEncrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @descriptions:
 * @data: 2021/10/23 12:40
 */
@Service @Transactional
public class LoginServiceImpl implements LoginService {
    @Resource
    private LoginDao loginDao;

    @Override
    public int checkValid(LoginDto dto) {
        // 查公钥是否存在
        System.out.println(dto.getPublic_key());
        if(loginDao.countPublicKey(dto)==0){
            return -1;
        }
        // 检查公私钥是否匹配
        if(!RSAEncrypt.verifyPublicAndPrivateKey(dto.getPublic_key(),dto.getPrivate_key())){
            return -2;
        }
        return 0;
    }

    @Override
    public Login getLogin(String public_key) {
        Login ans = new Login();
        ans.setName(loginDao.getName(public_key));
        return ans;
    }
}
