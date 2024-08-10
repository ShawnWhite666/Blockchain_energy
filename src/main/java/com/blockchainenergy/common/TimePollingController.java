package com.blockchainenergy.common;

import com.blockchainenergy.mqtt.server.MqttService;
import com.blockchainenergy.trade.dealResult.TradeService;
import com.blockchainenergy.trade.dealmatching.DealMatchingService;
import com.blockchainenergy.trade.release.TradeReleaseDto;
import com.blockchainenergy.trade.release.TradeReleaseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * 固定时间执行事件
 */
@Component
//@EnableScheduling
public class TimePollingController {
    // 测试买方
    public static final String user1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCUIARoqpKdmN3b+5DOZPv+4r22a1LTx43+ZhccXI4LTTe3M2PufJVq1n3a5w4dNFel2chEDNLdTpV4skHOh//YowQc3RraFdD0Ggi0nQTft+8utYOAfGp+6eQh8T79y/IxF7Tma3oFjsU9I6ipdDOS4HJBT8Zh1SXGLRfbL4a2dQIDAQAB";
    public static final String user2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDuT/kRBKh2p+Z+bpu/HR6923Q6JGhBr90pZ0BrtzeBW2OmWseanRvLX5q0LLRCusJZtBd0fTfHZCcDwXGeGXZBi6uqh4fRwDXDd2XyGOgfTTB7Q7shIYO2TDBCBJSKDnQoqCAs7cmOAhXbdIlNCmXhfiiVc9vBh6TQzInfDgpQnQIDAQAB";

    @Resource
    private DealMatchingService dealMatchingService;

    @Resource
    private MqttService mqttService;

    @Resource
    private TradeReleaseService tradeReleaseService;
    @Resource
    private TradeService tradeService;

    @Scheduled(cron = "0 50 * * * ?")// 每小时：50 撮合
    public void executeMatching() {
        LocalDate date = LocalDate.now();
        int hour = LocalTime.now().getHour() + 1;
        if (hour >= 24) {
            date = date.plusDays(1);
            hour %= 24;
        }
        dealMatchingService.executeMatching(date, hour);
    }

    @Scheduled(cron = "*/1 * * * * *") // 每秒 更新电表图表信息
    public void addMeterLog() {
        mqttService.addMeterLog();
    }

    @Scheduled(cron = "0 0 */1 * * ?")// 每1小时 模拟 用户发布交易
    public void insertNewTrade() {
        int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);// 当前小时
        // 时间限制
        int startTime = hour + 2;
        int endTime = 24;
        if (startTime >= endTime) return;

        // 随机交易数据
        int trade_type = new Random().nextDouble() > 0.5 ? 1 : 0;// 0 买 1 卖
        double total_elec = ((int) (new Random().nextDouble() * 100));
        double price = new Random().nextDouble() * 100;

        int r = new Random().nextInt(2);
        String sellerID, buyerID;
        if (r == 0) {
            buyerID = user1;
            sellerID = user2;
        } else {
            buyerID = user2;
            sellerID = user1;
        }

        if (trade_type == 1) {// 卖
            tradeReleaseService.insertNewTrade(new TradeReleaseDto(sellerID, 0, 1, total_elec, startTime, endTime, price, new Date()));
        } else {
            tradeReleaseService.insertNewTrade(new TradeReleaseDto(buyerID, 0, 0, total_elec, startTime, endTime, price, new Date()));
        }
    }

    @Scheduled(cron = "0 0 * * * ?")// 整点 调度
    public void dealResult() {
        tradeService.dealResult();
    }
}
