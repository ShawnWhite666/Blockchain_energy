package com.blockchainenergy.common;

import com.blockchainenergy.info.market.InfoMarketService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用于项目启动时运行方法
 */
@Component
@Order(value = 1)// 启动几次
public class AfterRunner implements ApplicationRunner {
    @Resource
    InfoMarketService infoMarketService;

    @Override
    public void run(ApplicationArguments args) {
        initMarketService();// 初始化市场数据
    }

    private void initMarketService() {
        int hours = new Date().getHours();
        for (int i = 0; i <= hours; i++) {
            infoMarketService.refreshMarketInfo(i);
        }
    }
}
