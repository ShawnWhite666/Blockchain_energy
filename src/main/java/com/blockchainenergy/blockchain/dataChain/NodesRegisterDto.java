package com.blockchainenergy.blockchain.dataChain;

import java.util.List;

/**
 * @descriptions: 节点注册用
 * @data: 2021/11/8 12:51
 */
public class NodesRegisterDto {
    private List<String> nodes;

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
