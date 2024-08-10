package com.blockchainenergy.trade.dealmatching;

import com.blockchainenergy.trade.dealmatching.dao.TimeSlotInfoEntity;

import java.util.Date;

/**
 * 买卖双方发布的交易信息
 */
public class ReleaseInfo {
    private String releaseID;
    private String userID;
    private Double elecTotalQuantity;
    private Double price;
    private Date releaseTime;
    private Double elecUpper;
    private Double elecLower;
    private Double elecValue;
    private Double creditPoint;
    private boolean trade_type;

    public ReleaseInfo(String releaseID, String userID, Double elecTotalQuantity, Double price, Date releaseTime, Double elecUpper, Double elecLower, Double elecValue, Double creditPoint, boolean trade_type) {
        this.releaseID = releaseID;
        this.userID = userID;
        this.elecTotalQuantity = elecTotalQuantity;
        this.price = price;
        this.releaseTime = releaseTime;
        this.elecUpper = elecUpper;
        this.elecLower = elecLower;
        this.elecValue = elecValue;
        this.creditPoint = creditPoint;
        this.trade_type = trade_type;
    }

    public ReleaseInfo(TimeSlotInfoEntity item) {
        this.releaseID = item.getRelease_id();
        this.userID = item.getUser_id();
        this.elecTotalQuantity = item.getTotal_elec();
        this.price = item.getPrice();
        this.releaseTime = item.getRelease_time();
        this.elecUpper = item.getElec_upper();
        this.elecLower = item.getElec_lower();
        this.elecValue = item.getElec_value();
        this.creditPoint = item.getCredit_point();
        this.trade_type = item.getTrade_type();
    }

    public String getReleaseID() {
        return releaseID;
    }

    public String getUserID() {
        return userID;
    }

    public boolean getTrade_type() {
        return trade_type;
    }

    public double getElecTotalQuantity() {
        return elecTotalQuantity;
    }

    public double getPrice() {
        return price;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public double getElecUpper() {
        return elecUpper;
    }

    public double getElecLower() {
        return elecLower;
    }

    public double getElecValue() {
        return elecValue;
    }

    public double getCreditPoint() {
        return creditPoint;
    }

    /**
     * 计算报价-信用值S
     *
     * @return 报价-信用值S
     */
    public double calcS() {
        return trade_type ? (price / creditPoint) : (price * creditPoint);
    }

    public void clearElecValue() {
        elecValue = 0.0;
    }

    public void subElecValue(double elec) throws RuntimeException {
        if (elec > elecValue) {
            throw new RuntimeException("交易之后电量为负！");
        } else {
            elecValue = elecValue - elec;
        }
    }
}
