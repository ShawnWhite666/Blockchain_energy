package com.blockchainenergy.manager.system.impl;

import com.blockchainenergy.manager.system.SystemEntry;
import com.blockchainenergy.manager.system.SystemService;
import com.blockchainenergy.manager.system.dao.SystemDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @descriptions:
 */
@Service
@Transactional
public class SystemServiceImpl implements SystemService {
    @Resource
    private SystemDao systemDao;

    @Override
    public SystemEntry getState() {
        SystemEntry systemEntry = new SystemEntry();
        systemEntry.setMoney_state(systemDao.getMoneyState());
        return systemEntry;
    }

    @Override
    public void setMoneyState() {
        systemDao.setMoneyState();
    }
}
