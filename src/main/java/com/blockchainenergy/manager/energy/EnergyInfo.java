package com.blockchainenergy.manager.energy;

import lombok.Data;

@Data
public class EnergyInfo {
    private String energy_name;
    private String energy_unit;
    private String publisher;
    private Integer energy_acc;
}
