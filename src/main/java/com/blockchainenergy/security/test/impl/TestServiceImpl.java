package com.blockchainenergy.security.test.impl;

import com.blockchainenergy.security.test.TestService;
import com.blockchainenergy.security.test.dao.TestDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @descriptions:
 * @data: 2021/10/23 14:19
 */

@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao homedao;

}
