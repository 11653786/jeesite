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
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;
import com.thinkgem.jeesite.service.OrderLogService;
import com.thinkgem.jeesite.util.TenpayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    Logger logger = Logger.getLogger(this.getClass().getName());

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
        PlatformRes<String> wechatPayResult = null;
        if (paymentStatus == 0) {
            wechatPayResult = wechatPayService.unifiedorder(orderNo, productIds, productTotalPrice, tradeType);
            //预支付id成功,生成订单
            if (!wechatPayResult.getCode().equals("0")) {
                return wechatPayResult;
            }

        } else if (paymentStatus == 1) { //公众号支付

        } else {       //支付宝扫码付

        }

        //------全部验证通过保存订单和订单明细--------------------------------------
        ordersService.submitForOrder(orderNo, paymentStatus, products, productTotalPrice, repackgeId);
        return wechatPayResult;


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
        CabinetProductRelaction cabinetProductRelaction = cabinetProductRelactionDao.findByDrawerNoAndProductId(productReq.getDrawerNo(), productReq.getProductId());
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
        if (StringUtils.isBlank(orders.getWechatTradeNo())) {
            //支付宝订单查询
            return null;
        } else {
            if (orders.getRefundStatus() != null && (orders.getRefundStatus().equals("0") || orders.getRefundStatus().equals("2"))) {
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
        for (OrderGoods orderGood : orderGoods) {
            //循环取餐
            drawerDao.outFood(orderGood.getCabinetNo(), orderGood.getDrawerNo());
        }
        //订单更新状态为已取餐
        orders.setOrderStatus(3);
        ordersDao.update(orders);

        return PlatformRes.success("取餐成功");

    }

    /**
     * 微信扫码付回调,支付宝扫码付回调修改订单逻辑
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

            for (OrderGoods ordergood : orderGoods) {
                //付款成功以后要锁定当前抽屉
                Drawer drawer = drawerDao.findCabinetAndDrawerNo(ordergood.getCabinetNo(), ordergood.getDrawerNo());
                //锁定抽屉
                drawer.setFoodStatus(3 + "");
                drawerDao.update(drawer);

                orderLogService.saveOrderLog(orders, orderGoods.size(), ordergood);
            }

            orders.setPaymentTime(new Date());
            //支付成功
            orders.setOrderStatus(1);
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


    public PlatformRes<String> validPreOrder(String[] productIds, String[] nums, String cabinetId,String red) {
        for (int a = 0; a < productIds.length; a++) {
            PlatformRes<String> result = validCabinetProduct(productIds[a], Integer.valueOf(nums[a]), cabinetId);
            if (!result.getCode().equals("0")) {
                return PlatformRes.error(result.getCode(), result.getData());
            }
        }

        if(StringUtils.isNotBlank(red)){
            UserRedpacketRelaction userRedpacketRelaction= userRedpacketRelactionService.get(red);
            if(userRedpacketRelaction==null)
                return PlatformRes.error("优惠券无法使用");
        }

        return PlatformRes.success(null);

    }


    //
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
