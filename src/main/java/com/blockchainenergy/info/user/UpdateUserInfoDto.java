package com.blockchainenergy.info.user;

import lombok.Data;

@Data
public class UpdateUserInfoDto {
    private String user_id;
    private String address;
    private String work_unit;
    private String phone_number;
    private String email;
    private String user_desc;
    private String elec_meter_id;
}
