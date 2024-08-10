package com.blockchainenergy.trade.dealResult;

import lombok.Data;

import java.util.Date;

@Data
public class TradeLog {
    Integer log_id;
    String user_id;
    Double credit_point;
    Double balance;
    Date trade_time;
}
