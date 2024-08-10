package com.blockchainenergy.blockchain.dataChain;

import com.blockchainenergy.trade.dealResult.TradeInfo;
import lombok.Data;

@Data
public class DataBlockDto extends TradeInfo {
    private int num;
    private int num_of_transaction;
    private String block_hash;
}
