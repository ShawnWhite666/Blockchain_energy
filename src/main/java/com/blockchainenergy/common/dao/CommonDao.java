package com.blockchainenergy.common.dao;

import com.blockchainenergy.common.model.Manager.Manager;
import com.blockchainenergy.common.model.User.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


public interface CommonDao {

    @Select("select user_id userId from user where user_id = #{account_number}")
    User findUserById(String userId);
    @Select("select m_id userId,m_password userPwd from manager where m_id = #{account_number}")
    Manager findManagerById(String userId);
}
