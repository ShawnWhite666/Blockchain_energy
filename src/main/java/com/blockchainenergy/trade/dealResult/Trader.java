package com.blockchainenergy.trade.dealResult;

import lombok.Data;

/**
 * @descriptions:
 * @data: 2022/1/31 22:26
 */
@Data
public class Trader {
    private String user_id;
    private Double credit_point;//信用积分初始为1 当低于0.5时禁止交易
    private Double balance;
    private Double remain_elec;
    private Integer buy_number;
    private Integer sell_number;
}
