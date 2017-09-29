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



    }
}
