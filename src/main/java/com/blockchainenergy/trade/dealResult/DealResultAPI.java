package com.blockchainenergy.trade.dealResult;

import com.blockchainenergy.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 调试
 */
@Slf4j
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/trade/dealResult")//本注解说明本控制器所有路径都以。。。开头
public class DealResultAPI {
    @Resource
    private TradeService tradeService;

    // 模拟违约处理十条
    @GetMapping("/")
    public Result dealResult() {
        return Result.success(tradeService.dealResult());
    }
}
