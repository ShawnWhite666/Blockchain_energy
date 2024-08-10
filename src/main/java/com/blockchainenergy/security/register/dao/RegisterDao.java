package com.blockchainenergy.security.register.dao;

import com.blockchainenergy.security.register.RegisterDto;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/24 10:44
 */
public interface RegisterDao {
    void insertUserInfo(RegisterDto dto);

    void insertUser(RegisterDto dto);

    //@Select("select count(user_id) from user where user_id = #{publicKeyStr}")
    int countUser_id(String publicKeyStr);

    //@Select("SELECT IFNULL((SELECT true from user_info where phone_number = #{phone_number} limit 1),false)")
    boolean checkPhone(String phone_number);

    //@Select("SELECT IFNULL((SELECT true from user_info where id_number = #{id_number} limit 1),false)")
    boolean checkIdNumber(String id_number);

    //@Select("SELECT IFNULL((SELECT true from user_info where id_number = #{id_number} limit 1),false)")
    boolean checkMoneyEntry();

    List<String> getElecMeterIds();
}
