package com.blockchainenergy.blockchain.dataChain;

import lombok.Data;

import java.util.Map;

@Data
public class Storage {
    private int num;
    private String jsonFile;
    private Map<String, Object> fullChainWithHash;

    public Storage(int num, String jsonFile, Map<String, Object> fullChainWithHash) {
        this.num = num;
        this.jsonFile = jsonFile;
        this.fullChainWithHash = fullChainWithHash;
    }
}
