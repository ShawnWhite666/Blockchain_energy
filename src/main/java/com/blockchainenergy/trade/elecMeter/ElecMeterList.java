package com.blockchainenergy.trade.elecMeter;

import lombok.Data;

import java.util.List;

@Data
public class ElecMeterList {
    List<Double> Vs;// 电压
    List<Double> As;// 电流
    List<Double> EFs;// 正向电量
    List<Double> ERs;// 反向电量

    public ElecMeterList(List<Double> vs, List<Double> as, List<Double> EFs, List<Double> ERs) {
        Vs = vs;
        As = as;
        this.EFs = EFs;
        this.ERs = ERs;
    }
}
