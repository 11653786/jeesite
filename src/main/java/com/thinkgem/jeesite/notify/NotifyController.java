package com.thinkgem.jeesite.notify;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.config.WechatConfig;
import com.thinkgem.jeesite.modules.wx.wxpay.TenpayUtil;
import com.thinkgem.jeesite.modules.wx.wxpay.base.util.tenpay.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by yangtao on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/notify")
public class NotifyController {


    @Autowired
    private WechatConfig wechatConfig;



    Logger logger = Logger.getLogger(this.getClass().getName());


    @RequestMapping(value = "/alipayNotify")
    @ResponseBody
    public String alipayNotify() {
        return "success";
    }

    /**
     * 微信回调url
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/wechatNotify")
    @ResponseBody
    public String wechatNotify(HttpServletRequest request) {
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();

        try {
            inputStream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();

            //解析xml成map
            Map<String, String> m = new HashMap<String, String>();
            m = XMLUtil.doXMLParse(sb.toString());

            //过滤空 设置 TreeMap
            Map<String, String> packageParams = new HashMap<String, String>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);

                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }



            //判断签名是否正确
            boolean isTenpaySign = TenpayUtil.isTenpaySign(packageParams, "UTF-8", "MD5", wechatConfig.app_key);
            logger.info("传入参数!" + JSONObject.toJSONString(packageParams) + ",结果: " + isTenpaySign);
            if (isTenpaySign) {
                //------------------------------
                //处理业务开始
                //------------------------------
                String resXml = "";
                if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                    // 这里是支付成功
                    //////////执行自己的业务逻辑////////////////
                    String mch_id = (String) packageParams.get("mch_id");
                    String openid = (String) packageParams.get("openid");
                    String is_subscribe = (String) packageParams.get("is_subscribe");
                    String out_trade_no = (String) packageParams.get("out_trade_no");

                    String total_fee = (String) packageParams.get("total_fee");

                    logger.info("mch_id:" + mch_id);
                    logger.info("openid:" + openid);
                    logger.info("is_subscribe:" + is_subscribe);
                    logger.info("out_trade_no:" + out_trade_no);
                    logger.info("total_fee:" + total_fee);

                    //////////执行自己的业务逻辑////////////////

                    logger.info("支付成功");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                } else {
                    logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }
                //------------------------------
                //处理业务完毕
                //------------------------------
//                BufferedOutputStream out = new BufferedOutputStream(
//                        response.getOutputStream());
//                out.write(resXml.getBytes());
//                out.flush();
//                out.close();
                return "success";
            } else {
                logger.info("通知签名验证失败");
                return null;
            }
        } catch (Exception e) {
            return null;
        }


    }
}
