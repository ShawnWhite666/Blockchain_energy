package com.blockchainenergy.trade.elecMeter.impl;

import com.blockchainenergy.trade.elecMeter.ElecMeter;
import com.blockchainenergy.trade.elecMeter.ElecMeterList;
import com.blockchainenergy.trade.elecMeter.ElecMeterLog;
import com.blockchainenergy.trade.elecMeter.ElectMeterService;
import com.blockchainenergy.trade.elecMeter.dao.ElecMeterDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions:
 * @data: 2022/2/5 18:59
 */
@Service
@Transactional
public class ElecMeterServiceImpl implements ElectMeterService {
    @Resource
    ElecMeterDao elecMeterDao;

    @Override
    public ElecMeterList getElecMeterLogPerson(String userID) {
        String elecMeterIdByUserId = elecMeterDao.getElecMeterIdByUserId(userID);
        List<ElecMeterLog> elecMeterLogs = elecMeterDao.getElecMeterLogByMeterId(elecMeterIdByUserId);
        List<Double> Vs = new ArrayList<>(), As = new ArrayList<>(), EFs = new ArrayList<>(), ERs = new ArrayList<>();
        int size = elecMeterLogs.size();
        for (int i = size - 1; i >= 0; i--) {
            ElecMeterLog elecMeterLog = elecMeterLogs.get(i);
            Vs.add(elecMeterLog.getV());
            As.add(elecMeterLog.getA());
            EFs.add(elecMeterLog.getEF());
            ERs.add(elecMeterLog.getER());
        }
        return new ElecMeterList(Vs, As, EFs, ERs);
    }

    @Override
    public List<ElecMeter> getAllElecMeter() {
        return elecMeterDao.getAllElecMeter();
    }
}
