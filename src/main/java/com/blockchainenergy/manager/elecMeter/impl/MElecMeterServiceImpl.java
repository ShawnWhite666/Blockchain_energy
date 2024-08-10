package com.blockchainenergy.manager.elecMeter.impl;

import com.blockchainenergy.common.redis.RedisService;
import com.blockchainenergy.common.redis.info.ElecMeterInfo;
import com.blockchainenergy.common.redis.log.UserMeterLog;
import com.blockchainenergy.manager.elecMeter.MElecMeterService;
import com.blockchainenergy.manager.elecMeter.dao.MElecMeterDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MElecMeterServiceImpl implements MElecMeterService {

    @Resource
    private MElecMeterDao MElecMeterDao;
    @Resource
    private RedisService redisService;

    // 管理员查看所有电表信息
    @Override
    public List<ElecMeterInfo> getAllElecMeterInfo() {
        return redisService.getAllElecMeter();
    }

    @Override
    public void deleteElecMeter(String elec_meter_id) {
        MElecMeterDao.delElecMeter(elec_meter_id);
    }

    @Override
    public List<UserMeterLog> getAllElecMeterLog() {
        return redisService.getAllElecMeterLog();
    }
}
