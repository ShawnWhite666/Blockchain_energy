package com.blockchainenergy.trade.elecMeter;

import lombok.Data;

@Data
public class ElecMeterLog {
    private Integer log_id;
    private Double EF;
    private Double ER;
    private Double V;
    private Double A;
}
