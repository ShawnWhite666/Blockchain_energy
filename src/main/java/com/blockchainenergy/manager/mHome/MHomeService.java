package com.blockchainenergy.manager.mHome;

import com.blockchainenergy.common.CurrentUser;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:10
 */
public interface MHomeService {
    List<MMenuVO> getMenus(CurrentUser currentUser);
}
