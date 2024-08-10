package com.blockchainenergy.trade.release;

import com.blockchainenergy.common.utils.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用于交易发布
 */
@Data
@NoArgsConstructor
public class TradeReleaseDto {
    // release_info
    private String user_id;
    private Integer input_type_id;
    private Integer trade_type;
    private Double total_elec;
    private Integer start_time;
    private Integer end_time;
    private Double price;
    private Date release_time = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private Integer trade_state_id = 0;

    private List<Double> elec_value;
    private List<Double> elec_upper;
    private List<Double> elec_lower;
    private String release_id = getHashString();    // 雪花算法

    public TradeReleaseDto(
            String user_id,
            Integer input_type_id,
            Integer trade_type,
            Double total_elec,
            Integer start_time,
            Integer end_time,
            Double price,
            Date date) {
        this.user_id = user_id;
        this.input_type_id = input_type_id;
        this.trade_type = trade_type;
        this.total_elec = total_elec;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
        this.date = date;
    }

    private static long count = 0;

    public String getHashString() {
        count++;
        return Utils.generateHashCode(user_id + release_time + date + count);
    }
}
