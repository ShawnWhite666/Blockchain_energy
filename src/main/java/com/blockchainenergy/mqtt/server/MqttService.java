package com.blockchainenergy.mqtt.server;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:10
 */
public interface MqttService {
    void addMeterLog();

    void updateAllElectricityMeterInfo();

    List<String> getElecIDs();
}
