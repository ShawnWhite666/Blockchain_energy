package com.blockchainenergy.trade.dealmatching;

import com.blockchainenergy.common.Electricity;
import com.blockchainenergy.trade.dealmatching.dao.TimeSlotInfoEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 该类用于完成交易撮合，包括对发布信息进行排序、撮合卖方买方交易、生成交易记录（合约）
 */
@Slf4j
public class DealMatcher {
    private volatile static DealMatcher dealMatcher;

    private DealMatcher() {
    }

    public static DealMatcher getInstance() {
        if (dealMatcher == null) {
            synchronized (DealMatcher.class) {
                if (dealMatcher == null) {
                    dealMatcher = new DealMatcher();
                }
            }
        }
        return dealMatcher;
    }


    private static final boolean BUY = false;
    private static final boolean SELL = true;

    //常量A用于计算成交价
    public static final double A = 0.5;

    /**
     * 比较器
     */
    class BuyerComparator implements Comparator<ReleaseInfo> {
        @Override
        public int compare(ReleaseInfo a, ReleaseInfo b) {
            return (b.calcS() - a.calcS()) > 0 ? 1 : -1;
        }
    }

    class SellerComparator implements Comparator<ReleaseInfo> {
        @Override
        public int compare(ReleaseInfo a, ReleaseInfo b) {
            return (a.calcS() - b.calcS()) > 0 ? 1 : -1;
        }
    }

    /**
     * @param timeSlotInfoEntities 时间槽信息的列表，由service调用dealMatching的时候提供
     *                             该方法用于撮合交易
     */
    public List<Contract> dealMatching(List<TimeSlotInfoEntity> timeSlotInfoEntities, int timeSlot) {
        List<ReleaseInfo> buyers = new ArrayList<>();
        List<ReleaseInfo> sellers = new ArrayList<>();

        List<Contract> contractList = new ArrayList<>();

        for (TimeSlotInfoEntity item : timeSlotInfoEntities) {
            if (item.getTrade_type() == BUY) {
                buyers.add(convertReleaseInfo(item));
            } else {
                sellers.add(convertReleaseInfo(item));
            }
        }

        log.info("买方数量：" + buyers.size());
        log.info("卖方数量：" + sellers.size());

        buyers.sort(new BuyerComparator());
        sellers.sort(new SellerComparator());

        for (ReleaseInfo buyer : buyers) {
            if (buyer.getUserID().equals(Electricity.ELECTRIC_GRID_ID)) continue;
            for (ReleaseInfo seller : sellers) {
                if (seller.getUserID().equals(Electricity.ELECTRIC_GRID_ID)) continue;
                if (buyer.getUserID().equals(seller.getUserID())) continue;// 如果ID相同就退出
                if (seller.getElecValue() == 0.0) continue;
                if (buyer.getPrice() >= seller.getPrice()) {
                    contractList.add(executeMatching(buyer, seller));
                    if (buyer.getElecValue() == 0.0) {
                        break;
                    }
                }
            }
        }

        // 多的就和电网交易
        for (ReleaseInfo buyer : buyers) {
            if (buyer.getElecValue() > 0.0) {
                contractList.add(transactionWithElectricGrid(buyer, timeSlot));
            }
        }
        for (ReleaseInfo seller : sellers) {
            if (seller.getElecValue() > 0.0) {
                contractList.add(transactionWithElectricGrid(seller, timeSlot));
            }
        }

        // 筛选
        contractList = filtrateContract(contractList);

        return contractList;
    }

    private List<Contract> filtrateContract(List<Contract> contractList) {
        List<Contract> ans = new ArrayList<>();
        for (Contract c : contractList) {
            if (c.getSeller().equals(Electricity.ELECTRIC_GRID_ID) || c.getBuyer().equals(Electricity.ELECTRIC_GRID_ID)) {
                continue;
            }
            if (c.getContractElec().equals(0.0) || c.getBuyer().equals(c.getSeller())) {
                continue;
            }
            ans.add(c);
        }
        return ans;
    }

    private ReleaseInfo convertReleaseInfo(TimeSlotInfoEntity item) {
        if (item.getElec_value() == null) {// 非总和模式 上限和总
            Double transactionElec = Math.min(item.getElec_upper(), item.getTotal_elec() - item.getSum_history_elec() - item.getSum_future_lower());
            item.setElec_value(transactionElec);
        }
        return new ReleaseInfo(item);
    }

    /**
     * 对卖方买方进行撮合，生成合约
     *
     * @return Contract 生成的合约
     */
    private Contract executeMatching(ReleaseInfo buyer, ReleaseInfo seller) {
        double buyerElecValue = buyer.getElecValue();
        double sellerElecValue = seller.getElecValue();

        double contractPrice = A * (buyer.getPrice()) + (1 - A) * seller.getPrice();
        double contractionElec = Math.min(buyerElecValue, sellerElecValue);
        Contract contract = new Contract(buyer.getUserID(), seller.getUserID(), contractPrice, contractionElec, buyer.getReleaseID(), seller.getReleaseID());

        if (buyerElecValue == sellerElecValue) {
            buyer.clearElecValue();
            seller.clearElecValue();
        } else if (buyerElecValue > sellerElecValue) {
            buyer.subElecValue(sellerElecValue);
            seller.clearElecValue();
        } else {
            seller.subElecValue(buyerElecValue);
            buyer.clearElecValue();
        }

        return contract;
    }

    /**
     * 将用户和电网进行撮合，即用户向电网买/卖电
     *
     * @param user 用户，买方或卖方
     * @return Contract 生成的合约
     */
    private Contract transactionWithElectricGrid(ReleaseInfo user, int timeSlot) {
        Contract contract;
        if (user.getTrade_type() == BUY) {
            contract = new Contract(user.getUserID(), Electricity.ELECTRIC_GRID_ID, Electricity.BUY_FROM_GRID_PRICE[timeSlot], user.getElecValue(), user.getReleaseID(), null);
        } else {
            contract = new Contract(Electricity.ELECTRIC_GRID_ID, user.getUserID(), Electricity.SELL_TO_GRID_PRICE[timeSlot], user.getElecValue(), null, user.getReleaseID());
        }
        return contract;
    }

}
