package com.blockchainenergy.trade.tradeInfo;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.page.PageInfo;
import com.blockchainenergy.common.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @descriptions:
 */
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/trade/tradeInfo")//本注解说明本控制器所有路径都以。。。开头
public class TradeInfoAPI {
    @Resource
    private TradeInfoService tradeInfoService;
    @Resource
    private CommonService commonService;

    @GetMapping("/")
    public Result getTrade(GetTradeDto dto) {
        PageHelper.startPage(dto);
        return Result.success(new PageInfo<>(tradeInfoService.getTrade(dto)));
    }

    @GetMapping("/getTradeDetail")
    public Result getTradeDetail(TradeInfoDto dto) {
        return Result.success(tradeInfoService.getTradeDetail(dto));
    }

    @GetMapping("/getTimeSlot")
    public Result getTimeSlot() {
        return Result.success(tradeInfoService.getTimeSlot());
    }

    @GetMapping("/getTimeSlotPersonByDate")
    public Result getTimeSlotPersonByDate(@RequestHeader("Token") String token, TimeSlotDto dto) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        dto.setUser_id(currentUser.getUserId());
        if (dto.getTrade_time() == null) dto.setTrade_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        else dto.setTrade_time(dto.getTrade_time());
        return Result.success(tradeInfoService.getTimeSlotPersonByDate(dto));
    }

    @GetMapping("/getMyTradeEChart")
    public Result getMyTradeEChart(@RequestHeader("Token") String token, @RequestParam boolean isBuyer) {
        if (token.equals("")) return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        String userId = currentUser.getUserId();
        return Result.success(tradeInfoService.getMyTradeEChart(userId, isBuyer));
    }

    @GetMapping("/split")
    public Result split(@RequestHeader("Token") String token, @RequestParam int isSplit) {
        if (token.equals("")) return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        String userId = currentUser.getUserId();
        tradeInfoService.split(userId, isSplit);
        return Result.success();
    }

    @GetMapping("/split1")
    public Result split1(@RequestParam String meterId, @RequestParam Integer isSplit) {
        tradeInfoService.split1(meterId, isSplit);
        return Result.success();
    }
}
