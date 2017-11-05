package com.thinkgem.jeesite.junit;

import com.google.gson.Gson;
import com.thinkgem.jeesite.api.entity.req.PutFoodReq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/5.
 */
public class Test {
    public static void main(String[] args) {
        List<PutFoodReq> list = new ArrayList<PutFoodReq>();
        PutFoodReq req = new PutFoodReq();
        req.setDrawerNo("1");
        req.setCabinetNo("002");
        req.setProductId("be8b53dcd8ec4afd80deba294af7354f");
        req.setFoodPassword("111111");
        list.add(req);
        Gson gson = new Gson();
        String result = gson.toJson(list);
        System.out.println(result);
    }
}
