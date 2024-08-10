package com.blockchainenergy.manager.system.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @descriptions:
 * @data: 2022/3/27 23:12
 */
public interface SystemDao {
    @Select("select entry_state from system_entry where entry_name='新用户体验金'")
    boolean getMoneyState();
    @Update("update system_entry set entry_state=1-entry_state where entry_name='新用户体验金'")
    void setMoneyState();
}
