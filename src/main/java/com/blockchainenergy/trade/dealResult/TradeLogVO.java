package com.blockchainenergy.trade.dealResult;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TradeLogVO {
    List<Integer> log_ids;
    List<Double> balances;
    List<Double> credit_points;
    List<Date> trade_times;

    public TradeLogVO(List<Integer> log_ids, List<Double> balances, List<Double> credit_points, List<Date> trade_times) {
        this.log_ids = log_ids;
        this.balances = balances;
        this.credit_points = credit_points;
        this.trade_times = trade_times;
    }
}
