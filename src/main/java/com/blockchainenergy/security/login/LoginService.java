package com.blockchainenergy.security.login;

/**
 * @descriptions:
 * @data: 2021/10/23 12:35
 */
public interface LoginService {
    int checkValid(LoginDto dto);

    /**
     * 放除了Token以外的前端储存信息的
     * @param public_key
     * @return
     */
    Login getLogin(String public_key);
}
