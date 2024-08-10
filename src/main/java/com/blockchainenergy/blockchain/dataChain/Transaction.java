package com.blockchainenergy.blockchain.dataChain;

import com.blockchainenergy.common.utils.SerializeUtils;
import com.blockchainenergy.trade.dealmatching.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

/**
 * 交易结构
 */
@Data
@NoArgsConstructor
public class Transaction {
    private String trade_id;
    private String buyer;
    private String seller;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+8")
    private Date trade_time;
    private Double price;
    private Double contract_elec;
    private String buyerReleaseID;
    private String sellerReleaseID;

    public Transaction(Contract dto) {
        this.trade_id = dto.getTradeID();
        this.buyer = dto.getBuyer();
        this.seller = dto.getSeller();
        this.trade_time = dto.getTradeTime();
        this.price = dto.getPrice();
        this.contract_elec = dto.getContractElec();
        this.buyerReleaseID = dto.getBuyerReleaseID();
        this.sellerReleaseID = dto.getSellerReleaseID();
    }

    public static Transaction CoinTX() {
        Transaction coinTX = new Transaction();
        coinTX.setTrade_id("genesis block");
        return coinTX;
    }

    public byte[] hash() {
        // 使用序列化的方式对Transaction对象进行深度复制
        byte[] serializeBytes = SerializeUtils.serialize(this);
        Transaction copyTx = (Transaction) SerializeUtils.deserialize(serializeBytes);

        return DigestUtils.sha256(SerializeUtils.serialize(copyTx));
    }
}
