package com.blockchainenergy.blockchain.dataChain;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/11/4 21:25
 */
public interface DataChainService {
    // 获取全部区块链信息
    List<DataBlock> getFullChain();

    Double createJsonFile(String jsonString);

    List<DataBlock> getLast10Chain();

    List<DataBlock> getChain();
}
