package com.blockchainenergy.info.memory;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.utils.TokenUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/info/memory")
public class MemoryAPI {
    @Resource
    private CommonService commonService;

    @Resource
    private MemoryService memoryService;

    @GetMapping("/userWalletLog")
    public Result getUserWalletLog(@RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        String userId = TokenUtils.getUserInfo(token, commonService).getUserId();
        return Result.success(memoryService.getUserWalletLog(userId));
    }

    // 获得单一用户历史电表数据
    @GetMapping("/userMeterLog")
    public Result getUserMeterLog(@RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        String userId = TokenUtils.getUserInfo(token, commonService).getUserId();
        return Result.success(memoryService.getUserMeterLog(userId));
    }

    // 获得所有电表当前数据
    @GetMapping("/getAllMeterInfo")
    public Result getAllMeterInfo() {
        return Result.success(memoryService.getAllMeterLog());
    }
}
