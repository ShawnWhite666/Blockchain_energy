package com.blockchainenergy.manager.energy;

import java.util.List;

public interface ManagerEnergyService {
    public void createEnergy(EnergyInfo energyInfo);

    public List<EnergyInfoVO> getAllEnergyInfo();

    public void deleteEnergy(Integer energy_id);
}
