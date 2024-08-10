package com.blockchainenergy.trade.tradeInfo.dao;

import com.blockchainenergy.trade.tradeInfo.*;

import java.util.List;

/**
 * @descriptions:
 * @data: 2022/2/27 22:08
 */
public interface TradeInfoDao {
    List<TradeInfo> getTrade(GetTradeDto dto);

    TradeDetail getTradeDetail(TradeInfoDto dto);

    String getMeterIdById(String user_id);

    Integer getTradingVolumesByTimePersonByDate(TimeSlotDto dto);

    Double getMaxPriceByTimePersonByDate(TimeSlotDto dto);

    Double getMinPriceByTimePersonByDate(TimeSlotDto dto);

    Double getAverageByTimePersonByDate(TimeSlotDto dto);

    List<TradeEChart> getTradeEChartByBuyerID(String userId);

    List<TradeEChart> getTradeEChartBySellerID(String userId);
}
