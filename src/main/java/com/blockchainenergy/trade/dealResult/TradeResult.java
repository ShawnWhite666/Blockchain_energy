package com.blockchainenergy.trade.dealResult;

import lombok.Data;

/**
 * @descriptions: 更新数据库需要
 * @data: 2022/1/31 13:52
 */
@Data
public class TradeResult {
    private String buyer;
    private String seller;
    private Double sellerIncome;
    private Double buyerIncome;
    private Double powerIncome;

    // 交易信息
    private String trade_id;
    private Integer default_id;
    private Double contractElec;
    private Double output_elec;
    private Double use_elec;

    private Double buyerPoint;
    private Double sellerPoint;

    public TradeResult(String trade_id, Double use_elec, Double output_elec) {
        this.trade_id = trade_id;
        this.use_elec = use_elec;
        this.output_elec = output_elec;
    }
}
