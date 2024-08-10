package com.blockchainenergy.manager.elecMeter.dao;

import com.blockchainenergy.common.redis.info.ElecMeterInfo;

import java.util.List;

public interface MElecMeterDao {

    List<ElecMeterInfo> getAllElecMeter();

    void delElecMeter(String elec_meter_id);
}
