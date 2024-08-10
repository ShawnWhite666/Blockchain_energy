package com.blockchainenergy.trade.dealmatching.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TimeSlotInfoEntity {
    private String release_id;
    private String user_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date release_time;

    private Integer time_slot;
    private Boolean trade_type;
    private Double elec_value;
    private Double elec_upper;
    private Double elec_lower;

    private Double total_elec;
    private Double credit_point;
    private Double price;

    private Double sum_history_elec;
    private Double sum_future_lower;
}
