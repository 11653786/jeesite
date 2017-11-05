package com.thinkgem.jeesite.mina;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.api.entity.req.PutFoodReq;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
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
import java.util.Date;
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


    /**
     * mina服务器接收消息的方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String content = message.toString();
        //获取客户端信息
        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
        //柜子端口号
        Integer port = address.getPort();
        //柜子ip
        String ip = address.getAddress().getHostAddress();
        String ips = ip + ":" + port;
        log.debug("柜子ip:" + ips + "服务端接收到的数据为: " + content);
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

                    PlatformRes<String> results = orderService.preorder(products, paymentType, tradeType, null);
                    result = StringUtils.isNotBlank(results.getData())?results.getData():results.getCode();
                } else if (data.equals("2")) {    //取餐,通过订单密码
                    String cabinetNo = params.get("cabinetNo").toString();
                    String putPassword = params.get("putPassword").toString();
                    result = gson.toJson(orderService.outFood(cabinetNo, putPassword));
                } else if (data.equals("3")) {  //获取商品列表
                    Product product = new Product();
                    product.setProductStatus(1 + "");
                    result = gson.toJson(PlatformRes.success(productService.findList(product)));
                } else if (data.equals("4")) {   //工作人员放餐接口
                    String putFoodReqs = params.get("list").toString();
                    List<PutFoodReq> list = gson.fromJson(putFoodReqs, new TypeToken<List<PutFoodReq>>() {
                    }.getType());
                    for (PutFoodReq req : list) {
                        result = gson.toJson(drawerService.putFood(req.getProductId(), req.getFoodPassword(), req.getCabinetNo(), req.getDrawerNo()));
                    }

                } else if (data.equals("5")) { //根据柜子编号获取当前柜子抽屉和商品的关系
                    String cabinetNo = params.get("cabinetNo").toString();
                    CabinetProductRelaction cabinetProductRelaction = new CabinetProductRelaction();
                    cabinetProductRelaction.setCabinetNo(cabinetNo);
                    result = gson.toJson(PlatformRes.success(cabinetProductRelactionService.findList(cabinetProductRelaction)));
                } else if (data.equals("6")) { //柜子通信是否正常接口
                    String cabinetNo = params.get("cabinetNo").toString();
                    Integer isSuccess = cabinetHttpLogService.saveOrUpdateCabinetLog(cabinetNo);
                    if (StringUtils.isBlank(cabinetNo)) {
                        cabinetNo = "1";
                    }
                    if (isSuccess == 0) {
                        result = gson.toJson(PlatformRes.error(ResCodeMsgType.HTTP_LOG_ERROR.code(), ResCodeMsgType.HTTP_LOG_ERROR.desc()));
                        SessionMap.removeSession(cabinetNo);
                    } else {   //保存客户端的会话session
                        sessionMap.addSession(cabinetNo, session);
                        result = gson.toJson(PlatformRes.success("http通信操作成功"));
                    }
                }
            }
        } catch (Exception e) {
            log.info("接口请求异常：" + e.getMessage());
            result = gson.toJson(PlatformRes.error("500", "消息异常：" + e.getMessage()));
        }
        session.write(result);
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
}
