package com.blockchainenergy.info.market;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTradeInfo {
    private Integer total_num;
    private Integer match_num;
    private Integer no_match_num;
    private Long trade_amount;

    public UserTradeInfo(Integer total_num, Integer match_num, Long trade_amount) {
        this.total_num = total_num;
        this.match_num = match_num;
        this.no_match_num = total_num - match_num;
        this.trade_amount = trade_amount;
    }
}
