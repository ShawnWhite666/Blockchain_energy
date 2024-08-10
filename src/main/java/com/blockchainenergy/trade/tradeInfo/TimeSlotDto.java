package com.blockchainenergy.trade.tradeInfo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TimeSlotDto {
    private String user_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String trade_time;
    private Integer time_slot;
}
