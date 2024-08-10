package com.blockchainenergy.blockchain.dataChain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class Throughoutput {
    private int block_num;
    private int tran_per_block;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+8")
    private Date start_time;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+8")
    private Date end_time;
    private String consume_time;
    private String tps;
    private String jsonMb;
    private Map<String, Object> blockchain;


    public Throughoutput(int numbers, int num_tran, Date starts, Date ends, String diffs, String tps, String jsonMb, Map<String, Object> chain) {
        this.block_num = numbers;
        this.tran_per_block = num_tran;
        this.start_time = starts;
        this.end_time = ends;
        this.consume_time = diffs;
        this.tps = tps;
        this.jsonMb = jsonMb;
        this.blockchain = chain;
    }
}
