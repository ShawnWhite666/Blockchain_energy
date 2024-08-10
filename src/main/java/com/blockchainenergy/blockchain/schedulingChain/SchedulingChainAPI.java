package com.blockchainenergy.blockchain.schedulingChain;

import com.blockchainenergy.common.Result;
import com.blockchainenergy.trade.dealResult.TradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 调度链API
 */
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/schedulingChain")//本注解说明本控制器所有路径都以XX开头
public class SchedulingChainAPI {
    @Resource
    private SchedulingChainService schedulingChainService;

    @Resource
    private TradeService tradeService;

    /**
     * 制造十条然后返回这十条
     */
    @GetMapping("/getChain")
    public Result getChain() {
        tradeService.dealResult();
        return Result.success(schedulingChainService.last10Block());
    }

    /**
     * 查看最新十条
     */
    @GetMapping("/last10Block")
    public Result last10Block() {
        return Result.success(schedulingChainService.last10Block());
    }

    /**
     * 返回区块链
     */
//    @GetMapping("/chain")
//    public Result chain() throws Exception {
//        Map<String, Object> fullChainWithHash = schedulingChainService.getFullChainWithHash();
//        return Result.success(fullChainWithHash);
//    }
}
