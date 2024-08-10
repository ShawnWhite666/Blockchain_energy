package com.blockchainenergy.blockchain.schedulingChain;

import com.blockchainenergy.common.Node;
import com.blockchainenergy.common.utils.ByteUtils;
import com.blockchainenergy.common.utils.RocksDBUtils;
import com.blockchainenergy.trade.dealResult.TradeResult;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;

@Data
@NoArgsConstructor
public class SchedulingBlock {
    private String hash;
    private Long index;
    private Long timeStamp;
    private String previous_hash;
    private String node_key;
    private Scheduling scheduling;


    /**
     * 生成Hash
     */
    public String getHash() {
        if (hash == null || hash.length() == 0) {
            hash = DigestUtils.sha256Hex(prepareHashData());
        }
        return hash;
    }

    private byte[] prepareHashData() {
        return ByteUtils.merge(
                ByteUtils.toBytes(index),
                ByteUtils.toBytes(timeStamp),
                previous_hash.getBytes(),
                node_key.getBytes(),
                scheduling.hash());
    }

    public SchedulingBlock(Long index, Long timestamp, String previous_hash, Scheduling scheduling) {
        this.index = index;
        this.timeStamp = timestamp;
        this.previous_hash = previous_hash;
        this.scheduling = scheduling;

        this.node_key = Node.KEY;

        getHash();
    }

    /**
     * 新建区块
     */
    public static SchedulingBlock newBlock(Scheduling sc) {
        RocksDBUtils rdb = RocksDBUtils.getInstance();

        if (rdb.isSchedulingBucketEmpty()) {// 如果是空的就先加入起始区块
            SchedulingBlock coinBlock = new SchedulingBlock(1L, Instant.now().getEpochSecond(), ByteUtils.ZERO_HASH, Scheduling.CoinSC());
            rdb.putSchedulingBlock(coinBlock);
        }

        // 制造新区块
        SchedulingBlock lastBlock = rdb.getLastSchedulingBlock();
        SchedulingBlock schedulingBlock = new SchedulingBlock(lastBlock.getIndex() + 1, Instant.now().getEpochSecond(), lastBlock.getHash(), sc);
        rdb.putSchedulingBlock(schedulingBlock);// 放过db
        return schedulingBlock;
    }

    public static SchedulingBlock newBlock(TradeResult tradeResult) {
        return newBlock(new Scheduling(tradeResult));
    }
}
