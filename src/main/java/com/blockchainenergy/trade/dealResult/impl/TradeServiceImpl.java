package com.blockchainenergy.trade.dealResult.impl;

import com.blockchainenergy.blockchain.schedulingChain.SchedulingBlock;
import com.blockchainenergy.common.Electricity;
import com.blockchainenergy.common.redis.RedisService;
import com.blockchainenergy.info.user.dao.UserInfoDao;
import com.blockchainenergy.trade.dealResult.*;
import com.blockchainenergy.trade.dealResult.dao.DealResultDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Transactional// 开启事务，确保每个事件都是原子化的
public class TradeServiceImpl implements TradeService {
    @Resource
    private DealResultDao dealResultDao;

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private RedisService redisService;

    /**
     * 卖方违约 买方继续用电
     **/
    @Override
    public TradeResult sellerDefault(TradeResult tradeResult, TradingParameter tradingParameter) {
        Double bPrice = Electricity.TARIFF;
        Double piPrice = tradingParameter.getPiPrice();
        Double hQuantity = tradingParameter.getHQuantity();
        Double mQuantity = tradingParameter.getMQuantity();
        tradeResult.setDefault_id(3);
        if (bPrice > piPrice) {
            double w = (bPrice - piPrice) * hQuantity;
            double buyerIncome = -mQuantity * piPrice - hQuantity * bPrice + w;
            double sellerIncome = mQuantity * piPrice - w;
            // 保留2位
            new DecimalFormat("######0.00").format(buyerIncome);
            new DecimalFormat("######0.00").format(sellerIncome);
            tradeResult.setBuyerIncome(buyerIncome);
            tradeResult.setSellerIncome(sellerIncome);
            tradeResult.setPowerIncome(-buyerIncome - sellerIncome);
        } else {
            Double cks = tradingParameter.getCks();
            double L = getLk(cks, hQuantity, piPrice);
            double buyerIncome = -mQuantity * piPrice + L - hQuantity * bPrice;
            double sellerIncome = mQuantity * piPrice - L;
            // 保留2位
            new DecimalFormat("######0.00").format(buyerIncome);
            new DecimalFormat("######0.00").format(sellerIncome);
            tradeResult.setBuyerIncome(buyerIncome);
            tradeResult.setSellerIncome(sellerIncome);
            tradeResult.setPowerIncome(-buyerIncome - sellerIncome);
        }
        return tradeResult;
    }

    /**
     * 买方违约 卖方卖给电网
     **/
    @Override
    public TradeResult buyerDefault(TradeResult tradeResult, TradingParameter tradingParameter) {
        Double aPrice = Electricity.GRID_PRICE;
        Double piPrice = tradingParameter.getPiPrice();
        Double hQuantity = tradingParameter.getHQuantity();
        Double mQuantity = tradingParameter.getMQuantity();
        Double eQuantity = tradingParameter.getEQuantity();
        tradeResult.setDefault_id(2);
        if (aPrice < piPrice) {
            double w = (piPrice - aPrice) * hQuantity;
            double buyerIncome = -mQuantity * piPrice - w;
            double sellerIncome = eQuantity * piPrice;
            // 保留2位
            new DecimalFormat("######0.00").format(buyerIncome);
            new DecimalFormat("######0.00").format(sellerIncome);
            tradeResult.setBuyerIncome(buyerIncome);
            tradeResult.setSellerIncome(sellerIncome);
            tradeResult.setPowerIncome(-buyerIncome - sellerIncome);
        } else {
            Double ckb = tradingParameter.getCkb();
            double L = getLk(ckb, hQuantity, piPrice);
            double buyerIncome = -mQuantity * piPrice - L;
            double sellerIncome = +mQuantity * piPrice + L + hQuantity * aPrice;
            // 保留2位
            new DecimalFormat("######0.00").format(buyerIncome);
            new DecimalFormat("######0.00").format(sellerIncome);
            tradeResult.setBuyerIncome(buyerIncome);
            tradeResult.setSellerIncome(sellerIncome);
            tradeResult.setPowerIncome(-buyerIncome - sellerIncome);
        }
        return tradeResult;
    }

