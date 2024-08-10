package com.blockchainenergy.security.register;

import lombok.Data;

/**
 * @descriptions:
 */
@Data
public class RegisterDto {
    private String user_id;
    private Double balance;
    //    个人信息
    private String name;
    private Boolean sex;
    private Integer age;
    private String address;
    private String work_unit;
    private String email;
    private String phone_number;
    private String id_number;
    private String elec_meter_id;
    private String user_desc;
}
