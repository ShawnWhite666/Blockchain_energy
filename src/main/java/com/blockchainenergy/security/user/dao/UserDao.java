package com.blockchainenergy.security.user.dao;

import com.blockchainenergy.security.user.User;
import com.blockchainenergy.security.user.UserDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/21 0:02
 */
public interface UserDao {

    @Select("select * from users")
    List<User> findUserList();

    List<User> findUserListByCondition(UserDto dto);
}
