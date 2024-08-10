package com.blockchainenergy.security.user;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/20 23:56
 */
public interface UserService {
    List<User> getUserList();

    List<User> getUserList(UserDto dto);
}
