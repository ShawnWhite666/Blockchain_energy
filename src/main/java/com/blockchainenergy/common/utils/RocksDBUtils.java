package com.blockchainenergy.common.utils;

import com.blockchainenergy.blockchain.dataChain.DataBlock;
import com.blockchainenergy.blockchain.schedulingChain.SchedulingBlock;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.Map;

/**
 * 存储工具类
 */
@Slf4j
public class RocksDBUtils {
    /**
     * 区块链数据文件存储地址
     * 数据区块桶Key
     * 调度区块桶Key
     * 最新数据区块key
     * 最新调度区块key
     */
    private static final String DB_FILE = "blockchain.db";
    private static final String DATA_BLOCKS_BUCKET_KEY = "data_blocks";
    private static final String SCHEDULING_BLOCKS_BUCKET_KEY = "scheduling_blocks";
    private static final String LAST_DATA_BLOCK_KEY = "dblk";
    private static final String LAST_SCHEDULING_BLOCK_KEY = "sblk";

    /**
     * 单例模式
     */
    private volatile static RocksDBUtils instance;

    public static RocksDBUtils getInstance() {
        if (instance == null) {
            synchronized (RocksDBUtils.class) {
                if (instance == null) {
                    instance = new RocksDBUtils();
                }
            }
        }
        return instance;
    }

    /**
     * db文件
     * db.put(SerializeUtils.serialize(BLOCKS_BUCKET_KEY), SerializeUtils.serialize(blocksBucket));
     * db.put(SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY), SerializeUtils.serialize(chainstateBucket));
     */
    private RocksDB db;

    /**
     * 存储桶
     * blocksBucket.put(LAST_BLOCK_KEY, SerializeUtils.serialize(tipBlockHash));
     * blocksBucket.put(block.getHash(), SerializeUtils.serialize(block));
     * <p>
     * 数据桶
     * 调度桶
     */
    private Map<String, byte[]> dataBlocksBucket;
    private Map<String, byte[]> schedulingBlocksBucket;

    /**
     * 创建
     */
    private RocksDBUtils() {
        openDB();
        initDataBucket();
        initSchedulingBucket();
    }

    /**
     * 打开数据库
     */
    private void openDB() {
        try {
            db = RocksDB.open(DB_FILE);
        } catch (RocksDBException e) {
            log.error("Fail to open db ! ", e);
            throw new RuntimeException("Fail to open db ! ", e);
        }
    }

    /**
     * 初始化桶
     */
    private void initDataBucket() {
        try {
            byte[] dataBlockBucketKey = SerializeUtils.serialize(DATA_BLOCKS_BUCKET_KEY);
            byte[] dataBlockBucketBytes = db.get(dataBlockBucketKey);
            if (dataBlockBucketBytes != null) {
                dataBlocksBucket = (Map) SerializeUtils.deserialize(dataBlockBucketBytes);
            } else {
                dataBlocksBucket = Maps.newHashMap();
                db.put(dataBlockBucketKey, SerializeUtils.serialize(dataBlocksBucket));
            }
        } catch (RocksDBException e) {
            log.error("Fail to init block bucket ! ", e);
            throw new RuntimeException("Fail to init bucket ! ", e);
        }
    }

    private void initSchedulingBucket() {
        try {
            byte[] schedulingBlockBucketKey = SerializeUtils.serialize(SCHEDULING_BLOCKS_BUCKET_KEY);
            byte[] schedulingBlockBucketBytes = db.get(schedulingBlockBucketKey);
            if (schedulingBlockBucketBytes != null) {
                schedulingBlocksBucket = (Map) SerializeUtils.deserialize(schedulingBlockBucketBytes);
            } else {
                schedulingBlocksBucket = Maps.newHashMap();
                db.put(schedulingBlockBucketKey, SerializeUtils.serialize(schedulingBlocksBucket));
            }
        } catch (RocksDBException e) {
            log.error("Fail to init block bucket ! ", e);
            throw new RuntimeException("Fail to init bucket ! ", e);
        }
    }

    /**
     * 获得最后区块hash
     */
    public String getLastDataBlockHash() {
        return getLastBlockHashHelp(dataBlocksBucket.get(LAST_DATA_BLOCK_KEY));
    }

    public String getLastSchedulingBlockHash() {
        return getLastBlockHashHelp(schedulingBlocksBucket.get(LAST_SCHEDULING_BLOCK_KEY));
    }

