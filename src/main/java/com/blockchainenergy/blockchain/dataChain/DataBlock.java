package com.blockchainenergy.blockchain.dataChain;

import com.blockchainenergy.common.Node;
import com.blockchainenergy.common.utils.ByteUtils;
import com.blockchainenergy.common.utils.RocksDBUtils;
import com.blockchainenergy.trade.dealmatching.Contract;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;

@Data
@NoArgsConstructor
public class DataBlock {
    private String hash;
    private Long index;
    private Long timeStamp;
    private String previous_hash;
    private String node_key;
    private Transaction transactionInfo;

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
                transactionInfo.hash());
    }

    public DataBlock(Long index, Long timestamp, String previous_hash, Transaction transactionInfo) {
        this.index = index;
        this.timeStamp = timestamp;
        this.previous_hash = previous_hash;
        this.transactionInfo = transactionInfo;

        this.node_key = Node.KEY;

        getHash();
    }

    /**
     * 新建区块
     */
    public static DataBlock newBlock(Transaction tx) {
        RocksDBUtils rdb = RocksDBUtils.getInstance();

        if (rdb.isDataBucketEmpty()) {// 如果是空的就先加入起始区块
            DataBlock coinBlock = new DataBlock(1L, Instant.now().getEpochSecond(), ByteUtils.ZERO_HASH, Transaction.CoinTX());
            rdb.putDataBlock(coinBlock);
        }

        DataBlock lastBlock = rdb.getLastDataBlock();
        DataBlock dataBlock = new DataBlock(lastBlock.getIndex() + 1, Instant.now().getEpochSecond(), lastBlock.getHash(), tx);

        rdb.putDataBlock(dataBlock);
        return dataBlock;
    }

    public static DataBlock newBlock(Contract c) {
        return newBlock(new Transaction(c));
    }
}
