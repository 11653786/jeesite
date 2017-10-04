package com.thinkgem.jeesite.junit;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.util.HttpUtil;
import com.thinkgem.jeesite.vo.AccessToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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


//        String productsStr = JSONObject.toJSONString(products);
//        System.out.println(productsStr);
//        Date now=new Date();
//        String result = HttpUtil.sendPostRequest("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" +"wx3bb5180e192011f3"+ "&secret=" + "5bfaf8a61e77c446501de3c181729486", true);
//        AccessToken token = JSONObject.parseObject(result, AccessToken.class);
//        token.setInTime(now);
//        token.setInOutTime(getTwoHoursDate(now));
//        token.setTokenType(0);
        String accessToken = "q0OyqaEQMBqwm4ExjCLjw5o5xk5L_Wf4XLa5LMxAbiy3y9keWBBDYQqldgVqny0tHbvchAz9wvHrEFq49JGFpHuVhJ85pH7cARYlv-0zPWTbo-HnKNvpYKB_FE8a_lkqHMDeACAOTK";
        String menu = "{button :[{type : view ,name : 今日歌曲 ,key : V1001_TODAY_MUSIC},{name : 菜单 ,sub_button :[{type : view ,name : 搜索,url : http://www.soso.com/ },{type : miniprogram ,name : wxa ,url : http://mp.weixin.qq.com ,appid : wx286b93c14bbf93aa ,pagepath : pages/lunar/index },{type : click ,name : 赞一下我们,key : V1001_GOOD }]}]}";

        createMenu(accessToken, menu);

    }

    private static Date getTwoHoursDate(Date now) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.HOUR_OF_DAY, 2);
        return ca.getTime();
    }


    public static String createMenu(String access_token, String menu) {

        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(menu.getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            return "返回信息" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "createMenu 失败";
    }

}
