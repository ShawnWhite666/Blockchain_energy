package com.blockchainenergy.security.user.impl;

import com.blockchainenergy.security.user.User;
import com.blockchainenergy.security.user.UserDto;
import com.blockchainenergy.security.user.UserService;
import com.blockchainenergy.security.user.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/20 23:59
 */

@Service //业务组件
@Transactional //所有方法事务性
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> getUserList() {
        return userDao.findUserList();
    }

    @Override
    public List<User> getUserList(UserDto dto) {
        return userDao.findUserListByCondition(dto);
    }
}
