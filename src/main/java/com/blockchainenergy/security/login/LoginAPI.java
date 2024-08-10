package com.blockchainenergy.security.login;

import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.utils.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @descriptions:
 * @data: 2021/10/23 12:31
 */

@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/security/login")//本注解说明本控制器所有路径都以。。。开头
public class LoginAPI {

    @Resource
    private LoginService loginService;

    @PostMapping("")
    public Result login(@RequestBody LoginDto dto) {
        int ok = loginService.checkValid(dto);
        switch (ok){
            case -1:return Result.fail(520,"公钥不存在");
            case -2:return Result.fail(520,"公私钥不匹配");
        }
        String public_key = dto.getPublic_key();
        // 生成一个登陆令牌
        String token = TokenUtils.loginSign(public_key, TokenUtils.PASSWORD);
        Login login = loginService.getLogin(public_key);
        login.setToken(token);
        return Result.success(login);
    }
}
