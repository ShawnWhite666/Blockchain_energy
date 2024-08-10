package com.blockchainenergy.common;


import com.blockchainenergy.common.model.Manager.Manager;
import com.blockchainenergy.common.model.User.User;

public interface CommonService {
    User getUserById(String userId);
    Manager getManagerById(String m_id);
}
