package com.blockchainenergy.manager.energy;

import lombok.Data;

@Data
public class EnergyInfoVO {
    private Integer energy_id;
    private String energy_name;
    private String energy_unit;
    private String publisher;
    private Integer energy_acc;
}
