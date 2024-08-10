package com.blockchainenergy.trade.release;

import java.util.List;

public interface TradeReleaseService {
    void insertNewTrade(TradeReleaseDto dto);

    List<ReleaseInfo> getReleaseDeal(GetReleaseDto dto);

    void deleteRelease(String release_id);
}
