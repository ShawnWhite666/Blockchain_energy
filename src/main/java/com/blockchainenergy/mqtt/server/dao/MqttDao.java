package com.blockchainenergy.mqtt.server.dao;

import com.blockchainenergy.mqtt.server.MqttParam;

/**
 * @descriptions:
 * @data: 2021/10/23 14:20
 */
public interface MqttDao {
    void insertElectricityMeterLog(MqttParam mqttParam);

    void updateElectricityMeterInfoPersonal(MqttParam mqttParam);
}
