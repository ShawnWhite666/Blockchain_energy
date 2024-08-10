package com.blockchainenergy.common.redis;

import com.blockchainenergy.common.redis.info.ElecMeterInfo;
import com.blockchainenergy.common.redis.log.UserMeterLog;
import com.blockchainenergy.common.redis.log.UserWalletLog;
import com.blockchainenergy.mqtt.server.MqttParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisService {
    UserWalletLog getWalletLog(String userId);

    void addWalletLog(String userId, double balance, double credit_point);

    // 根据电表ID查找电表数据
    UserMeterLog getMeterLog(String meterId);

    // 根据硬件刷新内存
    void addMeterLog(MqttParam mqttParam);

    // 管理员查看所有电表信息
    List<ElecMeterInfo> getAllElecMeter();

    List<UserMeterLog> getAllElecMeterLog();
}
