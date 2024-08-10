package com.blockchainenergy.trade.dealResult;

import java.util.List;

/**
 * 处理违约机制
 */
public interface TradeService {
    TradeResult sellerDefault(TradeResult tradeResult, TradingParameter tradingParameter);//卖方违约

    TradeResult buyerDefault(TradeResult tradeResult, TradingParameter tradingParameter);//买方违约

    TradeResult bothDefault(TradeResult tradeResult, TradingParameter tradingParameter);//双方违约

    TradeResult noDefault(TradeResult tradeResult, TradingParameter tradingParameter);

    Trader getTraderInfo(String user_id);

    List<TradeResult> dealResult();
}
