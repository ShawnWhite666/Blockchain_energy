package com.blockchainenergy.info.user.dao;

import com.blockchainenergy.info.user.UpdateUserInfoDto;

import java.util.List;

public interface UserInfoDao {
    List<UserReleaseEntity> getReleaseInfoByUserId(String user_id);

    List<UserTimeSlotInfoEntity> getTimeSlotInfoByReleaseId(String release_id);

    List<UserTradeRecordInfoEntity> getTradeRecordByBuyerId(String buyer);

    List<UserTradeRecordInfoEntity> getTradeRecordBySellerId(String seller);

    UserInfoEntity getUserInfoByUserId(String user_id);

    void updateUserInfo(UpdateUserInfoDto dto);

    Double getUserBalance(String user_id);
}
