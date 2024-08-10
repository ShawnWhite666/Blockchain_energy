package com.blockchainenergy.common.redis.impl;

import com.alibaba.fastjson2.JSON;
import com.blockchainenergy.common.redis.RedisService;
import com.blockchainenergy.common.redis.info.ElecMeterInfo;
import com.blockchainenergy.common.redis.log.UserMeterLog;
import com.blockchainenergy.common.redis.log.UserWalletLog;
import com.blockchainenergy.mqtt.server.MqttParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class RedisImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public final String USERWALLET = "USERWALLET";
    public final String USERMETER = "USERMETER";

    public UserWalletLog getWalletLog(String userId) {
        UserWalletLog userWalletLog = JSON.parseObject(JSON.toJSONString(redisTemplate.boundHashOps(USERWALLET).get(userId)), UserWalletLog.class);
        if (userWalletLog == null) {
            userWalletLog = new UserWalletLog();
            redisTemplate.boundHashOps(USERWALLET).put(userId, userWalletLog);
        }
        return userWalletLog;
    }

    public void addWalletLog(String userId, double balance, double credit_point) {
        UserWalletLog userWalletLog = JSON.parseObject(JSON.toJSONString(redisTemplate.boundHashOps(USERWALLET).get(userId)), UserWalletLog.class);
        if (userWalletLog == null) userWalletLog = new UserWalletLog();
        userWalletLog.add(balance, credit_point);
        redisTemplate.boundHashOps(USERWALLET).put(userId, userWalletLog);
    }

    // 根据电表ID查找电表数据
    public UserMeterLog getMeterLog(String meterId) {
        return JSON.parseObject(JSON.toJSONString(redisTemplate.boundHashOps(USERMETER).get(meterId)), UserMeterLog.class);
    }

    // 根据硬件刷新内存
    public void addMeterLog(MqttParam mqttParam) {
        String elec_meter_id = mqttParam.getElec_meter_id();
        UserMeterLog meterLog = JSON.parseObject(JSON.toJSONString(redisTemplate.boundHashOps(USERMETER).get(elec_meter_id)), UserMeterLog.class);
        if (meterLog == null) meterLog = new UserMeterLog(elec_meter_id);
        meterLog.add(mqttParam);
        // 插入redis并设置过期时间
        redisTemplate.boundHashOps(USERMETER).put(elec_meter_id, meterLog);
        redisTemplate.boundHashOps(USERMETER).expire(1, TimeUnit.MINUTES);
    }

    // 管理员查看所有电表信息
    public List<ElecMeterInfo> getAllElecMeter() {
        List<ElecMeterInfo> list = new ArrayList<>();
        for (Object value : redisTemplate.boundHashOps(USERMETER).values()) {
            list.add(new ElecMeterInfo(JSON.parseObject(JSON.toJSONString(value), UserMeterLog.class)));
        }
        return list;
    }

    public List<UserMeterLog> getAllElecMeterLog() {
        List<UserMeterLog> list = new ArrayList<>();
        for (Object value : redisTemplate.boundHashOps(USERMETER).values()) {
            list.add(JSON.parseObject(JSON.toJSONString(value), UserMeterLog.class));
        }
        list.sort(Comparator.comparing(UserMeterLog::getMeterId));// 根据MeterID排序
        return list;
    }
}
