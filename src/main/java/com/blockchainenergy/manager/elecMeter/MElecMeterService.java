package com.blockchainenergy.manager.elecMeter;

import com.blockchainenergy.common.redis.info.ElecMeterInfo;
import com.blockchainenergy.common.redis.log.UserMeterLog;

import java.util.List;

public interface MElecMeterService {

    List<ElecMeterInfo> getAllElecMeterInfo();

    void deleteElecMeter(String elec_meter_id);

    List<UserMeterLog> getAllElecMeterLog();
}
