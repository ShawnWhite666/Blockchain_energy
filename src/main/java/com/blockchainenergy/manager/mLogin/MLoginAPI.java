package com.blockchainenergy.manager.mLogin;

import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.utils.MTokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/manager/mLogin")//本注解说明本控制器所有路径都以。。。开头
public class MLoginAPI {

    @Resource
    private MLoginService mLoginService;

    @PostMapping("")
    public Result login(@RequestBody MLoginDto dto) {
        System.out.println(dto);
        int ok = mLoginService.checkValid(dto);
        switch (ok) {
            case -1:
                return Result.fail(500, "账号不存在");
            case -2:
                return Result.fail(500, "账号和密码不匹配");
        }
        // 生成一个登陆令牌
        String token = MTokenUtils.loginSign(dto.getM_id(), dto.getM_password());
        return Result.success((Object) token);
    }
}
