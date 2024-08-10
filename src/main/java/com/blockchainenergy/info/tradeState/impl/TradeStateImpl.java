package com.blockchainenergy.info.tradeState.impl;

import com.blockchainenergy.info.tradeState.TradeState;
import com.blockchainenergy.info.tradeState.TradeStateService;
import com.blockchainenergy.info.tradeState.dao.TradeStateDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 */
@Service
@Transactional
public class TradeStateImpl implements TradeStateService {
    @Resource
    private TradeStateDao tradeStateDao;

    @Override
    public List<TradeState> getAllTradeState() {
        return tradeStateDao.getAllTradeState();
    }
}
