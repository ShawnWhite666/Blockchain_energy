package com.blockchainenergy.trade.tradeInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @descriptions:
 */
@Data
public class TradeDetail {
    private String trade_id;
    private String buyer;
    private String seller;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss",timezone="GMT+8")
    private Date trade_time;
    private Double price;
    private Double contract_elec;
    private Double output_elec;
    private Double use_elec;
    private Integer default_id;

    private Integer block_height;
    private String block_hash;
    private String block_pre_hash;
    private String nonce;
    private String difficulty;

    private String buyer_elec_meter_id;
    private String seller_elec_meter_id;
}
