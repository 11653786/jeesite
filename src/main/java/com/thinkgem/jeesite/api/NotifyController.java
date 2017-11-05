package com.thinkgem.jeesite.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.gson.Gson;
import com.thinkgem.jeesite.api.entity.req.PlatformReq;
import com.thinkgem.jeesite.api.entity.res.PaymentRes;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.SocketResMsgType;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.config.AlipayConfig;
import com.thinkgem.jeesite.config.WechatConfig;
import com.thinkgem.jeesite.mina.SessionMap;
import com.thinkgem.jeesite.modules.manager.ordergoods.dao.OrderGoodsDao;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.orders.dao.OrdersDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.util.TenpayUtil;
import com.thinkgem.jeesite.util.XMLUtil;
import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 支付宝和微信的回调
 * Created by yangtao on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/notify")
public class NotifyController {


    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGoodsDao orderGoodsDao;


    Logger logger = Logger.getLogger(this.getClass().getName());


    /**
     * 支付宝同步回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/alipayReturn")
    @ResponseBody
    public String alipayReturn(HttpServletRequest request) {

        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号

            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.CHARSET, "RSA2");

            if (verify_result) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码
                //该页面可做页面美工编辑

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                return "验证成功";
                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                //该页面可做页面美工编辑
                return "验证失败";
            }
        } catch (Exception e) {
            logger.info("支付宝returnUrl出错:" + e.getMessage());
            throw new RuntimeException("");
        }

    }

    /**
     * 支付宝异步充值回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/alipayNotify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request) {

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        try {

            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }


            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号

            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.CHARSET, "RSA2");

            if (verify_result) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                return "success";    //请不要修改或删除

                //////////////////////////////////////////////////////////////////////////////////////////
            } else {//验证失败
                return "fail";
            }


        } catch (Exception e) {
            logger.info("支付宝notifyUrl出错:" + e.getMessage());
            throw new RuntimeException("");
        }


    }

    /**
     * 微信扫码付回调url
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/wechatCardNotify")
    @ResponseBody
    public String wechatCardNotify(HttpServletRequest request) {
        logger.info("微信扫码付回调-----------");
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
                logger.info("扫码付回调url返回参数：" + parameter + ":" + v);
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
                    //订单号，支付类型为微信扫码付
                    orderService.cardNotify(out_trade_no);
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    List<OrderGoods> orderGoods = orderGoodsDao.findListByOrderNo(out_trade_no);
                    logger.info("扫码付成功,下发消息： 子订单数量： " + orderGoods.size());
                    String cabinetNo = null;
                    String drawerNos = "";
                    for (OrderGoods orderGood : orderGoods) {
                        cabinetNo = orderGood.getCabinetNo();
                        drawerNos = drawerNos + "," + orderGood.getDrawerNo();
                    }

                    SessionMap sessionMap = SessionMap.newInstance();
                    Gson gson = new Gson();
                    logger.info("下发消息：cabinetNo: " + cabinetNo + "drawerNos" + drawerNos);
                    String notifyMessage=gson.toJson(PlatformRes.success(new PaymentRes(cabinetNo, drawerNos, SocketResMsgType.WECHAT_PAYMENT_TYPE.code())));
                            SessionMap.sendMessage(cabinetNo,notifyMessage+ PlatformReq.aite);

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
            logger.info("微信notifyUrl出错:" + e.getMessage());
            throw new RuntimeException("");
        }


    }


    /**
     * 微信公众号支付回调url
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/wechatJsPayNotify")
    @ResponseBody
    public String wechatJsPayNotify(HttpServletRequest request) {
        logger.info("微信公众号支付回调-----------");
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
                logger.info("微信公众号回调url返回参数：" + parameter + ":" + v);
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
                    //订单号，支付类型为微信扫码付
                    orderService.cardNotify(out_trade_no);
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
            logger.info("微信notifyUrl出错:" + e.getMessage());
            throw new RuntimeException("");
        }


    }


}
