package com.blockchainenergy.security.login.dao;

import com.blockchainenergy.security.login.LoginDto;
import org.apache.ibatis.annotations.Select;

/**
 * @descriptions:
 * @data: 2021/10/23 12:42
 */
public interface LoginDao {
    int countPublicKey(LoginDto dto);
    String getName(String public_key);
}