    public String getLastBlockHashHelp(byte[] lastBlockHashBytes) {
        if (lastBlockHashBytes != null) {
            String lastBlockHash = (String) SerializeUtils.deserialize(lastBlockHashBytes);
            if (lastBlockHash == null) return "";
            return lastBlockHash;
        }
        return "";
    }

    /**
     * 获得最后区块
     */
    public DataBlock getLastDataBlock() {
        String lastDataBlockHash = getLastDataBlockHash();
        if (lastDataBlockHash == null) return null;
        return getDataBlock(lastDataBlockHash);
    }

    public SchedulingBlock getLastSchedulingBlock() {
        String lastSchedulingBlockHash = getLastSchedulingBlockHash();
        if (lastSchedulingBlockHash == null) return null;
        return getSchedulingBlock(lastSchedulingBlockHash);
    }

    /**
     * 桶（区块链）是否为空
     */
    public boolean isDataBucketEmpty() {
        return getLastDataBlockHash().length() == 0;
    }

    public boolean isSchedulingBucketEmpty() {
        return getLastSchedulingBlockHash().length() == 0;
    }

    /**
     * 保存区块
     */
    public void putDataBlock(DataBlock block) {
        Map<String, byte[]> blocksBucket = dataBlocksBucket;
        String lastBlockKey = LAST_DATA_BLOCK_KEY;
        String bucketKey = DATA_BLOCKS_BUCKET_KEY;

        String blockHash = block.getHash();
        try {
            blocksBucket.put(blockHash, SerializeUtils.serialize(block));
            db.put(SerializeUtils.serialize(bucketKey), SerializeUtils.serialize(blocksBucket));
        } catch (RocksDBException e) {
            log.error("Fail to put block ! block=" + block.toString(), e);
            throw new RuntimeException("Fail to put block ! block=" + block.toString(), e);
        }

        putLastBlockHash(blocksBucket, bucketKey, lastBlockKey, blockHash);
    }

    public void putSchedulingBlock(SchedulingBlock block) {
        Map<String, byte[]> blocksBucket = schedulingBlocksBucket;
        String lastBlockKey = LAST_SCHEDULING_BLOCK_KEY;
        String bucketKey = SCHEDULING_BLOCKS_BUCKET_KEY;

        String blockHash = block.getHash();
        try {
            blocksBucket.put(blockHash, SerializeUtils.serialize(block));
            db.put(SerializeUtils.serialize(bucketKey), SerializeUtils.serialize(blocksBucket));
        } catch (RocksDBException e) {
            log.error("Fail to put block ! block=" + block.toString(), e);
            throw new RuntimeException("Fail to put block ! block=" + block.toString(), e);
        }

        putLastBlockHash(blocksBucket, bucketKey, lastBlockKey, blockHash);
    }

    private void putLastBlockHash(Map<String, byte[]> blocksBucket, String bucketKey, String lastBlockKey, String blockHash) {
        // 加入lastBlockKey
        try {
            blocksBucket.put(lastBlockKey, SerializeUtils.serialize(blockHash));
            db.put(SerializeUtils.serialize(bucketKey), SerializeUtils.serialize(blocksBucket));
        } catch (RocksDBException e) {
            String errInfo = "Fail to put last block hash ! blockHash=";
            log.error(errInfo + blockHash, e);
            throw new RuntimeException(errInfo + blockHash, e);
        }
    }


    /**
     * 查询区块
     */
    public DataBlock getDataBlock(String blockHash) {
        byte[] blockBytes = dataBlocksBucket.get(blockHash);
        if (blockBytes != null) {
            return (DataBlock) SerializeUtils.deserialize(blockBytes);
        }
        return null;
//        throw new RuntimeException("Fail to get block ! blockHash=" + blockHash);
    }

    public SchedulingBlock getSchedulingBlock(String blockHash) {
        byte[] blockBytes = schedulingBlocksBucket.get(blockHash);
        if (blockBytes != null) {
            return (SchedulingBlock) SerializeUtils.deserialize(blockBytes);
        }
        return null;
//        throw new RuntimeException("Fail to get block ! blockHash=" + blockHash);
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        try {
            db.close();
        } catch (Exception e) {
            log.error("Fail to close db ! ", e);
            throw new RuntimeException("Fail to close db ! ", e);
        }
    }
}
