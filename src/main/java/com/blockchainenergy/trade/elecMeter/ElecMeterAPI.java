package com.blockchainenergy.trade.elecMeter;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.utils.TokenUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 交易发布
 **/
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/trade/electMeter")//本注解说明本控制器所有路径都以。。。开头
public class ElecMeterAPI {
    @Resource
    private ElectMeterService electMeterService;
    @Resource
    private CommonService commonService;

    @GetMapping("/getElecMeterLogPerson")
    public Result getElecMeterLogPerson(@RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        String userId = currentUser.getUserId();
        return Result.success(electMeterService.getElecMeterLogPerson(userId));
    }

    @GetMapping("/getAllElecMeter")
    public Result getAllElecMeter() {
        return Result.success(electMeterService.getAllElecMeter());
    }
}
