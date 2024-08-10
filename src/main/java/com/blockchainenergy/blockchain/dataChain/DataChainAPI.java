package com.blockchainenergy.blockchain.dataChain;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/dataChain")//本注解说明本控制器所有路径都以XX开头
public class DataChainAPI {
    @Resource
    private DataChainService dataChainService;

    /**
     * 一键生成10个区块并返回10个区块
     */
    @GetMapping("/getChain")
    public Result getChain() {
        return Result.success(dataChainService.getChain());
    }


    /**
     * 返回区块链带hash
     */
    @GetMapping("/last10chain")
    public Result last10chain() {
        return Result.success(dataChainService.getLast10Chain());
    }


    /**
     * 测试出块数据随出块量的变化
     *
     * @param dataBlockDto
     * @throws IOException
     */
//    @GetMapping("/testThroughput")
//    public Result testThroughput(DataBlockDto dataBlockDto) throws Exception {
//        int num = dataBlockDto.getNum();
//        int num_tran = dataBlockDto.getNum_of_transaction();
//        System.out.println("block_num:" + num);
//        long start = System.currentTimeMillis();
//        Date startDate = new Date();
//        for (int i = 0; i < num; i++) {
//            List<Contract> cs = new ArrayList<>();
//            for (int j = 0; j < num_tran; j++) {
//                cs.add(new Contract("buyer", "seller", 35.0, 10.0, "buyerReleaseID", "sellerReleaseID"));
//            }
//            dataChainService.makeNewTransactionListBlockchain(cs);
//        }
//        // 创造新交易
//        long end = System.currentTimeMillis();
//        Date endDate = new Date();
//        double diff = (double) (end - start) / 1000;
//        String diffs = diff + "second";// 总耗时
//        String tps = num / diff + "blocks/second";// tps
//        Map<String, Object> fullChain = dataChainService.getFullChain();
//        Double jsonFile = dataChainService.createJsonFile(dataChainService.getFullChainWithHash().toString());
//        String jsonMb = jsonFile + "Kb";
//        return Result.success(new Throughoutput(num, num_tran, startDate, endDate, diffs, tps, jsonMb, fullChain));
//    }

    /**
     * 测试一个块的出块时间
     *
     * @param dataBlockDto
     * @throws Exception
     */
//    @GetMapping("/testOneBlock")
//    public Result testOneBlock(DataBlockDto dataBlockDto) throws Exception {
//        String buyer = dataBlockDto.getBuyer();
//        String seller = dataBlockDto.getSeller();
//        Double price = dataBlockDto.getPrice();
//        Double contract_elec = dataBlockDto.getContract_elec();
//        String buyer_release_id = dataBlockDto.getBuyer_release_id();
//        String seller_release_id = dataBlockDto.getSeller_release_id();
//        String block_hash = dataBlockDto.getBlock_hash();
//        String trade_id = dataBlockDto.getTrade_id();
//        Date trade_time = dataBlockDto.getTrade_time();
//
//        long start = System.currentTimeMillis();
//        String starts = "测试开始时间：" + new Date();
//        // 创造新交易
//        dataChainService.makeNewTransactionBlockchain(new Contract(buyer, seller, price, contract_elec, buyer_release_id, seller_release_id));
//        // 挖一个区块
//        long end = System.currentTimeMillis();
//        String ends = "测试结束时间：" + new Date();
////        new SimpleDateFormat("mm分钟ss秒").format
//        String diffs = "测试一共花费时间:" + (double) (end - start) / 100 + "秒";
//
//        return Result.success(new TestThroughputParam(diffs, block_hash, trade_id, trade_time));
//    }
}
