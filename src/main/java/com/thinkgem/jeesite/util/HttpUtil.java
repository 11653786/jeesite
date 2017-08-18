package com.thinkgem.jeesite.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    /**
     * @param urls
     * @param xmlData xml格式的数据
     * @return
     */
    public static String sendXmlData(String urls, String xmlData) {
        String returnXml = null;
        try {
            //使用HttpURLConnection发送http请求
            byte[] xmlbyte = xmlData.getBytes("UTF-8");
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);//允许输出
            conn.setUseCaches(false);//不使用Cache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");//维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(xmlbyte);//发送xml数据
            outStream.flush();
            outStream.close();

            //解析返回来的xml消息体
            byte[] msgBody = null;
            if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
            InputStream is = conn.getInputStream();//获取返回数据
            byte[] temp = new byte[1024];
            int n = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((n = is.read(temp)) != -1) {
                bos.write(temp, 0, n);
            }
            msgBody = bos.toByteArray();
            bos.close();
            is.close();
            returnXml = new String(msgBody, "UTF-8").trim();
            conn.disconnect();
            // 以下下游解析XML
        } catch (Exception e) {
            throw new RuntimeException("xml发送请求出错" + e.getMessage());
        }
        return returnXml;
    }


    public static String getXmlData() {
        return "<xml>\n" +
                "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "   <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>\n" +
                "   <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>\n" +
                "   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>\n" +
                "   <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>\n" +
                "   <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "</xml>";
    }


    public static void main(String[] args) {
        String result = sendXmlData("http://localhost:8080/api/notify/wechatNotify", getXmlData());
        System.out.println(result);
    }
}