package com.blockchainenergy.manager.user;

import lombok.Data;

@Data
public class ManageUserInfo {
    private String user_id;
    private String name;
    private boolean sex;
    private Integer age;
    private String address;
    private String work_unit;
    private String email;
    private String phone_number;
    private String id_number;
    private String elec_meter_id;
    private Double balance;
    private Double credit_point;
}
