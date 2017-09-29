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

        preOrderReq.setProductId("be8b53dcd8ec4afd80deba294af7354f");
        preOrderReq.setCabinetNo("002");
        preOrderReq.setDrawerNo("1");


        PreOrderReq preOrderReq1 = new PreOrderReq();

        preOrderReq1.setProductId("719f18d663fe4b91aee96f7c39d77236");
        preOrderReq1.setCabinetNo("002");
        preOrderReq1.setDrawerNo("2");


        PreOrderReq preOrderReq2 = new PreOrderReq();

        preOrderReq2.setProductId("719f18d663fe4b91aee96f7c39d77236");
        preOrderReq2.setCabinetNo("002");
        preOrderReq2.setDrawerNo("3");

        PreOrderReq preOrderReq3 = new PreOrderReq();

        preOrderReq3.setProductId("be8b53dcd8ec4afd80deba294af7354f");
        preOrderReq3.setCabinetNo("002");
        preOrderReq3.setDrawerNo("4");

        products.add(preOrderReq);
        products.add(preOrderReq1);
        products.add(preOrderReq2);
        products.add(preOrderReq3);


        String productsStr = JSONObject.toJSONString(products);
        System.out.println(productsStr);


    }
}
