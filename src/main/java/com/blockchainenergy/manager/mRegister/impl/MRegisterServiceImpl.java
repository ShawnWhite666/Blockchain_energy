package com.blockchainenergy.manager.mRegister.impl;

import com.blockchainenergy.manager.mRegister.MRegisterDto;
import com.blockchainenergy.manager.mRegister.MRegisterService;
import com.blockchainenergy.manager.mRegister.dao.MRegisterDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @descriptions:
 * @data: 2021/10/24 10:43
 */
@Service
@Transactional
public class MRegisterServiceImpl implements MRegisterService {

    @Resource
    private MRegisterDao mRegisterDao;

    @Override
    public boolean addUser(MRegisterDto dto) {
        mRegisterDao.insertManager(dto);
        return true;
    }

    @Override
    public boolean checkId(String id) {
        return mRegisterDao.checkId(id);
    }
}
