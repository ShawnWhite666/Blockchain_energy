package com.blockchainenergy.info.user.dao;

import lombok.Data;

@Data
public class UserTimeSlotInfoEntity {
    private String release_id;

    private Integer time_slot;
    private Double elec_value;
    private Double elec_upper;
    private Double elec_lower;
}
