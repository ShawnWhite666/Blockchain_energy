package com.blockchainenergy.trade.tradeInfo.impl;

import com.blockchainenergy.info.market.InfoMarket;
import com.blockchainenergy.mqtt.IoTDemoPubSubDemo;
import com.blockchainenergy.trade.tradeInfo.*;
import com.blockchainenergy.trade.tradeInfo.dao.TradeInfoDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TradeInfoServiceImpl implements TradeInfoService {
    @Resource
    private TradeInfoDao tradeInfoDao;

    @Override
    public List<TradeInfo> getTrade(GetTradeDto dto) {
        return tradeInfoDao.getTrade(dto);
    }

    @Override
    public TradeSlotInfo getTimeSlot() {
        return InfoMarket.get();
    }

    @Override
    public TradeDetail getTradeDetail(TradeInfoDto dto) {
        TradeDetail tradeDetail = tradeInfoDao.getTradeDetail(dto);
        tradeDetail.setBuyer_elec_meter_id(tradeInfoDao.getMeterIdById(tradeDetail.getBuyer()));
        tradeDetail.setSeller_elec_meter_id(tradeInfoDao.getMeterIdById(tradeDetail.getSeller()));
        return tradeDetail;
    }

    @Override
    public TradeSlotInfo getTimeSlotPersonByDate(TimeSlotDto dto) {
        int time = 0;
        List<Integer> tradingVolumes = new ArrayList<>();
        List<Double> maxPrices = new ArrayList<>();
        List<Double> minPrices = new ArrayList<>();
        List<Double> averagePrices = new ArrayList<>();
        while (time < 24) {
            dto.setTime_slot(time);
            tradingVolumes.add(tradeInfoDao.getTradingVolumesByTimePersonByDate(dto));
            maxPrices.add(tradeInfoDao.getMaxPriceByTimePersonByDate(dto));
            minPrices.add(tradeInfoDao.getMinPriceByTimePersonByDate(dto));
            averagePrices.add(tradeInfoDao.getAverageByTimePersonByDate(dto));
            time++;
        }
        TradeSlotInfo tradeSlotInfo = new TradeSlotInfo(tradingVolumes, maxPrices, minPrices, averagePrices);
        return tradeSlotInfo;
    }

    @Override
    public List<TradeEChart> getMyTradeEChart(String userId, boolean isBuyer) {
        List<TradeEChart> res = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            res.add(new TradeEChart());
        }
        List<TradeEChart> tradeECharts = isBuyer ? tradeInfoDao.getTradeEChartByBuyerID(userId) : tradeInfoDao.getTradeEChartBySellerID(userId);
        // 倒叙
        int len = tradeECharts.size();
        for (int i = 0; i < len; i++) {
            res.set(i, tradeECharts.get(len - i - 1));
        }
        return res;
    }

    @Override
    public void split(String userId, int isSplit) {
        String meterId = tradeInfoDao.getMeterIdById(userId);
        splitHelp(isSplit, meterId);
    }

    @Override
    public void split1(String meterId, int isSplit) {
        splitHelp(isSplit, meterId);
    }

    private void splitHelp(Integer isSplit, String meterId) {
        Thread thread = new Thread(() -> IoTDemoPubSubDemo.changeST(isSplit, meterId));
        thread.start();
    }
}
