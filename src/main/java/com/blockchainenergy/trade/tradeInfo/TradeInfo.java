package com.blockchainenergy.trade.tradeInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class TradeInfo {
    private String trade_id;
    private String buyer;
    private String seller;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date trade_time;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date match_time;
    private Double price;
    private Double contract_elec;
    private Double output_elec;
    private Double use_elec;
    private Integer default_id;
    private String default_info;
}
