package com.thinkgem.jeesite.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.config.WechatConfig;
import com.thinkgem.jeesite.util.TenpayUtil;
import com.thinkgem.jeesite.util.WebRequestUtil;
import com.thinkgem.jeesite.util.XMLUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangtao on 2017/8/18.
 */
@Service
public class WechatPayService {

    @Autowired
    private WechatConfig wechatConfig;


    /**
     * 微信统一下单
     *
     * @param orderNo
     * @param productId
     * @param actualPayMoney
     * @return
     */
    public PlatformRes<String> unifiedorder(String orderNo, String openid, String productId, Integer actualPayMoney, String tradeType, String remark) {
        Map<String, String> resultMap = null;
        String prePayId = null;
        String result = null;
        try {
            //xml格式字符串
            Map<String, String> params = setWechatConfig();

            params.put("nonce_str", TenpayUtil.genNonceStr());
            params.put("body", remark);
            params.put("out_trade_no", orderNo);
            //货币类型
            params.put("fee_type", wechatConfig.fee_type);
//            params.put("total_fee", actualPayMoney + "");
            params.put("total_fee", "1");
            params.put("spbill_create_ip", "127.0.0.1");
            params.put("trade_type", tradeType);
            params.put("product_id", "0");
            if (tradeType.equals("NATIVE")) {     //微信扫码付款
                params.put("notify_url", wechatConfig.scan_pay_url);
            } else if (tradeType.equals("JSAPI")) {     //微信公众号付款
                params.put("openid", openid);
                params.put("notify_url", wechatConfig.js_pay_url);
            }


            String sign = TenpayUtil.createSign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key).toUpperCase();
            params.put("sign", sign);
            boolean isTrue = TenpayUtil.isTenpaySign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key);
            if (isTrue) {
                String body = XMLUtil.getXmlByMap(params);
                result = WebRequestUtil.getResponseString(wechatConfig.unifiedorder_url, body, false);
                resultMap = XMLUtil.doXMLParse(result);
                //二维码图片

                if (tradeType.equals("NATIVE")) {
                    prePayId = resultMap.get("code_url");
                    //没有生成支付信息就返回微信给的信息
                    if (StringUtils.isBlank(prePayId))
                        return PlatformRes.error(resultMap.get("return_code"), resultMap.get("return_msg"));
                    else
                        return PlatformRes.success(prePayId);
                } else {
                    prePayId = resultMap.get("prepay_id");
                    //没有生成支付信息就返回微信给的信息
                    if (StringUtils.isBlank(prePayId))
                        return PlatformRes.error(resultMap.get("err_code"), resultMap.get("err_code_des"));
                    else
                        return PlatformRes.success(JSONObject.toJSONString(resultMap));
                }


            } else
                return PlatformRes.error(ResCodeMsgType.WECHAT_SIGN_ERROR);


        } catch (Exception e) {
            throw new RuntimeException("预下单失败: " + e.getMessage());
        }
    }


    public PlatformRes<Map<String, String>> wechatJsPay(String orderNo, String openid, String productId, Integer actualPayMoney, String tradeType, String remark) {
        Map<String, String> resultMap = null;
        String prePayId = null;
        String result = null;
        try {
            //xml格式字符串
            Map<String, String> params = setWechatConfig();

            params.put("nonce_str", TenpayUtil.genNonceStr());
            params.put("body", remark);
            params.put("out_trade_no", orderNo);
            //货币类型
            params.put("fee_type", wechatConfig.fee_type);
//            params.put("total_fee", actualPayMoney + "");
            params.put("total_fee", "1");
            params.put("spbill_create_ip", "127.0.0.1");
            params.put("trade_type", tradeType);
            params.put("product_id", "0");
            params.put("openid", openid);
            params.put("notify_url", wechatConfig.js_pay_url);


            String sign = TenpayUtil.createSign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key).toUpperCase();
            params.put("sign", sign);
            boolean isTrue = TenpayUtil.isTenpaySign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key);
            if (isTrue) {
                String body = XMLUtil.getXmlByMap(params);
                result = WebRequestUtil.getResponseString(wechatConfig.unifiedorder_url, body, false);
                resultMap = XMLUtil.doXMLParse(result);
                resultMap.put("timestamp", System.currentTimeMillis() + "");
                prePayId = resultMap.get("prepay_id");
                //没有生成支付信息就返回微信给的信息
                if (StringUtils.isBlank(prePayId))
                    return PlatformRes.error(resultMap.get("err_code"), resultMap.get("err_code_des"));
                else
                    return PlatformRes.success(resultMap);


            } else
                return PlatformRes.error(ResCodeMsgType.WECHAT_SIGN_ERROR);


        } catch (Exception e) {
            throw new RuntimeException("预下单失败: " + e.getMessage());
        }
    }


    public PlatformRes<String> orderQuery(String orderNo) {
        //查询订单
        Map<String, String> params = new HashMap<String, String>();
        String result = null;
        try {
            params = setWechatConfig();
            params.put("nonce_str", TenpayUtil.genNonceStr());
            params.put("out_trade_no", orderNo);
            String sign = TenpayUtil.createSign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key).toUpperCase();
            params.put("sign", sign);
            boolean isTrue = TenpayUtil.isTenpaySign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key);
            if (isTrue) {
                String body = XMLUtil.getXmlByMap(params);
                result = WebRequestUtil.getResponseString(wechatConfig.query_order_url, body, false);
                params = XMLUtil.doXMLParse(result);
                if (params.get("result_code") != null) {
                    if (params.get("result_code").toString().equals("FAIL")) {
                        return PlatformRes.error("" + params.get("err_code"));
                    } else if (params.get("result_code").toString().equals("SUCCESS")) {
                        if (params.get("trade_state").toString().equals("SUCCESS")) {
                            return PlatformRes.success("");
                        } else
                            return PlatformRes.error(params.get("trade_state"));


                    } else {
                        return PlatformRes.error("");
                    }

                }
                return PlatformRes.success(JSON.toJSONString(params));
            } else
                return PlatformRes.error(ResCodeMsgType.WECHAT_SIGN_ERROR);


        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 微信退款,分一次退款获取结果
     *
     * @param orderNo
     * @param refundOrderNo
     * @param orderTotalFee
     * @param orderRefundFee
     */
    public PlatformRes<String> wechatRefundFee(String orderNo, String refundOrderNo, Integer orderTotalFee, Integer orderRefundFee) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        Map<String, String> result = null;
        try {
            //设置双向验证客户端
            httpclient = getHttpClient();
            //拼装参数
            String body = setRefundParams(orderNo, refundOrderNo, orderTotalFee, orderRefundFee);
            //发送http获取结果
            HttpEntity entity = getHttpEntity(httpclient, response, body);
            if (entity != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    throw new RuntimeException("微信退款失败,请求参数:" + body);
                }

                if (entity != null)
                    result = XMLUtil.doXMLParse(EntityUtils.toString(response.getEntity(), "UTF-8"));

            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (result.get("result_code").toString().equalsIgnoreCase("SUCCESS"))
            return PlatformRes.success("退款成功");
        else
            return PlatformRes.error(ResCodeMsgType.REFUND_ERROR);
    }

    /**
     * 查询退款订单信息
     *
     * @return
     */
    public PlatformRes<String> queryRefundOrder(String orderNo) throws JDOMException, IOException {
        //查询退款订单订单
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", wechatConfig.app_id);
        params.put("mch_id", wechatConfig.mch_id);
        params.put("nonce_str", TenpayUtil.genNonceStr());
        params.put("out_trade_no", orderNo);
        String sign = TenpayUtil.createSign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key).toUpperCase();
        params.put("sign", sign);
        String body = XMLUtil.getXmlByMap(params);
        String result = WebRequestUtil.getResponseString(wechatConfig.refund_order_query, body, false);
        params = XMLUtil.doXMLParse(result);
        if (params.get("result_code").equalsIgnoreCase("SUCCESS")) {
            return PlatformRes.success(body);
        } else {
            return PlatformRes.error(params.get("err_code").toString());
        }
    }


    /**
     * 获取微信退款的双向认证
     *
     * @return
     */
    private CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpclient = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(wechatConfig.refund_token));
            try {
                keyStore.load(instream, wechatConfig.mch_id.toCharArray());
            } finally {
                instream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, wechatConfig.mch_id.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        } catch (Exception e) {
            e.getMessage();
        }

        return httpclient;

    }


    /**
     * 退款请求信息转成string
     *
     * @param orderNo
     * @param refundOrderNo
     * @param orderTotalFee
     * @param orderRefundFee
     * @return
     */
    private String setRefundParams(String orderNo, String refundOrderNo, Integer orderTotalFee, Integer orderRefundFee) {
        String nonce_str = TenpayUtil.genNonceStr();

        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", wechatConfig.app_id);
        params.put("mch_id", wechatConfig.mch_id);
        params.put("nonce_str", nonce_str);
        params.put("out_trade_no", orderNo);
        params.put("out_refund_no", refundOrderNo);
        params.put("total_fee", orderTotalFee + "");
        params.put("refund_fee", orderRefundFee + "");
        String sign = TenpayUtil.createSign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key).toUpperCase();
        params.put("sign", sign);

        return XMLUtil.getXmlByMap(params);

    }

    /**
     * 获取微信退款http请求结果
     *
     * @param httpclient
     * @param response
     * @throws IOException
     */
    private HttpEntity getHttpEntity(CloseableHttpClient httpclient,
                                     CloseableHttpResponse response, String xmlbody) throws IOException {
        HttpPost httpPost = new HttpPost(wechatConfig.refund_order_url);
        //设置请求参数,并且请求

        StringEntity se = new StringEntity(xmlbody, "utf-8");
        se.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.setEntity(se);
        response = httpclient.execute(httpPost);
        return response.getEntity();
    }


    /**
     * 微信预下单参数拼装,返回xml格式字符串
     *
     * @return
     */
    public Map<String, String> setWechatConfig() {
        Map<String, String> params = new HashMap<String, String>();
        //生成预支付请求参数列表
        params.put("appid", wechatConfig.app_id);
        //商户号
        params.put("mch_id", wechatConfig.mch_id);
        return params;


    }


}
