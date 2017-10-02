package com.thinkgem.jeesite.junit;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangtao on 2017/9/29.
 */
public class JsonTest {
    public static void main(String[] args) throws Exception {
        List<PreOrderReq> products = new ArrayList<PreOrderReq>();
        PreOrderReq preOrderReq = new PreOrderReq();

        preOrderReq.setProductId("719f18d663fe4b91aee96f7c39d77236");
        preOrderReq.setCabinetNo("002");
        preOrderReq.setDrawerNo("1");



        String productsStr = JSONObject.toJSONString(products);
        System.out.println(productsStr);


    }
}
