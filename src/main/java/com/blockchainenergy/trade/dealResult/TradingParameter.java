package com.blockchainenergy.trade.dealResult;

import lombok.Data;

/**
 * 违约处理需要的参数
 */
@Data
public class TradingParameter {
    private Double eQuantity;//合约电量
    private Double piPrice;//原合约电价

    private Double mQuantity;//实际交割量(两个电表变化量的小值)

    private Double hQuantity;

    private Double ckb;//买方信用积分
    private Double cks;//卖方信用积分

    public TradingParameter(Double eQuantity, Double piPrice, Double use_elec, Double output_elec, Double ckb, Double cks) {
        this.eQuantity = eQuantity;
        this.piPrice = piPrice;
        this.mQuantity = Math.min(use_elec, output_elec);
        this.hQuantity = eQuantity - mQuantity;
        this.ckb = ckb;
        this.cks = cks;
    }
}
