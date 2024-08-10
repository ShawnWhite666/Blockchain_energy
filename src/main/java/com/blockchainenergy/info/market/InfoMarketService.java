package com.blockchainenergy.info.market;

import java.text.ParseException;

public interface InfoMarketService {
    void refreshMarketInfo(Integer timeSlot);

    UserTradeInfo getUserTradeInfo();

    ManagerTradeInfo getManagerTradeInfo() throws ParseException;
}
