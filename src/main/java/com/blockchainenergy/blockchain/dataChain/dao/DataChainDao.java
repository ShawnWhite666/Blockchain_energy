package com.blockchainenergy.blockchain.dataChain.dao;

import com.blockchainenergy.blockchain.dataChain.Transaction;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/11/4 23:45
 */
@MapperScan
public interface DataChainDao {
    List<Transaction> get10Trade();
}
