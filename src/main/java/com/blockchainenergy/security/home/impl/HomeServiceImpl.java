package com.blockchainenergy.security.home.impl;

import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.security.home.HomeService;
import com.blockchainenergy.security.home.MenuVO;
import com.blockchainenergy.security.home.dao.HomeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:19
 */

@Service
@Transactional
public class HomeServiceImpl implements HomeService {

    @Resource
    private HomeDao homedao;

    @Override
    public List<MenuVO> getMenus(CurrentUser currentUser) {
        return null;
    }

    @Override
    public List<String> getElecMeterIds() {
        return homedao.getElecMeterIds();
    }
}
