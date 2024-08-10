package com.blockchainenergy.info.tradeState;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/info/tradeState")//本注解说明本控制器所有路径都以。。。开头
public class TradeStateAPI {
    @Resource
    private TradeStateService tradeStateService;

    @GetMapping("/getAllTradeState")
    public Result getAllTradeState() {
        return Result.success(tradeStateService.getAllTradeState());
    }
}
