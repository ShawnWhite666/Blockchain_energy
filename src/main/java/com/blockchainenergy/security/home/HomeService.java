package com.blockchainenergy.security.home;

import com.blockchainenergy.common.CurrentUser;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:10
 */
public interface HomeService {
    List<MenuVO> getMenus(CurrentUser currentUser);

    List<String> getElecMeterIds();
}
