package com.blockchainenergy.trade.elecMeter;

import lombok.Data;

import java.util.Date;

@Data
public class ElecMeter {
    private String elec_meter_id;
    private Double EA;
    private Double EF; // 正向电量
    private Double ER; // 反向电量
    private Double V;
    private Double A;
    private Double PIA;
    private Double PIR;
    private Double PI;
    private Double PF;
    private Date time;
}
