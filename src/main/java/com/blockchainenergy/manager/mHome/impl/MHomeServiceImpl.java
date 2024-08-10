package com.blockchainenergy.manager.mHome.impl;

import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.manager.mHome.MHomeService;
import com.blockchainenergy.manager.mHome.MMenuVO;
import com.blockchainenergy.manager.mHome.dao.MHomeDao;
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
public class MHomeServiceImpl implements MHomeService {

    @Resource
    private MHomeDao mHomeDao;

    @Override
    public List<MMenuVO> getMenus(CurrentUser currentUser) {
        return null;
    }
}
