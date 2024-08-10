package com.blockchainenergy.info.market;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerTradeInfo {
    private Integer user_num;
    private Integer match_trade_num;
    private Integer run_day;
}
