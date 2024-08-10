package com.blockchainenergy.manager.energy.impl;

import com.blockchainenergy.manager.energy.EnergyInfo;
import com.blockchainenergy.manager.energy.EnergyInfoVO;
import com.blockchainenergy.manager.energy.ManagerEnergyService;
import com.blockchainenergy.manager.energy.dao.ManagerEnergyDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ManagerEnergyServiceImpl implements ManagerEnergyService {

    @Resource
    private ManagerEnergyDao managerEnergyDao;

    @Override
    public void createEnergy(EnergyInfo energyInfo) {
        managerEnergyDao.createEnergy(energyInfo);
    }

    @Override
    public List<EnergyInfoVO> getAllEnergyInfo() {
        return managerEnergyDao.getAllEnergyInfo();
    }

    @Override
    public void deleteEnergy(Integer energy_id) {
        managerEnergyDao.deleteEnergy(energy_id);
    }
}
