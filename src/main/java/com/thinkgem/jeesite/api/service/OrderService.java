package com.thinkgem.jeesite.api.service;

import com.thinkgem.jeesite.api.entity.handler.CabinerDrawerHandler;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao.CabinetProductRelactionDao;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.ordergoods.dao.OrderGoodsDao;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.orders.dao.OrdersDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.modules.manager.orders.service.OrdersService;
import com.thinkgem.jeesite.modules.manager.product.dao.ProductDao;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.dao.UserRedpacketRelactionDao;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;
import com.thinkgem.jeesite.service.OrderLogService;
import com.thinkgem.jeesite.util.TenpayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 给外部提供的订单接口
 * Created by yangtao on 2017/9/29.
 */
@Service
public class OrderService {


    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DrawerDao drawerDao;
    @Autowired
    private CabinetProductRelactionDao cabinetProductRelactionDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private UserRedpacketRelactionService userRedpacketRelactionService;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserRedpacketRelactionDao userRedpacketRelactionDao;
    @Autowired
    private AlipayService alipayService;

    Logger logger = Logger.getLogger(this.getClass().getName());


    /**
     * 微信公众号下单
     *
     * @return
     */
    @Transactional(readOnly = false)
    public PlatformRes<Orders> wechatPublicPreorder(String[] ids, String[] nums, String cabinetId, String red, String openid) {

        Orders orders = new Orders();

        if (ids == null || nums == null || ids.length < 1 || nums.length < 1 || StringUtils.isBlank(cabinetId) || openid == null)
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);
        List<Product> products = productDao.getProductByList(ids);

        orders.setOpenid(openid);

        UserRedpacketRelaction userRedpacketRelaction = null;
        if (StringUtils.isNotBlank(red)) {
            userRedpacketRelaction = userRedpacketRelactionService.get(red);
        }

        if (products != null && !products.isEmpty()) {
            //查询当前柜子配置的所有商品

            Integer totalPrice = 0;
            for (int a = 0; a < products.size(); a++) {
                Product product = products.get(a);
                List<Drawer> list = drawerDao.getDrawerBuy(product.getId(), cabinetId);
                if (list.size() < Integer.valueOf(nums[a]))
                    return PlatformRes.error("503", product.getProductName() + ",套餐只剩" + list.size() + "份");
                else {
                    totalPrice = totalPrice + product.getProductActualPrice();

                }

            }

            // 微信公众号下单
            String orderNo = TenpayUtil.getCurrTime();


            //取餐密码设置
            String putPassword = (int) ((Math.random() * 9 + 1) * 100000) + "";
            orders.setPutPassword(putPassword);
            orders.setPayMoney(totalPrice);
            //使用红包,实付金额的判断
            if (userRedpacketRelaction != null) {
                orders.setActualPayMoney(totalPrice - userRedpacketRelaction.getRedpacketPrice());
                orders.setRedpacketId(userRedpacketRelaction.getId());
                orders.setRedpacketName(userRedpacketRelaction.getRedpacketName());
                orders.setRedpacketPrice(userRedpacketRelaction.getRedpacketPrice());
            } else
                orders.setActualPayMoney(totalPrice);

            orders.setOrderStatus(0);
            // 0,微信扫码支付 1,微信公众号支付 2,支付宝
            orders.setPaymentStatus(1);
            orders.setCreateTime(new Date());
            orders.setWechatTradeNo(orderNo);
            String cabinetNo = "";
            List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();

            for (int a = 0; a < products.size(); a++) {
                Integer num = Integer.valueOf(nums[a]);
                for (int b = 0; b < num; b++) {
                    Product product = products.get(a);
                    List<Drawer> list = drawerDao.getDrawerBuy(product.getId(), cabinetId);
                    Drawer drawer = list.get(b);
                    OrderGoods orderGoods = new OrderGoods();
                    orderGoods.setOrderNo(orderNo);
                    orderGoods.setProductId(product.getId());
                    orderGoods.setProductName(product.getProductName());
                    orderGoods.setProductPrice(product.getProductActualPrice());
                    orderGoods.setProductNum(1);
                    orderGoods.setAreaId(drawer.getAreaId());
                    orderGoods.setAreaName(drawer.getCabinetAreaName());
                    orderGoods.setCabinetNo(drawer.getCabinetNo());
                    orderGoods.setDrawerNo(drawer.getDrawerNo());
                    orderGoods.setCreateTime(new Date());
                    //设置订单柜子信息
                    orders.setCabinetNo(orderGoods.getCabinetNo());
                    //生成id
                    orderGoods.preInsert();
//                    orderGoodsDao.insert(orderGoods);

                    orderGoodsList.add(orderGoods);
                    //锁定柜子5分钟
                    drawerDao.lockOrUnlockStatus(drawer.getId(), 4);

                    orderNo = orderNo + drawer.getDrawerNo();
                    cabinetNo = drawer.getCabinetNo();

                }

            }


            for (OrderGoods orderGoods : orderGoodsList) {
                orderGoods.setOrderNo(orderNo + cabinetNo);
                orderGoodsDao.insert(orderGoods);
            }

            orders.setOrderNo(orderNo + cabinetNo);
            orders.preInsert();
            ordersDao.insert(orders);

            //这里要下单通知微信锁定5分钟

        }

