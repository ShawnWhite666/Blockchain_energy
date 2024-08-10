package com.blockchainenergy.info.market.impl;

import com.blockchainenergy.info.market.InfoMarket;
import com.blockchainenergy.info.market.InfoMarketService;
import com.blockchainenergy.info.market.ManagerTradeInfo;
import com.blockchainenergy.info.market.UserTradeInfo;
import com.blockchainenergy.info.market.dao.InfoMarketDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class InfoMarketServiceImpl implements InfoMarketService {
    @Resource
    InfoMarketDao infoMarketDao;

    @Override
    public void refreshMarketInfo(Integer timeSlot) {
        Integer t = infoMarketDao.getTradingVolumesByTime(timeSlot);
        Double ma = infoMarketDao.getMaxPriceByTime(timeSlot);
        Double mi = infoMarketDao.getMinPriceByTime(timeSlot);
        Double av = infoMarketDao.getAverageByTime(timeSlot);
        if (t == null || ma == null || mi == null || av == null) {
            return;
        }
        InfoMarket.add(timeSlot, t, ma, mi, av);
    }

    @Override
    public UserTradeInfo getUserTradeInfo() {
        Integer total_num = infoMarketDao.getTotalTradeNum();
        Integer match_num = infoMarketDao.getMatchTradeNum();
        Integer no_match_num = total_num - match_num;
        Long trade_amount = infoMarketDao.getTradeAmount();
        UserTradeInfo userTradeInfo = new UserTradeInfo(total_num, match_num, trade_amount);
        return userTradeInfo;
    }

    @Override
    public ManagerTradeInfo getManagerTradeInfo() throws ParseException {
        Integer num_of_users = infoMarketDao.getUserNum();
        Integer match_num = infoMarketDao.getMatchTradeNum();

        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date star = dft.parse("2021-12-01");
        Long dayNum = (new Date().getTime() - star.getTime()) / 24 / 60 / 60 / 1000;

        return new ManagerTradeInfo(num_of_users, match_num, dayNum.intValue());
    }
}
