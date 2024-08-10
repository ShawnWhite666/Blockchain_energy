package com.blockchainenergy.trade.dealmatching.dao;

import com.blockchainenergy.blockchain.dataChain.DataBlock;
import com.blockchainenergy.trade.dealmatching.Contract;
import lombok.Data;

import java.util.Date;

@Data
public class TradeRecordEntity {
    private String trade_id;
    private String buyer;
    private String seller;
    private Date trade_time;
    private Double price;
    private Double contract_elec;
    private Double output_elec;
    private Double use_elec;
    private Integer time_slot;
    private String buyer_release_id;
    private String seller_release_id;
    private Long block_height;
    private String block_hash;
    private String block_pre_hash;

    public TradeRecordEntity(String trade_id, String buyer, String seller, Date trade_time, Double price, Double contract_elec, Integer time_slot, String buyer_release_id, String seller_release_id) {
        this.trade_id = trade_id;
        this.buyer = buyer;
        this.seller = seller;
        this.trade_time = trade_time;
        this.price = price;
        this.contract_elec = contract_elec;
        this.time_slot = time_slot;
        this.buyer_release_id = buyer_release_id;
        this.seller_release_id = seller_release_id;
    }

//    contract.getTradeID(),
//            contract.getBuyer(),
//            contract.getSeller(),
//            contract.getTradeTime(),
//            contract.getPrice(),
//            contract.getContractElec(),
//    time_slot,
//            contract.getBuyerReleaseID(),
//            contract.getSellerReleaseID()

    public TradeRecordEntity(Contract contract, Integer time_slot) {
        this.trade_id = contract.getTradeID();
        this.buyer = contract.getBuyer();
        this.seller = contract.getSeller();
        this.trade_time = contract.getTradeTime();
        this.price = contract.getPrice();
        this.contract_elec = contract.getContractElec();
        this.time_slot = time_slot;
        this.buyer_release_id = contract.getBuyerReleaseID();
        this.seller_release_id = contract.getSellerReleaseID();
    }

    public void addBlockInfo(DataBlock dataBlock) {
        block_height = dataBlock.getIndex();
        block_hash = dataBlock.getHash();
        block_pre_hash = dataBlock.getPrevious_hash();
    }
}
