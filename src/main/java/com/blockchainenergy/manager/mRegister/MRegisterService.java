package com.blockchainenergy.manager.mRegister;

/**
 * @descriptions:
 * @data: 2021/10/24 10:38
 */
public interface MRegisterService {
    boolean addUser(MRegisterDto dto);

    boolean checkId(String id);
}
