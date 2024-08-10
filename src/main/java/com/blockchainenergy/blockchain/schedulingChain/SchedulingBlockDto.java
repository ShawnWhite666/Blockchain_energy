package com.blockchainenergy.blockchain.schedulingChain;

import com.blockchainenergy.trade.dealResult.TradeInfo;
import lombok.Data;

@Data
public class SchedulingBlockDto extends TradeInfo {
    private int num;
    private int num_of_transaction;
    private String block_hash;
}
