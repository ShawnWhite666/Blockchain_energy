package com.blockchainenergy.common.redis.log;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Data
public class UserWalletLog implements Serializable {
    int n = 24;// 容量
    LinkedList<Double> balances;
    LinkedList<Double> credit_points;

    public UserWalletLog() {
        balances = new LinkedList<>();
        credit_points = new LinkedList<>();
    }

    public void add(Double b, Double c) {
        int cur = balances.size();
        balances.add(b);
        credit_points.add(c);
        if (cur >= n) {
            balances.removeFirst();
            credit_points.removeFirst();
        }
    }
}
