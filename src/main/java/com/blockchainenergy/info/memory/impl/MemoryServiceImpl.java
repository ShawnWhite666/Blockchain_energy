package com.blockchainenergy.info.memory.impl;

import com.blockchainenergy.common.redis.RedisService;
import com.blockchainenergy.common.redis.info.ElecMeterInfo;
import com.blockchainenergy.common.redis.log.UserMeterLog;
import com.blockchainenergy.common.redis.log.UserWalletLog;
import com.blockchainenergy.info.memory.MemoryService;
import com.blockchainenergy.info.memory.dao.MemoryDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MemoryServiceImpl implements MemoryService {

    @Resource
    private MemoryDao memoryDao;

    @Resource
    private RedisService redisService;

    @Override
    public UserWalletLog getUserWalletLog(String userId) {
        return redisService.getWalletLog(userId);
    }

    @Override
    public UserMeterLog getUserMeterLog(String userId) {
        return redisService.getMeterLog(memoryDao.getMeterIdByUserId(userId));
    }

    @Override
    public List<ElecMeterInfo> getAllMeterLog() {
        return redisService.getAllElecMeter();
    }
}
