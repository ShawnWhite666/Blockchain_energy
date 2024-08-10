package com.blockchainenergy.blockchain.dataChain.impl;

import com.blockchainenergy.blockchain.dataChain.DataBlock;
import com.blockchainenergy.blockchain.dataChain.DataChainService;
import com.blockchainenergy.blockchain.dataChain.Transaction;
import com.blockchainenergy.blockchain.dataChain.dao.DataChainDao;
import com.blockchainenergy.common.JsonFormatTool;
import com.blockchainenergy.common.ServerConfig;
import com.blockchainenergy.common.utils.ByteUtils;
import com.blockchainenergy.common.utils.RocksDBUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions:
 * @data: 2021/11/4 23:45
 */
@Service
@Transactional
public class DataChainServiceImpl implements DataChainService {
    @Resource
    DataChainDao dataChainDao;

    /**
     * 创建json文件
     */
    @Override
    public Double createJsonFile(String jsonString) {
        // 标记文件生成是否成功
        boolean flag = true;
        double ans = 0;
        // 拼接文件完整路径
        String filePath = ".";
        String fileName = "blockJson";
        String fullPath = filePath + File.separator + fileName + ".json";
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            // 格式化json字符串
            jsonString = JsonFormatTool.formatJson(jsonString);
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        File file = new File(fullPath);
        if (file.exists()) ans = (double) file.length() / 1024;
        // 返回是否成功的标记
        return ans;
    }

    /**
     * 获取主机IP和端口号
     **/
    @Resource
    private ServerConfig serverConfig;

    /**
     * 全部区块
     **/
    @Override
    public List<DataBlock> getFullChain() {
        List<DataBlock> list = new ArrayList();
        RocksDBUtils rdb = RocksDBUtils.getInstance();
        String blockHash = rdb.getLastDataBlockHash();
        while (true) {
            if (blockHash.length() == 0 || blockHash.equals(ByteUtils.ZERO_HASH)) break;
            DataBlock dataBlock = rdb.getDataBlock(blockHash);
            list.add(dataBlock);
            blockHash = dataBlock.getPrevious_hash();
        }
        return list;
    }

    /**
     * 返回十个区块
     */
    @Override
    public List<DataBlock> getLast10Chain() {
        List<DataBlock> list = new ArrayList<>();
        RocksDBUtils rdb = RocksDBUtils.getInstance();
        String blockHash = rdb.getLastDataBlockHash();
        for (int i = 0; i < 10; i++) {
            if (blockHash.length() == 0 || blockHash.equals(ByteUtils.ZERO_HASH)) break;
            DataBlock block = rdb.getDataBlock(blockHash);
            list.add(block);
            blockHash = block.getPrevious_hash();
        }
        return list;
    }

