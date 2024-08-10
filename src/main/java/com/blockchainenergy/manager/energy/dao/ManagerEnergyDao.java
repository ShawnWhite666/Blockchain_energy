package com.blockchainenergy.manager.energy.dao;

import com.blockchainenergy.manager.energy.EnergyInfo;
import com.blockchainenergy.manager.energy.EnergyInfoVO;

import java.util.List;

public interface ManagerEnergyDao {
    public void createEnergy(EnergyInfo energyInfo);
    public List<EnergyInfoVO> getAllEnergyInfo();
    public void deleteEnergy(Integer energy_id);
}
