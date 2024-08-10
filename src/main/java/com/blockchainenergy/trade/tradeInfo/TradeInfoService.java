package com.blockchainenergy.trade.tradeInfo;

import java.util.List;

/**
 * @descriptions:
 * @data: 2022/2/27 21:44
 */
public interface TradeInfoService {
    List<TradeInfo> getTrade(GetTradeDto dto);

    TradeSlotInfo getTimeSlot();

    TradeDetail getTradeDetail(TradeInfoDto dto);

    TradeSlotInfo getTimeSlotPersonByDate(TimeSlotDto dto);

    // 获取tradeInfo 参数：userId,isBuyer
    List<TradeEChart> getMyTradeEChart(String userId, boolean isBuyer);

    void split(String userId, int isSplit);

    void split1(String meterId, int isSplit);
}
