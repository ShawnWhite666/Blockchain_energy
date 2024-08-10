package com.blockchainenergy.manager.mLogin.dao;

import com.blockchainenergy.manager.mLogin.MLoginDto;
import org.apache.ibatis.annotations.Select;

/**
 * @descriptions:
 * @data: 2021/10/23 12:42
 */
public interface MLoginDao {
    @Select("select count(m_id) from manager where m_id = #{m_id}")
    int CountPublicKey(MLoginDto dto);
    @Select("select count(m_id) from manager where m_id = #{m_id} and m_password=#{m_password}")
    int CheckPassword(MLoginDto dto);
}
