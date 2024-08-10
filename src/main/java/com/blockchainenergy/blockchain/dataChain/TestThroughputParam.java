package com.blockchainenergy.blockchain.dataChain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @descriptions:
 */
@Data
public class TestThroughputParam {
    private String diffs;
    private String block_hash;
    private String trade_id;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date trade_time;

    public TestThroughputParam(String diffs, String block_hash, String trade_id, Date trade_time) {
        this.diffs = diffs;
        this.block_hash = block_hash;
        this.trade_id = trade_id;
        this.trade_time = trade_time;
    }

    @Override
    public String toString() {
        return "测试数据：{" + '\n' +
                "    " + "区块号：" + block_hash + '\n' +
                "    " + "交易ID：" + trade_id + '\n' +
                "    " + "交易时间：" + trade_time + '\n' +
                "    " + "出块时间：" + diffs + '\n' +
                '}';
    }
}
