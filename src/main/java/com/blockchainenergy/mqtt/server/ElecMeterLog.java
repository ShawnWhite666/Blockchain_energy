package com.blockchainenergy.mqtt.server;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ElecMeterLog {
    private String elec_meter_id;// 电表ID
    private Double EA;// 总电量
    private Double ER;// 反向电量
    private Double EF;// 正向电量
    private Double V;// 电压
    private Double A;// 电流
    private Double PIA;// 瞬时有功功率
    private LocalDate time;
    private Integer hour;

    public ElecMeterLog(MqttParam mqttParam, Integer hour) {
        this.elec_meter_id = mqttParam.getElec_meter_id();
        this.EA = mqttParam.getEA();
        this.ER = mqttParam.getER();
        this.EF = mqttParam.getEF();
        V = mqttParam.getV();
        A = mqttParam.getA();
        this.PIA = mqttParam.getPIA();
        this.time = LocalDate.now();
        this.hour = hour;
    }
}
