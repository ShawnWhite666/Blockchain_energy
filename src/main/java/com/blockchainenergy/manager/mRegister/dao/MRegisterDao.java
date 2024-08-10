package com.blockchainenergy.manager.mRegister.dao;

import com.blockchainenergy.manager.mRegister.MRegisterDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @descriptions:
 * @data: 2021/10/24 10:44
 */
public interface MRegisterDao {
    @Insert("insert into manager(m_id,m_password) values(#{m_id},#{m_password})")
    void insertManager(MRegisterDto dto);
    @Select("SELECT IFNULL((SELECT true from manager where m_id = #{id} limit 1),false)")
    boolean checkId(String id);
}
