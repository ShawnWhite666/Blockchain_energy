package com.blockchainenergy.manager.mLogin.impl;

import com.blockchainenergy.manager.mLogin.MLoginDto;
import com.blockchainenergy.manager.mLogin.MLoginService;
import com.blockchainenergy.manager.mLogin.dao.MLoginDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @descriptions:
 * @data: 2021/10/23 12:40
 */
@Service
@Transactional
public class MLoginServiceImpl implements MLoginService {
    @Resource
    private MLoginDao mLoginDao;

    @Override
    public int checkValid(MLoginDto dto) {
        // 查账号是否存在
        if (mLoginDao.CountPublicKey(dto) == 0) {
            System.out.println("-1");
            return -1;
        }
        // 查账号密码是否匹配
        if (mLoginDao.CheckPassword(dto) == 0) {
            System.out.println("-2");
            return -2;
        }
        return 0;
    }
}
