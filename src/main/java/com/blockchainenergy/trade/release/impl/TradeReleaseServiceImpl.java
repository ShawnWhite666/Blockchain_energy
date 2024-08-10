package com.blockchainenergy.trade.release.impl;

import com.blockchainenergy.trade.release.*;
import com.blockchainenergy.trade.release.dao.TradeReleaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 * @data: 2022/2/5 18:59
 */
@Service
@Transactional
public class TradeReleaseServiceImpl implements TradeReleaseService {
    @Resource
    private TradeReleaseDao tradeReleaseDao;

    @Override
    public void insertNewTrade(TradeReleaseDto dto) {
        tradeReleaseDao.insertNewRelease(dto);
        Integer start_time = dto.getStart_time();
        Integer end_time = dto.getEnd_time();
        String release_id = dto.getRelease_id();
        Integer input_type_id = dto.getInput_type_id();
        if (input_type_id == 0) {
            double v = dto.getTotal_elec() / (end_time - start_time);
            while (start_time < end_time) {
                TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
                timeSlotInfo.setTime_slot(start_time);
                timeSlotInfo.setRelease_id(release_id);
                timeSlotInfo.setElec_value(v);
                start_time++;
                tradeReleaseDao.insertNewTimeSlot(timeSlotInfo);
            }
        } else if (input_type_id == 1) {
            int i = 0;
            List<Double> elec_values = dto.getElec_value();
            while (start_time < end_time) {
                TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
                timeSlotInfo.setTime_slot(start_time);
                timeSlotInfo.setRelease_id(release_id);
                timeSlotInfo.setElec_value(elec_values.get(i++));
                start_time++;
                tradeReleaseDao.insertNewTimeSlot(timeSlotInfo);
            }
        } else if (input_type_id == 2) {
            int i = 0;
            List<Double> elec_uppers = dto.getElec_upper();
            List<Double> elec_lowers = dto.getElec_lower();
            while (start_time < end_time) {
                TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
                timeSlotInfo.setTime_slot(start_time);
                timeSlotInfo.setRelease_id(release_id);
                timeSlotInfo.setElec_upper(elec_uppers.get(i));
                timeSlotInfo.setElec_lower(elec_lowers.get(i++));
                start_time++;
                tradeReleaseDao.insertNewTimeSlot(timeSlotInfo);
            }
        }

    }

    @Override
    public List<ReleaseInfo> getReleaseDeal(GetReleaseDto dto) {
        List<ReleaseInfo> releaseInfos = tradeReleaseDao.getReleaseDeal(dto);
        for (ReleaseInfo releaseInfo : releaseInfos) {
            releaseInfo.setTrade_type_name(releaseInfo.getTrade_type() == 0 ? "买入" : "卖出");
            releaseInfo.setRelease_state(releaseInfo.getRelease_state_id() == 0 ? "未撮合" : "已撮合");
        }
        return releaseInfos;
    }

    @Override
    public void deleteRelease(String release_id) {
        tradeReleaseDao.deleteReleaseInfoByReleaseId(release_id);
    }
}
