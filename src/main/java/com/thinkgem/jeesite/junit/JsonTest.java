package com.thinkgem.jeesite.junit;

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

        preOrderReq.setProductId("");
        preOrderReq.setCabinetNo("");
        preOrderReq.setDrawerNo("");
        preOrderReq.setProductNum(1);


        PreOrderReq preOrderReq1 = new PreOrderReq();

        preOrderReq1.setProductId("");
        preOrderReq1.setCabinetNo("");
        preOrderReq1.setDrawerNo("");
        preOrderReq1.setProductNum(1);


        PreOrderReq preOrderReq2 = new PreOrderReq();

        preOrderReq2.setProductId("");
        preOrderReq2.setCabinetNo("");
        preOrderReq2.setDrawerNo("");
        preOrderReq2.setProductNum(2);


    }
}
