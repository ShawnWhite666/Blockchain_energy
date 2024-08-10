package com.blockchainenergy.manager.energy;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/manager/energy")
public class ManagerEnergyAPI {

    @Resource
    private ManagerEnergyService managerEnergyService;

    @PostMapping("/create")
    public Result createEnergy(@RequestBody EnergyInfo energyInfo) {
        managerEnergyService.createEnergy(energyInfo);
        return Result.success("创建成功");
    }

    @GetMapping("/info")
    public Result getAllEnergyInfo() {
        List<EnergyInfoVO> energyInfoList = managerEnergyService.getAllEnergyInfo();
        return Result.success(energyInfoList);
    }

    @DeleteMapping("/delete")
    public Result deleteRelease(@RequestBody DeleteEnergyDto deleteDto) {
        managerEnergyService.deleteEnergy(deleteDto.getEnergy_id());
        return Result.success("删除成功！");
    }
}
