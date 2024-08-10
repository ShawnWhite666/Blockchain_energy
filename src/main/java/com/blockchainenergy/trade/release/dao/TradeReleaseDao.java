package com.blockchainenergy.trade.release.dao;

import com.blockchainenergy.trade.release.GetReleaseDto;
import com.blockchainenergy.trade.release.ReleaseInfo;
import com.blockchainenergy.trade.release.TimeSlotInfo;
import com.blockchainenergy.trade.release.TradeReleaseDto;

import java.util.List;

public interface TradeReleaseDao {
    void insertNewRelease(TradeReleaseDto dto);

    void insertNewTimeSlot(TimeSlotInfo timeSlotInfo);

    List<ReleaseInfo> getReleaseDeal(GetReleaseDto dto);

    void deleteReleaseInfoByReleaseId(String release_id);
}
