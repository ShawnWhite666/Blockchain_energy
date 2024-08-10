package com.blockchainenergy.info.memory;

import com.blockchainenergy.common.redis.info.ElecMeterInfo;
import com.blockchainenergy.common.redis.log.UserMeterLog;
import com.blockchainenergy.common.redis.log.UserWalletLog;

import java.util.List;

public interface MemoryService {
    UserWalletLog getUserWalletLog(String userId);

    UserMeterLog getUserMeterLog(String userId);

    List<ElecMeterInfo> getAllMeterLog();
}
