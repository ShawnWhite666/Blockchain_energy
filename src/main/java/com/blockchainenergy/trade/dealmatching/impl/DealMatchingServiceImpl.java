package com.blockchainenergy.trade.dealmatching.impl;

import com.blockchainenergy.blockchain.dataChain.DataBlock;
import com.blockchainenergy.info.market.InfoMarketService;
import com.blockchainenergy.trade.dealmatching.Contract;
import com.blockchainenergy.trade.dealmatching.DealMatcher;
import com.blockchainenergy.trade.dealmatching.DealMatchingService;
import com.blockchainenergy.trade.dealmatching.dao.DealingMatchingDao;
import com.blockchainenergy.trade.dealmatching.dao.TimeSlotInfoEntity;
import com.blockchainenergy.trade.dealmatching.dao.TradeRecordEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DealMatchingServiceImpl implements DealMatchingService {

    @Resource
    private DealingMatchingDao dealingMatchingDao;

    @Resource
    private InfoMarketService infoMarketService;

    @Override
    public List<TimeSlotInfoEntity> getTimeSlotInfo(LocalDate date, Integer timeSlot) {
        List<TimeSlotInfoEntity> entities = dealingMatchingDao.getTimeSlotInfoByTimeSlot(timeSlot, date);// 获得发布交易的信息
        for (TimeSlotInfoEntity item : entities) {
            if (item.getTrade_type() == false) {// 买
                Double historyElec = dealingMatchingDao.getBuyerHistoryElec(item.getRelease_id(), timeSlot);// 获取买方的历史数据
                if (historyElec == null) historyElec = 0.0;
                item.setSum_history_elec(historyElec);
            } else {// 卖
                Double historyElec = dealingMatchingDao.getSellerHistoryElec(item.getRelease_id(), timeSlot);// 获取卖方的历史数据
                if (historyElec == null) historyElec = 0.0;
                item.setSum_history_elec(historyElec);
            }
            Double futureLower = dealingMatchingDao.getFutureLower(item.getRelease_id(), timeSlot);// 获得未来最小数据
            if (futureLower == null) futureLower = 0.0;
            item.setSum_future_lower(futureLower);
        }
        return entities;
    }

    /**
     * 执行撮合
     */
    @Override
    public void executeMatching(LocalDate date, Integer timeSlot) {
        //  准备数据：获得当前时间戳的总买卖信息
        List<TimeSlotInfoEntity> timeSlotInfoEntities = getTimeSlotInfo(date, timeSlot);// 获得发布数据
        List<Contract> contractList = DealMatcher.getInstance().dealMatching(timeSlotInfoEntities, timeSlot);// 撮合、筛选
        List<TradeRecordEntity> tradeRecordEntityList = convertContractsToEntities(contractList, timeSlot);// 转化
        // 添加区块信息
        for (int i = 0; i < contractList.size(); i++) {
            Contract contract = contractList.get(i);
            TradeRecordEntity entity = tradeRecordEntityList.get(i);
            DataBlock dataBlock = DataBlock.newBlock(contract);
            entity.addBlockInfo(dataBlock);// 新增区块并将信息加入entity中
        }
        log.info("-------------------");
        log.info("交易记录数量：" + tradeRecordEntityList.size());
        for (TradeRecordEntity tradeRecord : tradeRecordEntityList) {
            dealingMatchingDao.insertTradeRecord(tradeRecord);// 插入交易记录
            log.info("插入交易记录");
            dealingMatchingDao.updateReleaseStateToMatched(tradeRecord.getBuyer_release_id());// 插入买方交易
            dealingMatchingDao.updateReleaseStateToMatched(tradeRecord.getSeller_release_id());// 插入卖方交易
            log.info("更新发布状态");
        }
        log.info("-------------------");

        infoMarketService.refreshMarketInfo(timeSlot);
    }

    private List<TradeRecordEntity> convertContractsToEntities(List<Contract> contractList, Integer time_slot) {
        List<TradeRecordEntity> tradeRecordEntities = new ArrayList<>();
        for (Contract contract : contractList) {
            tradeRecordEntities.add(new TradeRecordEntity(contract, time_slot));
        }
        return tradeRecordEntities;
    }
}
