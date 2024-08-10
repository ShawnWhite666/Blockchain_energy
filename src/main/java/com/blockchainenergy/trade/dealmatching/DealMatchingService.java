package com.blockchainenergy.trade.dealmatching;

import com.blockchainenergy.trade.dealmatching.dao.TimeSlotInfoEntity;

import java.time.LocalDate;
import java.util.List;

public interface DealMatchingService {
    List<TimeSlotInfoEntity> getTimeSlotInfo(LocalDate date, Integer timeSlot);

    void executeMatching(LocalDate date, Integer hour);
}
