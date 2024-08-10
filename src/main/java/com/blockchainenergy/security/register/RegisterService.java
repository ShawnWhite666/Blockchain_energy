package com.blockchainenergy.security.register;

/**
 * @descriptions:
 * @data: 2021/10/24 10:38
 */
public interface RegisterService {
    RegisterParam addUser(RegisterDto dto);

    boolean checkPhone(String phone_number);

    boolean checkIdNumber(String id_number);


}
