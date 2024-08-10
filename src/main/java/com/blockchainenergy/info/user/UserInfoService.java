package com.blockchainenergy.info.user;

import com.blockchainenergy.info.user.dao.UserInfoEntity;
import com.blockchainenergy.info.user.dao.UserReleaseEntity;
import com.blockchainenergy.info.user.dao.UserTimeSlotInfoEntity;
import com.blockchainenergy.info.user.dao.UserTradeRecordInfoEntity;
import com.blockchainenergy.info.user.dto.GetUserTimeSlotDto;

import java.util.List;

public interface UserInfoService {

    List<UserReleaseEntity> getUserReleaseInfo(String user_id);

    List<UserTimeSlotInfoEntity> getTimeSlot(GetUserTimeSlotDto dto);

    List<UserTradeRecordInfoEntity> getBuyerRecord(String buyer);

    List<UserTradeRecordInfoEntity> getSellerRecord(String seller);

    UserInfoEntity getUserInfo(String user_id);

    void updateUserInfo(UpdateUserInfoDto dto);
}
