package com.blockchainenergy.info.user.impl;

import com.blockchainenergy.info.defaultHelp.Default;
import com.blockchainenergy.info.defaultHelp.dao.DefaultDao;
import com.blockchainenergy.info.tradeState.TradeState;
import com.blockchainenergy.info.tradeState.dao.TradeStateDao;
import com.blockchainenergy.info.user.UpdateUserInfoDto;
import com.blockchainenergy.info.user.UserInfoService;
import com.blockchainenergy.info.user.dao.*;
import com.blockchainenergy.info.user.dto.GetUserTimeSlotDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private DefaultDao defaultDao;
    @Resource
    private TradeStateDao tradeStateDao;

    @Override
    public List<UserReleaseEntity> getUserReleaseInfo(String user_id) {
        List<UserReleaseEntity> entities = userInfoDao.getReleaseInfoByUserId(user_id);
        System.out.println(entities);
        for (UserReleaseEntity entity : entities) {
            entity.setTrade_state_name((entity.getRelease_state_id() ? "已" : "未") + "撮合");
            entity.setTrade_type((entity.getTrade_type().equals("0") ? "买" : "卖"));
        }
        return entities;
    }

    @Override
    public List<UserTimeSlotInfoEntity> getTimeSlot(GetUserTimeSlotDto dto) {
        return userInfoDao.getTimeSlotInfoByReleaseId(dto.getRelease_id());
    }

    @Override
    public List<UserTradeRecordInfoEntity> getBuyerRecord(String buyer) {
        return getTradeRecord(userInfoDao.getTradeRecordByBuyerId(buyer));
    }

    @Override
    public List<UserTradeRecordInfoEntity> getSellerRecord(String seller) {
        return getTradeRecord(userInfoDao.getTradeRecordBySellerId(seller));
    }

    private List<UserTradeRecordInfoEntity> getTradeRecord(List<UserTradeRecordInfoEntity> tradeRecords) {
        List<Default> allDefault = defaultDao.getAllDefault();
        List<TradeState> allTradeState = tradeStateDao.getAllTradeState();

        for (UserTradeRecordInfoEntity tradeRecord : tradeRecords) {
            Integer default_id = tradeRecord.getDefault_id();
            Integer trade_state_id = tradeRecord.getTrade_state_id();
            for (Default aDefault : allDefault) {
                if (aDefault.getDefault_id().equals(default_id)) {
                    tradeRecord.setDefault_info(aDefault.getDefault_info());
                    break;
                }
            }
            for (TradeState tradeState : allTradeState) {
                if (tradeState.getTrade_state_id().equals(trade_state_id)) {
                    tradeRecord.setTrade_state_name(tradeState.getTrade_state_name());
                    break;
                }
            }
        }
        return tradeRecords;
    }

    @Override
    public UserInfoEntity getUserInfo(String user_id) {
        return userInfoDao.getUserInfoByUserId(user_id);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoDto dto) {
        userInfoDao.updateUserInfo(dto);
    }
}
