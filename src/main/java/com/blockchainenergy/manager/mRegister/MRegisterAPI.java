package com.blockchainenergy.manager.mRegister;

import com.blockchainenergy.common.Node;
import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @descriptions:
 * @data: 2021/10/24 10:36
 */

@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/manager/mRegister")//本注解说明本控制器所有路径都以。。。开头
public class MRegisterAPI {
    @Resource
    private MRegisterService registerService;

    @PostMapping("")
    public Result addUser(@RequestBody MRegisterDto dto) {
        if (!dto.getSafe_code().equals(Node.SAFE_CODE)) return Result.fail(500, "安全码错误！");
        if (registerService.checkId(dto.getM_id())) return Result.fail(500, "该账号已经被使用！");
        if (registerService.addUser(dto)) return Result.success();
        else return Result.fail(500, "添加账户失败！");
    }
}
