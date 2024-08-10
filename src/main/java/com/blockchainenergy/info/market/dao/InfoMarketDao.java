package com.blockchainenergy.info.market.dao;

/**
 * @descriptions:
 */
public interface InfoMarketDao {
    Integer getTradingVolumesByTime(Integer timeSlot);

    Double getMaxPriceByTime(Integer timeSlot);

    Double getMinPriceByTime(Integer timeSlot);

    Double getAverageByTime(Integer timeSlot);

    Integer getTotalTradeNum();

    Integer getMatchTradeNum();

    Long getTradeAmount();

    Integer getUserNum();
}
