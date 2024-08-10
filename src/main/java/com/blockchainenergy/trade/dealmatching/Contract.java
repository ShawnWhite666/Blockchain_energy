package com.blockchainenergy.trade.dealmatching;

import com.blockchainenergy.common.utils.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 合约信息，即撮合后生成的交易记录
 */
@Data
public class Contract {
    private String tradeID;
    private String buyer;
    private String seller;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date tradeTime;
    private Double price;
    private Double contractElec;
    private String buyerReleaseID;
    private String sellerReleaseID;

    public Contract(String buyer, String seller, Double price, Double contractElec, String buyerReleaseID, String sellerReleaseID) {
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.contractElec = contractElec;
        this.buyerReleaseID = buyerReleaseID;
        this.sellerReleaseID = sellerReleaseID;

        tradeTime = new Date();

        tradeID = getHashString();
    }

    public String getHashString() {
        String info = buyer + seller + tradeTime + price + contractElec + buyerReleaseID + sellerReleaseID;
        return Utils.generateHashCode(info);
    }
}
