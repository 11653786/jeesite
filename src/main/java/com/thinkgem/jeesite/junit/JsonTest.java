package com.thinkgem.jeesite.junit;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.util.HttpUtil;
import com.thinkgem.jeesite.vo.AccessToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        products.add(preOrderReq);


        String productsStr = JSONObject.toJSONString(products);
        System.out.println(productsStr);
        Date now=new Date();
        String result = HttpUtil.sendPostRequest("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" +"wx3bb5180e192011f3"+ "&secret=" + "5bfaf8a61e77c446501de3c181729486", true);
        AccessToken token = JSONObject.parseObject(result, AccessToken.class);
        token.setInTime(now);
        token.setInOutTime(getTwoHoursDate(now));
        token.setTokenType(0);
    }

    private static Date getTwoHoursDate(Date now) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.HOUR_OF_DAY, 2);
        return ca.getTime();
    }
}
