package com.blockchainenergy.common.utils;

import com.blockchainenergy.common.page.PageParam;
import com.blockchainenergy.common.page.QueryAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static <T> Map<String, Object> getPage(PageParam pageParam, QueryAction<T> queryAction) {
        //执行映射器方法之前立即执行分页参数（页码和每页记录数）的设置
        PageHelper.startPage(pageParam);

        List<T> list = queryAction.execute();

        //执行映射器方法之后，立即执行PageInfo对象的创建
        PageInfo<T> pageInfo = new PageInfo<T>(list);

        Map<String, Object> page = new HashMap<>();
        page.put("current", pageInfo.getPageNum());//当前页
        page.put("pageSize", pageInfo.getPageSize());//每页最大记录数
        page.put("total", pageInfo.getTotal());//总记录数
        page.put("pages", pageInfo.getPages());//总页数
        page.put("size", pageInfo.getSize());//当前页实际记录数
        page.put("list", pageInfo.getList());//当前页的数据记录
        page.put("first", 1);
        page.put("pre", pageInfo.getPrePage());
        page.put("next", pageInfo.getNextPage());
        page.put("last", pageInfo.getPages());

        return page;
    }

    /**
     * 将一个Java对象转换为json串，并向浏览器（客户端）输出
     *
     * @param resp 响应对象
     * @param obj  需要转化为json串的java对象
     * @throws IOException
     */
    public static void outJson(HttpServletResponse resp, Object obj) throws IOException {
        //json格式的媒体标准：application/json
        resp.setContentType("application/json;charset=UTF-8");//设置字符编码
        PrintWriter out = resp.getWriter();//获取向客户端发送字符信息流对象
        // 将list集合对象转化为json格式字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(obj);
        out.print(jsonString);

        out.flush();
        out.close();
    }

    public static String generateHashCode(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
