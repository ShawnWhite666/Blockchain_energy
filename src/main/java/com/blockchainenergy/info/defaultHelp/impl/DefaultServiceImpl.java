package com.blockchainenergy.info.defaultHelp.impl;

import com.blockchainenergy.info.defaultHelp.Default;
import com.blockchainenergy.info.defaultHelp.DefaultService;
import com.blockchainenergy.info.defaultHelp.dao.DefaultDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DefaultServiceImpl implements DefaultService {
    @Resource
    private DefaultDao defaultDao;

    @Override
    public List<Default> getAllDefault() {
        return defaultDao.getAllDefault();
    }
}
