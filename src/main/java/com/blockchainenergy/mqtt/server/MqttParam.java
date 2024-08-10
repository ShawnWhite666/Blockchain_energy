package com.blockchainenergy.mqtt.server;

import lombok.Data;

import java.time.LocalDate;

/**
 * @descriptions: 获得的数据都在这个地方
 */
@Data
public class MqttParam {
    private String elec_meter_id;//设备名

    private Double EA;//总电量
    private Double ER;//反向电量
    private Double EF;//正向电量
    private Double V;//电压
    private Double A;//电流
    private Double PIA;//瞬时有功功率
    private Double PIR;//瞬时无功功率
    private Double PI;//顺时视在功率
    private Double PF;//功率因数
    private Integer ST;// 开 1 关 0
    private LocalDate time = LocalDate.now();
}
