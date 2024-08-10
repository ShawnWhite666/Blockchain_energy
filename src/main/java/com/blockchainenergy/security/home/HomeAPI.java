package com.blockchainenergy.security.home;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.Constants;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.utils.TokenUtils;
import com.blockchainenergy.mqtt.server.MqttService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:00
 */
@RestController
@RequestMapping("/security/home")
public class HomeAPI {
    @Resource
    private CommonService commonService;
    @Resource
    private MqttService mqttService;

    @Resource
    private HomeService homeService;

    @GetMapping("/menus")
    public Result menus(@RequestHeader(Constants.HEADER_TOKEN) String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = TokenUtils.getUserInfo(token, commonService);
        List<MenuVO> menuVOList = homeService.getMenus(currentUser);
        return Result.success(menuVOList);
    }

    @DeleteMapping("/exit")
    public Result exit(@RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        //在服务端清除缓存的token
        TokenUtils.removeToken(token);
        return Result.success();
    }


    @GetMapping("/getElecMeterIds")
    public Result getElecMeterIds() {
        return Result.success(mqttService.getElecIDs());
    }
}