    /**
     * 双方违约
     **/
    @Override
    public TradeResult bothDefault(TradeResult tradeResult, TradingParameter tradingParameter) {
        Double mQuantity = tradingParameter.getMQuantity();
        Double piPrice = tradingParameter.getPiPrice();
        tradeResult.setDefault_id(4);
        double sellerIncome = +mQuantity * piPrice;
        double buyerIncome = -sellerIncome;
        // 保留2位
        new DecimalFormat("######0.00").format(buyerIncome);
        new DecimalFormat("######0.00").format(sellerIncome);
        tradeResult.setBuyerIncome(buyerIncome);
        tradeResult.setSellerIncome(sellerIncome);
        tradeResult.setPowerIncome(0D);
        return tradeResult;
    }

    /**
     * 未违约
     **/
    @Override
    public TradeResult noDefault(TradeResult tradeResult, TradingParameter tradingParameter) {
        tradeResult.setDefault_id(1);
        Double mQuantity = tradingParameter.getMQuantity();
        Double piPrice = tradingParameter.getPiPrice();
        double sellerIncome = +mQuantity * piPrice;
        double buyerIncome = -sellerIncome;
        // 保留2位
        new DecimalFormat("######0.00").format(buyerIncome);
        new DecimalFormat("######0.00").format(sellerIncome);
        tradeResult.setBuyerIncome(buyerIncome);
        tradeResult.setSellerIncome(sellerIncome);
        tradeResult.setPowerIncome(0D);
        return tradeResult;
    }

    /**
     * 根据用户id获取交易员信息
     **/
    @Override
    public Trader getTraderInfo(String user_id) {
        return dealResultDao.getTraderInfo(user_id);
    }

    /**
     * 交易刷新个人信息
     **/
    private void refreshTrader(Double contract_elec, Trader tradeBuyer, Trader tradeSeller, TradeResult tradeResult) {
        Double use_elec = tradeResult.getUse_elec();
        Double output_elec = tradeResult.getOutput_elec();
        String buyerUser_id = tradeBuyer.getUser_id();
        String sellerUserId = tradeSeller.getUser_id();
        // 获取最新积分
        Double buyerPoint = getNewCreditPoint(tradeBuyer, use_elec, contract_elec);
        Double sellerPoint = getNewCreditPoint(tradeSeller, output_elec, contract_elec);
        // 更新用户表
        dealResultDao.updateBuyer(buyerUser_id, buyerPoint, tradeResult.getBuyerIncome());
        dealResultDao.updateSeller(sellerUserId, sellerPoint, tradeResult.getSellerIncome());
        dealResultDao.updatePower(Electricity.ELECTRIC_GRID_ID, tradeResult.getPowerIncome());// 更新电网

        tradeResult.setBuyerPoint(buyerPoint);
        tradeResult.setSellerPoint(sellerPoint);
        tradeResult.setBuyer(buyerUser_id);
        tradeResult.setSeller(sellerUserId);
        // 更新内存
        redisService.addWalletLog(buyerUser_id, userInfoDao.getUserBalance(buyerUser_id), buyerPoint);
        redisService.addWalletLog(sellerUserId, userInfoDao.getUserBalance(sellerUserId), sellerPoint);
    }

    /**
     * 获取新的信用积分
     **/
    private Double getNewCreditPoint(Trader trader, Double actual_elec, Double contract_elec) {
        Double ck = trader.getCredit_point();
        int r = trader.getBuy_number() + trader.getSell_number();
        return ((ck * r) + actual_elec / contract_elec) / (r + 1);
    }

    /**
     * 获取违约金
     **/
    private Double getLk(Double ck, Double h, Double pi) {
        return (1 - ck) * h * pi * 0.1;
    }