    /**
     * 生成十个区块并返回
     */
    @Override
    public List<DataBlock> getChain() {
        List<Transaction> trades = dataChainDao.get10Trade();
        for (Transaction trade : trades) {
            DataBlock.newBlock(trade);
        }
        return getLast10Chain();
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 把节点注册进来，注册的时候就自动验证加入链了
     * @Param [dto]
     **/
//    @Override
//    public Map<String, Object> nodesRegister(NodesRegisterDto dto) throws Exception {
//        List<String> nodes = dto.getNodes();
//        // 注册节点
//        DataChain blockChain = DataChain.getInstance();
//        for (String node : nodes) {
//            blockChain.registerNode(node);
//        }
//        // 自己与邻居的一致
//        resolve();
//        // 向客户端返回处理结果
//        Map<String, Object> response = new HashMap<String, Object>();
//        response.put("message", "New nodes have been added");
//        response.put("total_nodes", blockChain.getNodes().toArray());
//        return response;
//    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 把邻居节点的链拿来比对验证
     * @Param []
     **/
//    @Override
//    public Map<String, Object> resolve() throws Exception {
//        DataChain blockChain = DataChain.getInstance();
//        boolean replaced = blockChain.resolveConflicts();
//        Map<String, Object> response = new HashMap<String, Object>();
//        if (replaced) {
//            response.put("message", "Our chain was replaced");
//            response.put("new_chain", blockChain.getChain());
//        } else {
//            response.put("message", "Our chain is authoritative");
//            response.put("chain", blockChain.getChain());
//        }
//        return response;
//    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 更新Node的邻居节点-和自己的链对比
     * @Param [node]
     **/
//    @Override
//    public Map<String, Object> updateByNode(String node) throws Exception {
//        DataChain blockChain = DataChain.getInstance();
//        boolean replaced = updateByNodeHelper(node);
//        Map<String, Object> response = new HashMap<String, Object>();
//        if (replaced) {
//            response.put("message", "Our chain was replaced");
//            response.put("new_chain", blockChain.getChain());
//        } else {
//            response.put("message", "Our chain is authoritative");
//            response.put("chain", blockChain.getChain());
//        }
//        return response;
//    }

    /**
     * @return boolean
     * @Description 如果node节点的链比自己更长就更新自己的链, 配合广播机制，向下广播
     * @Param [node]
     **/
//    public boolean updateByNodeHelper(String node) throws Exception {
//        DataChain blockChain = DataChain.getInstance();
//        Set<String> neighbours = blockChain.getNodes();
//        List<DataBlock> newChain = null;
//        // 寻找最长的区块链
//        long maxLength = blockChain.getChain().size();
//        // 获取并验证网络中的所有节点的区块链
//        URL url = new URL("http://" + node + "/blockChain/chain");
//        System.out.println("url:" + url);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.connect();
//        if (connection.getResponseCode() == 200) {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuffer responseData = new StringBuffer();
//            String response = null;
//            while ((response = bufferedReader.readLine()) != null) {
//                responseData.append(response);
//            }
//            bufferedReader.close();
//            // 刷新当前链
//            JSONObject jsonData = JSONObject.parseObject(responseData.toString()).getJSONObject("data");
//            long length = jsonData.getLong("length");
//            JSONArray jsonArray = jsonData.getJSONArray("chain");
//            List<DataBlock> chain = new ArrayList<>();
//            for (int i = 0; i < jsonArray.size(); i++) {
//                chain.add(JSONObject.parseObject(jsonArray.getJSONObject(i).toJSONString(), new TypeReference<DataBlock>() {
//                }));
//            }
//
//            // 检查长度是否长，链是否有效
//            if (length > maxLength && blockChain.validChain(chain)) {
//                maxLength = length;
//                newChain = chain;
//            }
//        }
//        // 如果发现一个新的有效链比我们的长，就替换当前的链
//        if (newChain != null) {
//            blockChain.setChain(newChain);
//            //链更新了就广播给其他链
//            notifyAllObservers();
//            return true;
//        }
//        return false;
//    }

    /**
     * 当区块改动的时候，调动邻居节点的共识算法，实现共识机制
     * 用到了观察者模式
     *
     * @param
     * @return 如果链被取代返回true, 否则返回false
     * @throws IOException
     */
//    public boolean notifyAllObservers() throws Exception {
//        DataChain blockChain = DataChain.getInstance();
//        Set<String> neighbours = blockChain.getNodes();
//        boolean ans = true;
//        String thisUrl = serverConfig.getUrl();
//        // 通知邻居都来看这个节点的链并验证更新自己的链
//        for (String node : neighbours) {
//            //调用共识算法
//            URL url = new URL("http://" + node + "/blockChain/nodes/updateByNode/" + thisUrl);
//            System.out.println("url:" + url);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//            if (connection.getResponseCode() == 200) {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuffer responseData = new StringBuffer();
//                String response = null;
//                while ((response = bufferedReader.readLine()) != null) {
//                    responseData.append(response);
//                }
//                bufferedReader.close();
//            }
//            if (ans || connection.getResponseCode() != 200) {
//                ans = false;
//            }
//        }
//        return ans;
//    }
}
