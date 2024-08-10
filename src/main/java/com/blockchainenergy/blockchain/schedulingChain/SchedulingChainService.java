package com.blockchainenergy.blockchain.schedulingChain;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/11/4 21:25
 */
public interface SchedulingChainService {
    List<SchedulingBlock> last10Block();

    List<SchedulingBlock> getChain();
//    // 获取全部区块链信息
//    Map<String, Object> getFullChain() throws Exception;
//
//    Double createJsonFile(String jsonString);
//
//    Map<String, Object> getFullChainWithHash() throws Exception;
//
//    // 挖区块， 交易单个存储
//    void makeNewSchedulingChain(TradeResult tradeResult) throws Exception;
//
////    Map<String, Object> mine(MineDto dto) throws IOException;
//
//    Map<String, Object> nodesRegister(NodesRegisterDto dto) throws Exception;
//
//    Map<String, Object> resolve() throws Exception;
//
//    Map<String, Object> updateByNode(String node) throws Exception;
//
//    Map<String, Object> getLast10ChainWithHash() throws Exception;
}
