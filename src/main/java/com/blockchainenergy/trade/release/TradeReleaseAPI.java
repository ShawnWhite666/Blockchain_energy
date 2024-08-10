package com.blockchainenergy.trade.release;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.page.PageInfo;
import com.blockchainenergy.common.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 交易发布
 **/
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/trade/release")//本注解说明本控制器所有路径都以。。。开头
public class TradeReleaseAPI {
    @Resource
    private TradeReleaseService tradeReleaseService;

    @Resource
    private CommonService commonService;


    @PostMapping("/releaseDeal")
    public Result ReleaseDeal(@RequestBody TradeReleaseDto dto, @RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        // 检查时间
        int start_time = dto.getStart_time();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getDate());
        int today = calendar.get(Calendar.DATE);
        calendar.setTime(new Date());
        int nowToday = calendar.get(Calendar.DATE);
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (nowToday == today && start_time < (nowHour + 1)) {
            return Result.fail(Result.TIME_ERROR, "交易时间段应该在当前时间之后，请检查交易时间段！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        dto.setUser_id(currentUser.getUserId());
        tradeReleaseService.insertNewTrade(dto);
        return Result.success();
    }

    //    @PostMapping("/releaseDeal1")
//    public Result ReleaseDeal1(@RequestBody TradeReleaseDto dto) {
//        int start_time = dto.getStart_time();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(dto.getDate());
//        int today = calendar.get(Calendar.DATE);
//        calendar.setTime(new Date());
//        int nowToday = calendar.get(Calendar.DATE);
//        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
//        if(nowToday==today&&start_time<(nowHour+1)){
//            return Result.fail(Result.TIME_ERROR,"交易时间段应该在当前时间之后！");
//        }
//        dto.setUser_id(testId);
//        tradeReleaseService.insertNewTrade(dto);
//        return Result.success();
//    }

    /**
     * 获取所有交易信息
     **/
    @GetMapping("/getRelease")
    public Result getRelease(GetReleaseDto dto) {
        PageHelper.startPage(dto);
        List<ReleaseInfo> releaseDeal = tradeReleaseService.getReleaseDeal(dto);
        return Result.success(new PageInfo<>(releaseDeal));
    }

    /**
     * 撤销（删除）发布信息
     *
     * @param deleteDto
     * @return
     */
    @DeleteMapping("/delete")
    public Result deleteRelease(@RequestBody DeleteReleaseDto deleteDto) {
        tradeReleaseService.deleteRelease(deleteDto.getRelease_id());
        return Result.success("撤销成功！");
    }
}
