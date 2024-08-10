package com.blockchainenergy.security.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.根包：springboot启动类所在的包
 * 2.spring容器管理的组件都应该定义在根包下，或者根包的子包下。
 * 3.
 */
@Slf4j
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/security/user")//本注解说明本控制器所有路径都以。。。开头
public class UserAPI {
//    此注解表示引入依赖对象
    @Resource
    private UserService userService;

    @RequestMapping("/user-list")
    public List<User> userList(){
        return userService.getUserList();
    }

    @RequestMapping("/user-query")
    public List<User> userList(UserDto dto){
        log.info("访问了接口/user-query");
        return userService.getUserList(dto);
    }
}