        return PlatformRes.success(orders);

    }


    /**
     * @param products
     * @param paymentStatus 支付类型: 0,微信扫码支付 1,微信公众号支付 2,支付宝
     * @param tradeType
     * @param repackgeId    用户红包表的id
     * @return
     */
    public PlatformRes<String> preorder(List<PreOrderReq> products, Integer paymentStatus, String tradeType, String repackgeId) {


        //参数验证
        if (products == null || products.isEmpty())
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);
        //订单号
        String orderNo = TenpayUtil.getCurrTime();
        String cabinetNo = "";
        if (products != null && !products.isEmpty()) {
            for (PreOrderReq product : products) {
                cabinetNo = product.getCabinetNo();
                orderNo = orderNo + product.getDrawerNo();
            }
            orderNo = orderNo + cabinetNo;
        }

        //商品id
        String productIds = "";
        //支付总金额
        Integer productTotalPrice = 0;

        for (PreOrderReq productReq : products) {

            //如果验证通过(区域,商品信息填充)
            if (StringUtils.isBlank(productReq.getCabinetNo())) {
                return PlatformRes.error("传入参数柜子编号有错误!");
            }

            if (StringUtils.isBlank(productReq.getDrawerNo())) {
                return PlatformRes.error("传入参数抽屉编号有为空的");
            }


            if (StringUtils.isBlank(productReq.getProductId())) {
                return PlatformRes.error("传入参数商品ID为空!");
            }

            Product product = productService.get(productReq.getProductId());


            PlatformRes<String> validResult = validOrderInfo(productReq, product);


            if (!validResult.getCode().equals("0"))
                return validResult;
            productIds = product.getId() + "," + productIds;
            productTotalPrice = product.getProductActualPrice() + productTotalPrice;
        }


        //这里要创建订单,订单状态为0,等到回调通过以后更改状态,微信预约下单生成二维码
        PlatformRes<String> wechatAlipayPayResult = null;
        UserRedpacketRelaction userRedpacketRelaction = null;
        if (StringUtils.isNotBlank(repackgeId)) {
            //这里判断下红包使用
            userRedpacketRelaction = userRedpacketRelactionDao.get(repackgeId);
        }


        if (paymentStatus == 0) {
            String remark = userRedpacketRelaction == null ? "" : "使用红包优惠: " + (userRedpacketRelaction.getRedpacketPrice() / 100) + "元";
            wechatAlipayPayResult = wechatPayService.unifiedorder(orderNo, null, productIds, productTotalPrice, tradeType, remark);
            //预支付id成功,生成订单
            if (!wechatAlipayPayResult.getCode().equals("0")) {
                return wechatAlipayPayResult;
            }

        } else if (paymentStatus == 1) { //公众号支付

        } else {       //支付宝扫码付
            wechatAlipayPayResult = alipayService.unifiedorder(orderNo, productIds, productTotalPrice, "牛上山快餐下單");
        }

        //------全部验证通过保存订单和订单明细--------------------------------------
        ordersService.submitForOrder(orderNo, paymentStatus, products, productTotalPrice, userRedpacketRelaction);
        return wechatAlipayPayResult;


    }


    private PlatformRes<String> validOrderInfo(PreOrderReq productReq, Product product) {


        if (product == null)
            return PlatformRes.error(ResCodeMsgType.PRODUCT_NOT_EXISTS);
        //商品价格
        productReq.setGetProductActualPrice(product.getProductActualPrice());
        //拼接商品id和设置商品金额


        //商品下架判断
        if (product.getProductStatus().equals("0"))
            return PlatformRes.error(ResCodeMsgType.PRODUCT_NOT_USE);

        //判断柜子抽屉是否存在
        CabinerDrawerHandler cabinerDrawerHandler = drawerDao.findCabinetAndDrawerInfo(productReq.getCabinetNo(), productReq.getDrawerNo());
        if (cabinerDrawerHandler == null)
            return PlatformRes.error(ResCodeMsgType.CABINET_DRAWER_EXISTS);

        //柜子是否可用
        if (!cabinerDrawerHandler.getDrawerStatus().equals("1"))
            return PlatformRes.error(ResCodeMsgType.DRAWER_NOT_ACTION);

        //柜子是否放餐,1.已放餐
        if (!cabinerDrawerHandler.getFoodStatus().equals("1"))
            return PlatformRes.error(ResCodeMsgType.DRAWER_NOT_PUT_FOOD);


        //设置区域信息给后面订单用,节省查询数据库次数
        productReq.setAreaId(cabinerDrawerHandler.getAreaId());
        productReq.setAreaName(cabinerDrawerHandler.getAreaName());
        productReq.setProductName(product.getProductName());
        productReq.setProductNum(1);
        //判断当前柜子是否可以放当前的商品
        CabinetProductRelaction cabinetProductRelaction = cabinetProductRelactionDao.findByDrawerNoAndProductId(productReq.getCabinetNo(), productReq.getDrawerNo(), productReq.getProductId());
        if (cabinetProductRelaction == null)
            return PlatformRes.error(ResCodeMsgType.DRAWER_NOT_PUT_PRODUCT);


        return PlatformRes.success(null);
    }

    public PlatformRes<Orders> queryOrder(String orderNo) {
        Orders orders = ordersDao.getOrdersByOrderNo(orderNo);
        if (orders == null)
            return PlatformRes.error(ResCodeMsgType.ORDERS_NOT_EXISTS);
        /**
         *
         */
        if (StringUtils.isBlank(orders.getWechatTradeNo())) {
            //支付宝订单查询
            return null;
        } else {
            //订单号
            PlatformRes<String> result = wechatPayService.orderQuery(orderNo);
            if (result.getCode().equals("0")) {
                return PlatformRes.success(orders);
            } else {
                //订单号出错
                return PlatformRes.error(result.getCode(), result.getData(), orders);
            }
        }
    }


    /**
     * 退单,微信和支付宝
     *
     * @param orderNo
     * @return
     */
    public PlatformRes<String> refundOrder(String orderNo) {
        Orders orders = ordersDao.getOrdersByOrderNo(orderNo);
        if (orders == null)
            return PlatformRes.error(ResCodeMsgType.ORDERS_NOT_EXISTS);
        if (StringUtils.isBlank(orders.getWechatTradeNo()) && StringUtils.isNotBlank(orders.getAlipayTradeNo())) {
            //支付宝订单查询
            return null;
        } else {
            if (orders.getRefundStatus() != null && (orders.getRefundStatus() == 0 || orders.getRefundStatus() == 2)) {
                String refundOrderNo = StringUtils.isBlank(orders.getRefundNo()) ? TenpayUtil.getCurrTime() : orders.getRefundNo();
                PlatformRes<String> result = wechatPayService.wechatRefundFee(orderNo, refundOrderNo, orders.getActualPayMoney(), orders.getActualPayMoney());
                orders.setRefundNo(refundOrderNo);
                if (result.getCode().equals("0")) {
                    //退款成功
                    orders.setRefundStatus(1);
                    ordersDao.update(orders);
                    return PlatformRes.success("退款成功");
                } else {
                    //退款失败
                    orders.setRefundStatus(2);
                    ordersDao.update(orders);
                    return PlatformRes.error(ResCodeMsgType.REFUND_ERROR);
                }


            } else
                return PlatformRes.error(ResCodeMsgType.REFUND_ORDERS_NOT_EXISTS);

        }

    }

    /**
     * 查詢退款訂單,微信和支付宝
     *
     * @param orderNo
     * @return
     */
    public PlatformRes<String> queryRefundOrder(String orderNo) {
        Orders orders = ordersDao.getOrdersByOrderNo(orderNo);
        try {
            if (orders == null)
                return PlatformRes.error(ResCodeMsgType.ORDERS_NOT_EXISTS);
            if (StringUtils.isBlank(orders.getWechatTradeNo())) {
                //支付宝订单查询
                return null;
            } else {
                return wechatPayService.queryRefundOrder(orderNo);
            }
        } catch (Exception e) {
            return PlatformRes.error(e.getMessage());
        }

    }

    public PlatformRes<String> outFood(String cabinetNo, String outPassword) {
        if (StringUtils.isBlank(outPassword))
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);
        if (StringUtils.isBlank(cabinetNo))
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);
        Orders orders = ordersDao.getOrdersByPassAndCabinetNo(cabinetNo, outPassword);
        if (orders == null)
            return PlatformRes.error(ResCodeMsgType.ORDER_NOT_OUT);

        if (orders.getOrderStatus() == null || orders.getOrderStatus() != 1)
            return PlatformRes.error(ResCodeMsgType.PUT_ORDER_MESSAGE_EXCEPTION);
        List<OrderGoods> orderGoods = orderGoodsDao.findListByOrderNo(orders.getOrderNo());
        String drawerNo = "";
        for (OrderGoods orderGood : orderGoods) {
            //循环取餐
            drawerNo = drawerNo + "," + orderGood.getDrawerNo();
            drawerDao.outFood(orderGood.getCabinetNo(), orderGood.getDrawerNo());
        }
        //订单更新状态为已取餐
        orders.setOrderStatus(3);
        ordersDao.update(orders);

        return PlatformRes.success(drawerNo);

    }

    /**
     * 微信扫码付回调,微信公众号支付，支付宝扫码付回调修改订单逻辑
     *
     * @param orderNo
     * @return
     */
    public PlatformRes<String> cardNotify(String orderNo) {
        Orders orders = null;
        try {
            orders = ordersDao.getOrdersByOrderNo(orderNo);
            if (orders == null)
                throw new RuntimeException(ResCodeMsgType.ORDERS_NOT_EXISTS.name());
            List<OrderGoods> orderGoods = orderGoodsDao.findListByOrderNo(orderNo);
            if (orderGoods == null || orderGoods.isEmpty())
                throw new RuntimeException(ResCodeMsgType.OUT_FOOD_EXCEPTION.name());

            UserRedpacketRelaction userRedpacketRelaction = null;
            //微信公众号支付,使用红包就让红包成已使用
            if (orders.getPaymentStatus() == 1) {
                userRedpacketRelaction = userRedpacketRelactionDao.get(orders.getRedpacketId());
                if (userRedpacketRelaction != null) {
                    userRedpacketRelaction.setInUse(2);
                    userRedpacketRelactionDao.update(userRedpacketRelaction);
                }
            }


            for (OrderGoods ordergood : orderGoods) {
                //付款成功以后要锁定当前抽屉
                Drawer drawer = drawerDao.findCabinetAndDrawerNo(ordergood.getCabinetNo(), ordergood.getDrawerNo());
                //微信扫码支付
                if (orders.getPaymentStatus() == 0) {
                    //设置放餐状态未放餐
                    drawer.setFoodStatus(0 + "");
                    drawerDao.update(drawer);
                    //通知柜子开门
                } else if (orders.getPaymentStatus() == 1) {  //微信公众号支付
                    //已经支付，锁定抽屉
                    drawer.setFoodStatus(1 + "");
                    drawerDao.update(drawer);
                    //通知柜子锁定。。。
                } else if (orders.getPaymentStatus() == 2) {
                    drawer.setFoodStatus(0 + "");
                    drawerDao.update(drawer);
                }


                orderLogService.saveOrderLog(orders, orderGoods.size(), ordergood);
            }

            orders.setPaymentTime(new Date());
            //支付成功
            if (orders.getPaymentStatus() == 0) {  //微信公众号支付成功预订订单
                orders.setOrderStatus(1);
            } else if (orders.getPaymentStatus() == 1) {  //扫码付当面取餐
                orders.setOrderStatus(3);
            } else if (orders.getPaymentStatus() == 2) {
                orders.setOrderStatus(1);
            }

            ordersDao.update(orders);
            //微信扫码付款,支付宝扫码付,需要通知柜子机器
            if (orders.getPaymentStatus() == 0 || orders.getPaymentStatus() == 2) {

            }
        } catch (Exception e) {
            logger.info("下单回调失败： " + e.getMessage());
            if (orders != null) {
                //支付失败咯
                orders.setOrderStatus(2);
                ordersDao.update(orders);
            }

        }
        return PlatformRes.success(null);
    }


    /**
     * 取消订单
     *
     * @return
     */
    public PlatformRes<String> cancelOrder(String orderNo) {
        Orders orders = null;
        try {
            orders = ordersDao.getOrdersByOrderNo(orderNo);
            if (orders == null)
                throw new RuntimeException(ResCodeMsgType.ORDERS_NOT_EXISTS.name());
            List<OrderGoods> orderGoods = orderGoodsDao.findListByOrderNo(orderNo);
            if (orderGoods == null || orderGoods.isEmpty())
                throw new RuntimeException(ResCodeMsgType.OUT_FOOD_EXCEPTION.name());

            for (OrderGoods ordergood : orderGoods) {
                //付款成功以后要锁定当前抽屉
                Drawer drawer = drawerDao.findCabinetAndDrawerNo(ordergood.getCabinetNo(), ordergood.getDrawerNo());
                //支付类型:公众号支付
                if (orders.getPaymentStatus() == 1) {
                    //未放餐
                    if (StringUtils.isBlank(drawer.getProductId())) {
                        drawer.setFoodStatus(0 + "");
                    } else {//已放餐
                        drawer.setFoodStatus(1 + "");
                    }
                    drawerDao.update(drawer);
                    //通知柜子开门
                } else {
                    return PlatformRes.error(null);
                }


            }
            //支付取消
            orders.setOrderStatus(5);
            ordersDao.update(orders);
            return PlatformRes.success(null);
        } catch (Exception e) {
            return PlatformRes.error("501", e.getMessage());
        }


    }


    public List<Orders> getPayOrders(String openId) {
        return ordersDao.getPayOrders(openId);
    }


    public List<Orders> getOrderDetail(String openId) {
        return ordersDao.getOrderDetail(openId);
    }

    public Orders getOrderByOrderNo(String orderNo) {
        return ordersDao.getOrdersByOrderNo(orderNo);
    }

    public void updateRemark(String orderNo, Integer type, String remark) {
        Orders orders = this.getOrderByOrderNo(orderNo);
        orders.setRemark(remark);

        if (orders != null) {
            if (type == 1) {  //评价

            } else if (type == 2) { //问题反馈
                orders.setRefundStatus(0);
                orders.setRefundNo(orderNo);
                orders.setRefundTime(new Date());
            }

            ordersDao.update(orders);
        }

    }


    public PlatformRes<String> validPreOrder(String[] productIds, String[] nums, String cabinetId, String red) {
        if (productIds == null || nums == null || StringUtils.isBlank(cabinetId))
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);

        for (int a = 0; a < productIds.length; a++) {
            PlatformRes<String> result = validCabinetProduct(productIds[a], Integer.valueOf(nums[a]), cabinetId);
            if (!result.getCode().equals("0")) {
                return PlatformRes.error(result.getCode(), result.getData());
            }
        }

        if (StringUtils.isNotBlank(red)) {
            UserRedpacketRelaction userRedpacketRelaction = userRedpacketRelactionService.get(red);
            if (userRedpacketRelaction == null)
                return PlatformRes.error("优惠券无法使用");
        }

        return PlatformRes.success(null);

    }


    public PlatformRes<String> validCabinetProduct(String productId, Integer num, String cabinetId) {
        Product product = productService.get(productId);
        if (product == null)
            return PlatformRes.error("商品不存在");

        List<CabinetProductRelaction> list = cabinetProductRelactionDao.findListByCabinetIdAndProductId(productId, cabinetId);
        if (list == null || list.isEmpty())
            return PlatformRes.error("当前套餐已卖完");
        else {
            if (list.size() < num)
                return PlatformRes.error(product.getProductName() + ",套餐只剩" + list.size() + "份");
            else
                return PlatformRes.success(null);
        }
    }

}
