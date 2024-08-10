package com.blockchainenergy.security.register;

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
@RequestMapping("/security/register")//本注解说明本控制器所有路径都以。。。开头
public class RegisterAPI {
    @Resource
    private RegisterService registerService;

    @PostMapping("")
    public Result addUser(@RequestBody RegisterDto dto) {
//        // 检测手机号
//        if (registerService.checkPhone(dto.getPhone_number())) {
//            return Result.fail(500, "该手机号已经被使用");
//        }
//        // 检测身份证
//        if (registerService.checkIdNumber(dto.getId_number())) {
//            return Result.fail(500, "该身份证已经被使用");
//        }
        return Result.success(registerService.addUser(dto));
    }
}
