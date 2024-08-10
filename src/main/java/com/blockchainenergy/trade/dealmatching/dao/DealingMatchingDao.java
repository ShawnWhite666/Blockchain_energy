package com.blockchainenergy.trade.dealmatching.dao;

import java.time.LocalDate;
import java.util.List;

public interface DealingMatchingDao {

    List<TimeSlotInfoEntity> getTimeSlotInfoByTimeSlot(Integer timeSlot, LocalDate date);

    Double getBuyerHistoryElec(String release_id, Integer time_slot);

    Double getSellerHistoryElec(String release_id, Integer time_slot);

    Double getFutureLower(String release_id, Integer time_slot);

    void insertTradeRecord(TradeRecordEntity entity);

    void updateReleaseStateToMatched(String release_id);
}
