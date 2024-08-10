package com.blockchainenergy.trade.dealResult.dao;

import com.blockchainenergy.trade.dealResult.TradeInfo;
import com.blockchainenergy.trade.dealResult.TradeResult;
import com.blockchainenergy.trade.dealResult.Trader;

import java.util.List;

public interface DealResultDao {
    Trader getTraderInfo(String user_id);

    void updateBuyer(String buyerUser_id, Double newCreditPoint, Double buyerIncome);

    void updateSeller(String sellerUserId, Double newCreditPoint, Double sellerIncome);

    void updatePower(String electricGridId, Double powerIncome);

    void updateTradeRecord(TradeResult tradeResult);


    List<TradeInfo> getMatchedTrade();
}
