package com.blockchainenergy.info.market;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/info/market")
public class InfoMarketAPI {
    @Resource
    private InfoMarketService infoMarketService;

    @GetMapping("/userTradeInfo")
    public Result userTradeInfo() {
        return Result.success(infoMarketService.getUserTradeInfo());
    }

    @GetMapping("/managerTradeInfo")
    public Result managerTradeInfo() throws ParseException {
        return Result.success(infoMarketService.getManagerTradeInfo());
    }
}
