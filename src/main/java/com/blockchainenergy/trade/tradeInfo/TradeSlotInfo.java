package com.blockchainenergy.trade.tradeInfo;
import lombok.Data;

import java.util.List;
/**
 * @descriptions:
 */
@Data
public class TradeSlotInfo {
    private List<Integer> tradingVolumes;
    private List<Double> maxPrices;
    private List<Double> minPrices;
    private List<Double> averagePrices;

    public TradeSlotInfo(List<Integer> tradingVolumes, List<Double> maxPrices, List<Double> minPrices, List<Double> averagePrices) {
        this.tradingVolumes = tradingVolumes;
        this.maxPrices = maxPrices;
        this.minPrices = minPrices;
        this.averagePrices = averagePrices;
    }
}
