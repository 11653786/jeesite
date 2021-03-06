package com.thinkgem.jeesite.mina;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thinkgem.jeesite.api.entity.req.PlatformReq;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.api.entity.req.PutFoodReq;
import com.thinkgem.jeesite.api.entity.res.CabinetDrawerFoodStatusRes;
import com.thinkgem.jeesite.api.entity.res.CabinetPasswordRes;
import com.thinkgem.jeesite.api.entity.res.CabinetWorkTimeRes;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.service.CabinetProductRelactionService;
import com.thinkgem.jeesite.modules.manager.drawer.service.DrawerService;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import com.thinkgem.jeesite.service.CabinetHttpLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServerHandler extends IoHandlerAdapter {

    private static final Log log = LogFactory.getLog(ServerHandler.class);


    @Autowired
    private OrderService orderService;
    @Autowired
    private DrawerService drawerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CabinetProductRelactionService cabinetProductRelactionService;
    @Autowired
    private CabinetHttpLogService cabinetHttpLogService;
    @Autowired
    private CabinetService cabinetService;


    /**
     * mina服务器接收消息的方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("服务端接收到的对象: " + message);
        String content = message.toString();
        log.info("服务端接收到的数据为: " + content);
        //获取客户端信息
        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
        //柜子端口号
        Integer port = address.getPort();
        //柜子ip
        String ip = address.getAddress().getHostAddress();
        String ips = ip + ":" + port;
//        log.info("柜子ip:" + ips + "服务端接收到的数据为: " + content);
        String result = null;
        Gson gson = new Gson();
        SessionMap sessionMap = SessionMap.newInstance();
        try {
            content = content.replace("Host:47.95.114.60", "");
            Map<String, Object> params = getMap(content);
            String data = params.get("data").toString();

            //判断当前map
            if (params != null && params.size() > 0) {
                if (data.equals("1")) { //下单
                    String tradeType = null;
                    String productsStr = params.get("productStr").toString();

                    Integer paymentType = Integer.valueOf(params.get("paymentType").toString());
//                    String repackgeId = params.get("repackgeId").toString();
                    List<PreOrderReq> products = JSONObject.parseArray(productsStr, PreOrderReq.class);
                    if (paymentType == 0) {
                        tradeType = "NATIVE";
                    }

                    PlatformRes<String> results = null;
                    //缓存防止多次提交的问题
                    if (getSessionKey(session, tradeType, productsStr)) {
                        setSessionKey(session, tradeType, productsStr);
                        results = orderService.preorder(products, paymentType, tradeType, null);
                        removeSessionKey(session, tradeType, productsStr);
                    } else {
                        results = new PlatformRes<String>(ResCodeMsgType.ORDER_SUBMIT_SUBMITS.code(), data);
                        removeSessionKey(session, tradeType, productsStr);
                    }

                    results.setMessage(data);
                    result = JSONObject.toJSONString(results);
                } else if (data.equals("2")) {    //取餐,通过订单密码
                    String cabinetNo = params.get("cabinetNo").toString();
                    String putPassword = params.get("putPassword").toString();
                    PlatformRes<String> results = orderService.outFood(cabinetNo, putPassword);
                    results.setMessage(data);
                    result = gson.toJson(results);
                } else if (data.equals("3")) {  //获取商品列表
                    Product product = new Product();
                    product.setProductStatus(1 + "");
                    result = gson.toJson(PlatformRes.success(data, productService.findListByInterface(product)));
                } else if (data.equals("4")) {   //工作人员放餐接口
                    String putFoodReqs = params.get("list").toString();
                    List<PutFoodReq> list = gson.fromJson(putFoodReqs, new TypeToken<List<PutFoodReq>>() {
                    }.getType());
                    for (PutFoodReq req : list) {
                        PlatformRes<String> results = drawerService.putFood(req.getProductId(), req.getFoodPassword(), req.getCabinetNo(), req.getDrawerNo());
                        results.setMessage(data);
                        result = gson.toJson(results);
                    }

                } else if (data.equals("5")) { //根据柜子编号获取当前柜子抽屉和商品的关系
                    String cabinetNo = params.get("cabinetNo").toString();
                    CabinetProductRelaction cabinetProductRelaction = new CabinetProductRelaction();
                    cabinetProductRelaction.setCabinetNo(cabinetNo);
                    result = gson.toJson(PlatformRes.success(data, cabinetProductRelactionService.findListByInterface(cabinetProductRelaction)));
                } else if (data.equals("6")) { //柜子通信是否正常接口
                    String cabinetNo = params.get("cabinetNo").toString();
                    String temperature = params.get("temperature").toString();
                    Integer isSuccess = cabinetHttpLogService.saveOrUpdateCabinetLog(cabinetNo,temperature);
                    if (isSuccess == 0) {
                        result = gson.toJson(PlatformRes.error(data, data));
                        if (SessionMap.getSession(cabinetNo) != null) {
                            SessionMap.removeSession(cabinetNo);
                        }
                    } else {   //保存客户端的会话session
                        if (SessionMap.getSession(cabinetNo) != null) {
                            sessionMap.removeSession(cabinetNo);
                        }
                        sessionMap.addSession(cabinetNo, session);
                        result = gson.toJson(PlatformRes.success(data, data));
                    }
                } else if (data.equals("7")) { //获取柜子密码
                    String cabinetNo = params.get("cabinetNo").toString();
                    CabinetPasswordRes cabinetPasswordRes = cabinetService.getPassByCabinetNo(cabinetNo);
                    result = gson.toJson(PlatformRes.success(data, cabinetPasswordRes));
                } else if (data.equals("8")) {  //获取柜子工作时间
                    String cabinetNo = params.get("cabinetNo").toString();
                    CabinetWorkTimeRes cabinetWorkTimeRes = cabinetService.getWorkTimeByCabinetNo(cabinetNo);
                    result = gson.toJson(PlatformRes.success(data, cabinetWorkTimeRes));
                } else if (data.equals("9")) {     //根据柜子编号获取抽屉的放餐状态,放餐状态心跳
                    String cabinetNo = params.get("cabinetNo").toString();
                    List<CabinetDrawerFoodStatusRes> list = cabinetProductRelactionService.findDrawerFoodStatusList(cabinetNo);
                    result = gson.toJson(PlatformRes.success(data, list));
                }
            }
        } catch (Exception e) {
            log.info("接口请求异常：" + e.getMessage());
            if (message != null) {
                result = gson.toJson(PlatformRes.error("501", "接口请求参数异常：params: " + content + ",异常信息:" + e.getMessage()));
            } else {
                result = gson.toJson(PlatformRes.error("502", "接口请求参数异常：params: " + message + ",异常信息:" + e.getMessage()));
            }
        }
        session.write(result + PlatformReq.aite);
        super.messageReceived(session, message);

    }


    /**
     * 服务器发送消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.debug("服务端发送信息成功: " + message.toString());
        super.messageSent(session, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.debug("服务端发送异常: " + cause.getMessage());
        super.exceptionCaught(session, cause);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.debug("服务端与客户端创建连接...");
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("服务端与客户端连接打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.debug("服务端与客户端连接关闭...");
        SessionMap.removeSession(session);
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.debug("服务端进入空闲状态: " + session.getIdleCount(status));
        super.sessionIdle(session, status);
    }

    private static Map<String, Object> getMap(String receive) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(receive)) {
            if (null != receive) {
                String[] param = receive.split("&");
                for (int i = 0; i < param.length; i++) {
                    int index = param[i].indexOf('=');
                    params.put(param[i].substring(0, index), param[i].substring((index + 1)));
                }
            }
        }
        return params;
    }


    public void setSessionKey(IoSession session, String tradeType, String product) {
        session.setAttribute(tradeType + "," + product, tradeType + "," + product);
    }

    public boolean getSessionKey(IoSession session, String tradeType, String product) {
        Object object = session.getAttribute(tradeType + "," + product);
        if (object == null)
            return true;
        return false;
    }

    public void removeSessionKey(IoSession session, String tradeType, String product) {
        session.removeAttribute(tradeType + "," + product);
    }
}
