package com.blockchainenergy.manager.user;

import java.util.List;

public interface ManagerUserService {
    public List<ManageUserInfo> getUsersInfo();

    public UserBalanceDto getUserBalance(String user_id);
}
