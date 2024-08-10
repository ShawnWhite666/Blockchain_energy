package com.blockchainenergy.blockchain.schedulingChain;

import com.blockchainenergy.common.utils.SerializeUtils;
import com.blockchainenergy.trade.dealResult.TradeResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 交易结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scheduling {
    private String tradeID;
    private String buyer;
    private String seller;
    private Double contractElec;
    private Double useElec;
    private Double outputElec;
    private String defaulter;
    private Double buyer_credit_point;
    private Double seller_credit_point;

    public Scheduling(TradeResult tradeResult) {
        tradeID = tradeResult.getTrade_id();
        buyer = tradeResult.getBuyer();
        seller = tradeResult.getSeller();
        Integer default_id = tradeResult.getDefault_id();
        defaulter = default_id == 2 ? buyer :
                default_id == 3 ? seller :
                        default_id == 4 ? "双方均违约" : "无";
        outputElec = tradeResult.getOutput_elec();
        useElec = tradeResult.getUse_elec();
        contractElec = tradeResult.getContractElec();
        buyer_credit_point = tradeResult.getBuyerPoint();
        seller_credit_point = tradeResult.getSellerPoint();
    }

    public static Scheduling CoinSC() {
        Scheduling coinSC = new Scheduling();
        coinSC.setTradeID("genesis block");
        return coinSC;
    }

    public byte[] hash() {
        // 使用序列化的方式对Transaction对象进行深度复制
        byte[] serializeBytes = SerializeUtils.serialize(this);
        Scheduling copyTx = (Scheduling) SerializeUtils.deserialize(serializeBytes);

        return DigestUtils.sha256(SerializeUtils.serialize(copyTx));
    }
}
