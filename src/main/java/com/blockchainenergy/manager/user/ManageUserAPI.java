package com.blockchainenergy.manager.user;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/manager/user")
public class ManageUserAPI {

    @Resource
    private ManagerUserService managerUserService;

    @GetMapping("/info")
    public Result getAllUsersInfo() {
        List<ManageUserInfo> userInfoList = managerUserService.getUsersInfo();
        return Result.success(userInfoList);
    }

    @GetMapping("/balance")
    public Result getUserBalance(@RequestParam String user_id) {
        UserBalanceDto balance = managerUserService.getUserBalance(user_id);
        String message = "";
        if (balance != null) {
            message = "查询余额成功！";
        } else {
            message = "该用户不存在！";
        }
        return Result.success(message, balance);
    }
}