    /**
     * 模拟 取全部未调度的交易调度
     **/
    @Override
    public List<TradeResult> dealResult() {
        List<TradeInfo> tradeInfos = dealResultDao.getMatchedTrade();// 获取适合的交易
        log.info("### 适合调度的交易数：" + tradeInfos.size() + " ###");
        List<TradeResult> ans = new ArrayList<>();

        for (TradeInfo tradeInfo : tradeInfos) {
            int r = new Random().nextInt(4);
            // 随机化概率
            DealResultDto dealResult;
            switch (r) {
                case 1:
                    dealResult = getBuyerDefaultResult(tradeInfo);// 买方错误
                    break;
                case 2:
                    dealResult = getSellerDefaultResult(tradeInfo);// 卖方错误
                    break;
                case 3:
                    dealResult = getBothDefaultResult(tradeInfo);// 均错误
                    break;
                default:
                    dealResult = getFairResult(tradeInfo);// 模拟数据
                    break;
            }
            TradeResult tradeResult = dealResultHelp(dealResult, tradeInfo);// 调度数据并更新数据库
            log.info("### 装入区块 ###");
            SchedulingBlock.newBlock(tradeResult);// 新建调度区块
            ans.add(tradeResult);
        }
        return ans;
    }

    private DealResultDto getBothDefaultResult(TradeInfo tradeInfo) {
        return new DealResultDto(tradeInfo.getTrade_id(), tradeInfo.getContract_elec() * 0.8, tradeInfo.getContract_elec() * 0.85);
    }

    private DealResultDto getFairResult(TradeInfo tradeInfo) {
        return new DealResultDto(tradeInfo.getTrade_id(), tradeInfo.getContract_elec(), tradeInfo.getContract_elec());
    }

    private DealResultDto getBuyerDefaultResult(TradeInfo tradeInfo) {
        return new DealResultDto(tradeInfo.getTrade_id(), tradeInfo.getContract_elec(), tradeInfo.getContract_elec() * 0.8);
    }

    private DealResultDto getSellerDefaultResult(TradeInfo tradeInfo) {
        return new DealResultDto(tradeInfo.getTrade_id(), tradeInfo.getContract_elec() * 0.8, tradeInfo.getContract_elec());
    }

    /**
     * 实际调度和加入数据库
     */
    private TradeResult dealResultHelp(DealResultDto dealResultDto, TradeInfo tradeInfo) {
        log.info("### 开始调度 ###");
        String trade_id = tradeInfo.getTrade_id();
        //获取交易
        //参数获取
        Double contract_elec = tradeInfo.getContract_elec();//合约电量
        Double output_elec = dealResultDto.getOutput_elec();//卖方实际出电量
        Double use_elec = dealResultDto.getUse_elec();//买方实际用电量
        Trader tradeBuyer = getTraderInfo(tradeInfo.getBuyer());
        Trader tradeSeller = getTraderInfo(tradeInfo.getSeller());
        Double price = tradeInfo.getPrice();// 合约价格
        Double credit_buyer_point = tradeBuyer.getCredit_point();
        Double credit_seller_point = tradeSeller.getCredit_point();
        TradeResult tradeResult = new TradeResult(trade_id, use_elec, output_elec);
        // 初始化
        TradingParameter tradingParameter = new TradingParameter(contract_elec, price, use_elec, output_elec, credit_buyer_point, credit_seller_point);
        if (use_elec == output_elec && output_elec == contract_elec) {//未违约
            tradeResult = noDefault(tradeResult, tradingParameter);
        } else {//违约了
            if (output_elec < contract_elec && use_elec < contract_elec) {//同时违约
                tradeResult = bothDefault(tradeResult, tradingParameter);
            } else if (output_elec < contract_elec) {//卖方违约
                tradeResult = sellerDefault(tradeResult, tradingParameter);
            } else if (use_elec < contract_elec) {//买方违约
                tradeResult = buyerDefault(tradeResult, tradingParameter);
            }
            // 超过了不处理
        }
        log.info("### 更新数据库 ###");
        dealResultDao.updateTradeRecord(tradeResult);// 更新交易记录表
        refreshTrader(contract_elec, tradeBuyer, tradeSeller, tradeResult);// 更新交易人信息

        tradeResult.setContractElec(tradeInfo.getContract_elec());
        log.info("### 结束调度 ###");
        return tradeResult;
    }
}
