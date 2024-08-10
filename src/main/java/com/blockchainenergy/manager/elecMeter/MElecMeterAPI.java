package com.blockchainenergy.manager.elecMeter;

import com.blockchainenergy.common.Result;
import com.blockchainenergy.mqtt.server.MqttService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manager/elecMeter")
public class MElecMeterAPI {

    @Resource
    private MElecMeterService MElecMeterService;
    @Resource
    private MqttService mqttService;

    // 获得电表当前数据
    @GetMapping("/info")
    public Result getAllElecMeterInfo() {
        return Result.success(MElecMeterService.getAllElecMeterInfo());
    }

    // 获得电表当前数据
    @GetMapping("/log")
    public Result getAllElecMeterLog() {
        return Result.success(MElecMeterService.getAllElecMeterLog());
    }

    @GetMapping("/getElecID")
    public Result getElecID() {
        return Result.success(mqttService.getElecIDs());
    }

    @DeleteMapping("/del")
    public Result deleteRelease(@RequestBody DelElecMeterDto deleteDto) {
        MElecMeterService.deleteElecMeter(deleteDto.getElec_meter_id());
        return Result.success("删除成功！");
    }
}
