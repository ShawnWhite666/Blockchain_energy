package com.blockchainenergy.manager.user.dao;

import com.blockchainenergy.manager.user.ManageUserInfo;
import com.blockchainenergy.manager.user.UserBalanceDto;

import java.util.List;

public interface ManageUserDao {
    public List<ManageUserInfo> getAllUsersInfo();

    public UserBalanceDto getUserBalanceByUserId(String user_id);
}
