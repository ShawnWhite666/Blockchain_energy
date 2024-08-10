package com.blockchainenergy.trade.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @descriptions:
 * @data: 2022/2/5 20:31
 */
@Data
public class ReleaseInfo {
    private String release_id;
    private String user_id;
    private Integer input_type_id;
    private Double total_elec;
    private Integer start_time;
    private Integer end_time;
    private Double price;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date release_time = new Date();
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date date;
    private Integer release_state_id;
    private Integer trade_type;
    private String release_state;
    private String trade_type_name;
}
