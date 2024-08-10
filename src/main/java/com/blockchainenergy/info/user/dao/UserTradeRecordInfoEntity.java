package com.blockchainenergy.info.user.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserTradeRecordInfoEntity {
    private String trade_id;
    private String buyer;
    private String seller;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date match_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trade_time;
    private Double price;
    private Double contract_elec;
    private Double output_elec;
    private Double use_elec;

    private Integer block_height;
    private String block_hash;
    private String block_pre_hash;

    private Integer default_id;
    private Integer trade_state_id;

    private String trade_state_name;
    private String default_info;
}
