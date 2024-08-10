package com.blockchainenergy.trade.elecMeter;

import java.util.List;

/**
 * @descriptions:
 * @data: 2022/2/5 18:58
 */
public interface ElectMeterService {
    ElecMeterList getElecMeterLogPerson(String userId);

    List<ElecMeter> getAllElecMeter();
}
