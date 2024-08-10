package com.blockchainenergy.trade.dealResult;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 */
@Data
@AllArgsConstructor
public class DealResultDto {
    private String trade_id;//交易id
    private Double output_elec;//卖方实际出电量
    private Double use_elec;//买方实际用电量
}
