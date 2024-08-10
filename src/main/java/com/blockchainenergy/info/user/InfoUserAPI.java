package com.blockchainenergy.info.user;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.page.PageInfo;
import com.blockchainenergy.common.page.PageParam;
import com.blockchainenergy.common.utils.TokenUtils;
import com.blockchainenergy.info.user.dao.UserReleaseEntity;
import com.blockchainenergy.info.user.dao.UserTimeSlotInfoEntity;
import com.blockchainenergy.info.user.dao.UserTradeRecordInfoEntity;
import com.blockchainenergy.info.user.dto.GetUserTimeSlotDto;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/info/user")
public class InfoUserAPI {
    @Resource
    private CommonService commonService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取个人的发布信息
     *
     * @param token
     * @return 交易发布信息
     */
    @GetMapping("/release")
    public Result getUserReleaseInfo(PageParam pageParam, @RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        PageHelper.startPage(pageParam);
        List<UserReleaseEntity> releaseList = userInfoService.getUserReleaseInfo(currentUser.getUserId());
        return Result.success(new PageInfo<>(releaseList));
    }

//    @GetMapping("/release1")
//    public Result getUserReleaseInfo1(){
//        List<UserReleaseVO> releaseList = userInfoService.getUserReleaseInfo(TokenUtils.testId);
//        return Result.success(releaseList);
//    }

    /**
     * 获取某个发布信息的时间槽信息
     *
     * @param
     * @return 时间槽信息
     */
    @GetMapping("/timeSlot")
    public Result getUserTimeSlot(GetUserTimeSlotDto getUserTimeSlotDto) {
        PageHelper.startPage(getUserTimeSlotDto);
        List<UserTimeSlotInfoEntity> timeSlot = userInfoService.getTimeSlot(getUserTimeSlotDto);
        return Result.success(new PageInfo<>(timeSlot));
    }

    /**
     * 获取个人作为买方的交易记录
     *
     * @param token
     * @return 交易记录
     */
    @GetMapping("/buy")
    public Result getUserBoughtRecord(PageParam pageParam, @RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        PageHelper.startPage(pageParam);
        List<UserTradeRecordInfoEntity> recordList = userInfoService.getBuyerRecord(currentUser.getUserId());
        return Result.success(new PageInfo<>(recordList));
    }
//    @GetMapping("/buy1")
//    public Result getUserBoughtRecord1(){
//        List<UserTradeRecordInfoEntity> recordList = userInfoService.getBuyerRecord(TokenUtils.testId);
//        return Result.success(recordList);
//    }

    /**
     * 获取个人作为卖方的交易记录
     *
     * @param token
     * @return 交易记录
     */
    @GetMapping("/sell")
    public Result getUserSoldRecord(PageParam pageParam, @RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        PageHelper.startPage(pageParam);
        List<UserTradeRecordInfoEntity> recordList = userInfoService.getSellerRecord(currentUser.getUserId());
        return Result.success(new PageInfo<>(recordList));
    }
//    @GetMapping("/sell1")
//    public Result getUserSoldRecord1(){
//        List<UserTradeRecordInfoEntity> recordList = userInfoService.getSellerRecord(TokenUtils.testId);
//        return Result.success(recordList);
//    }


    /**
     * 获取用户个人信息
     *
     * @param token
     * @return 个人信息
     */
    @GetMapping("/user")
    public Result getUserInfo(@RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        return Result.success(userInfoService.getUserInfo(currentUser.getUserId()));
    }

    /**
     * 修改个人信息
     *
     * @param dto
     * @param token
     * @return
     */
    @PostMapping("/update")
    public Result updateUserInfo(@RequestBody UpdateUserInfoDto dto, @RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        dto.setUser_id(currentUser.getUserId());
        userInfoService.updateUserInfo(dto);
        return Result.success("修改成功！");
    }
}
