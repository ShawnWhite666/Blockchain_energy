package com.blockchainenergy.common.redis.info;

import com.blockchainenergy.common.redis.log.UserMeterLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ElecMeterInfo {
    private String elec_meter_id;
    private Double EA;//总电量
    private Double EF;//正向电量
    private Double ER;//反向电量
    private Double V;//电压
    private Double A;//电流
    private Double PIA;//瞬时有功功率
    private Double PIR;//瞬时无功功率
    private Double PI;//顺时视在功率
    private Double PF;//功率因数

    public ElecMeterInfo(Map.Entry<String, UserMeterLog> meterEntry) {
        UserMeterLog userMeterLog = meterEntry.getValue();
        this.elec_meter_id = meterEntry.getKey();
        if (userMeterLog.getSize() > 0) {
            EA = userMeterLog.getEAs().getLast();
            EF = userMeterLog.getEFs().getLast();
            ER = userMeterLog.getERs().getLast();
            V = userMeterLog.getVs().getLast();
            A = userMeterLog.getAs().getLast();
            PIA = userMeterLog.getPIAs().getLast();
            PIR = userMeterLog.getPIRs().getLast();
            PI = userMeterLog.getPIs().getLast();
            PF = userMeterLog.getPFs().getLast();
        }
    }

    public ElecMeterInfo(UserMeterLog userMeterLog) {
        this.elec_meter_id = userMeterLog.getMeterId();
        if (userMeterLog.getSize() > 0) {
            EA = userMeterLog.getEAs().getLast();
            EF = userMeterLog.getEFs().getLast();
            ER = userMeterLog.getERs().getLast();
            V = userMeterLog.getVs().getLast();
            A = userMeterLog.getAs().getLast();
            PIA = userMeterLog.getPIAs().getLast();
            PIR = userMeterLog.getPIRs().getLast();
            PI = userMeterLog.getPIs().getLast();
            PF = userMeterLog.getPFs().getLast();
        }
    }
}
