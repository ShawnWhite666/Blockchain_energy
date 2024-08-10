package com.blockchainenergy.mqtt.server.impl;

import com.blockchainenergy.common.redis.RedisService;
import com.blockchainenergy.mqtt.IoTDemoPubSubDemo;
import com.blockchainenergy.mqtt.server.MqttParam;
import com.blockchainenergy.mqtt.server.MqttService;
import com.blockchainenergy.mqtt.server.dao.MqttDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @descriptions:
 * @data: 2021/10/23 14:19
 */

@Service
@Transactional
public class MqttServiceImpl implements MqttService {
    @Resource
    private MqttDao mqttDao;
    @Resource
    private RedisService redisService;

    @Override
    public void addMeterLog() {
        Map<String, MqttParam> mqttMap = IoTDemoPubSubDemo.getMqttMap();
        for (MqttParam mqttParam : mqttMap.values()) {
            redisService.addMeterLog(mqttParam);
        }
    }

    @Override
    public void updateAllElectricityMeterInfo() {
        Map<String, MqttParam> mqttMap = IoTDemoPubSubDemo.getMqttMap();
        for (MqttParam mqttParam : mqttMap.values()) {
            mqttDao.updateElectricityMeterInfoPersonal(mqttParam);
        }
    }

    // 获取内存中的elecId
    @Override
    public List<String> getElecIDs() {
        Map<String, MqttParam> mqttMap = IoTDemoPubSubDemo.getMqttMap();
        List<String> elecIds = new ArrayList<>(mqttMap.keySet());
        Collections.sort(elecIds);
        return elecIds;
    }
}
