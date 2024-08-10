package com.blockchainenergy.manager.user.impl;

import com.blockchainenergy.manager.user.ManageUserInfo;
import com.blockchainenergy.manager.user.ManagerUserService;
import com.blockchainenergy.manager.user.UserBalanceDto;
import com.blockchainenergy.manager.user.dao.ManageUserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ManageUserServiceImpl implements ManagerUserService {

    @Resource
    private ManageUserDao manageUserDao;

    @Override
    public List<ManageUserInfo> getUsersInfo() {
        return manageUserDao.getAllUsersInfo();
    }

    @Override
    public UserBalanceDto getUserBalance(String user_id) {
        return manageUserDao.getUserBalanceByUserId(user_id);
    }
}
