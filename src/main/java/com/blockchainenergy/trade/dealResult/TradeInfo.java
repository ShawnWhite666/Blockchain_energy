package com.blockchainenergy.trade.dealResult;

import lombok.Data;

import java.util.Date;

/**
 * @descriptions:
 * @data: 2022/2/4 18:15
 */
@Data
public class TradeInfo {
    private String trade_id;
    private String buyer;
    private String seller;
    private Date trade_time;//生成合约的时间 暂时无用
    private Double price;
    private Double contract_elec;//合约电量
    private Integer time_slot;
    //暂时不清楚用途的两个参数
    private String buyer_release_id;
    private String seller_release_id;
}
