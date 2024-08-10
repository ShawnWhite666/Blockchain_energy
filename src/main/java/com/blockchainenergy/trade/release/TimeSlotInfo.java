package com.blockchainenergy.trade.release;

import lombok.Data;

/**
 * @descriptions:
 * @data: 2022/2/5 19:50
 */
@Data
public class TimeSlotInfo {
    private Integer time_slot;
    private String release_id;
    private Double elec_value;
    private Double elec_upper;
    private Double elec_lower;
}
