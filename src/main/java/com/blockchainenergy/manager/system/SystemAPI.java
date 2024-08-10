package com.blockchainenergy.manager.system;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @descriptions:
 */
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/manager/system")//本注解说明本控制器所有路径都以。。。开头
public class SystemAPI {
    @Resource
    private SystemService systemService;

    @GetMapping("/getState")
    public Result getState() {
        return Result.success(systemService.getState());
    }

    @PostMapping("/setMoneyState")
    public Result setMoneyState() {
        systemService.setMoneyState();
        return Result.success();
    }
}
