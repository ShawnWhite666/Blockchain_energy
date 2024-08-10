package com.blockchainenergy.trade.dealmatching;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * 该接口仅用于测试
 */
@RestController
@RequestMapping("/trade/deal_match")
public class DealMatchingAPI {

    @Resource
    public DealMatchingService dealMatchingService;

    /**
     * 测试接口，用于测试交易撮合的执行
     *
     * @param hour 小时，即执行撮合的时间点，而实际撮合的是hour+2时间槽的交易
     */
    @GetMapping("")
    public Result dealMatching(@RequestParam Integer year, @RequestParam Integer month,
                               @RequestParam Integer day, @RequestParam Integer hour) {
        LocalDate testDate = LocalDate.of(year, month, day);
        dealMatchingService.executeMatching(testDate, hour);
        return Result.success("测试成功");
    }
}
