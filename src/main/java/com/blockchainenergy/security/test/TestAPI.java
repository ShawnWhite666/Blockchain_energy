package com.blockchainenergy.security.test;

import com.blockchainenergy.common.Result;
import com.blockchainenergy.mqtt.server.MqttService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @descriptions: 测试类
 * @data: 2021/10/23 14:00
 */
@RestController
@RequestMapping("/security/test")
public class TestAPI {
    @Resource
    private TestService testService;
    @Resource
    private MqttService mqttService;

    @GetMapping("/testInsertElectricityMeterLog")
    public Result testInsertElectricityMeterLog() {
        mqttService.addMeterLog();
        return Result.success();
    }
}
