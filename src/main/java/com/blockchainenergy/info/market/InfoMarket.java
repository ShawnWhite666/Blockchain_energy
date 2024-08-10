package com.blockchainenergy.info.market;

import com.blockchainenergy.trade.tradeInfo.TradeSlotInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InfoMarket {
    public static List<Integer> tradingVolumes = new ArrayList<>();
    public static List<Double> maxPrices = new ArrayList<>();
    public static List<Double> minPrices = new ArrayList<>();
    public static List<Double> averagePrices = new ArrayList<>();

    static {
        for (int i = 0; i < 24; i++) {
            tradingVolumes.add(null);
            maxPrices.add(null);
            minPrices.add(null);
            averagePrices.add(null);
        }
    }

    public static void add(Integer timeSlot, int t, double ma, double mi, double av) {
        if (timeSlot == 0) clear();
        tradingVolumes.set(timeSlot, t);
        maxPrices.set(timeSlot, ma);
        minPrices.set(timeSlot, mi);
        averagePrices.set(timeSlot, av);
    }

    private static void clear() {
        for (int i = 0; i < 24; i++) {
            tradingVolumes.set(i, null);
            maxPrices.set(i, null);
            minPrices.set(i, null);
            averagePrices.set(i, null);
        }
    }

    public static TradeSlotInfo get() {
        return new TradeSlotInfo(tradingVolumes, maxPrices, minPrices, averagePrices);
    }
}
