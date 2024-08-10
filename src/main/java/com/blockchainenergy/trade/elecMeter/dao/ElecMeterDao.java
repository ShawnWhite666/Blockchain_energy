package com.blockchainenergy.trade.elecMeter.dao;

import com.blockchainenergy.trade.elecMeter.ElecMeter;
import com.blockchainenergy.trade.elecMeter.ElecMeterLog;

import java.util.List;

/**
 * @descriptions:
 * @data: 2022/2/5 18:59
 */
public interface ElecMeterDao {
    String getElecMeterIdByUserId(String user_id);

    List<ElecMeterLog> getElecMeterLogByMeterId(String user_id);

    List<ElecMeter> getAllElecMeter();
}
