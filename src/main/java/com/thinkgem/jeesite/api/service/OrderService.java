package com.thinkgem.jeesite.api.service;

import com.thinkgem.jeesite.api.entity.handler.CabinerDrawerHandler;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao.CabinetProductRelactionDao;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.orders.service.OrdersService;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import com.thinkgem.jeesite.util.TenpayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    /**
     * @param products
     * @param paymentType 支付类型: 0,微信扫码支付 1,微信公众号支付 2,支付宝
     * @param tradeType
     * @return
     */
    public PlatformRes<String> preorder(List<PreOrderReq> products, Integer paymentType, String tradeType) {


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
            PlatformRes<String> validResult = validOrderInfo(productReq, productIds, productTotalPrice);

            if (!validResult.getCode().equals("0"))
                return validResult;
        }


        //这里要创建订单,订单状态为0,等到回调通过以后更改状态,微信预约下单生成二维码
        if (paymentType == 0) {
            PlatformRes<String> wechatPayResult = wechatPayService.unifiedorder(orderNo, productIds, productTotalPrice, tradeType);
            //预支付id成功,生成订单
            if (!wechatPayResult.getCode().equals("0")) {
                return wechatPayResult;
            }

        } else
            //支付宝预约下单生成二维码
            return null;

        //------全部验证通过保存订单和订单明细
            return null;


    }


    private PlatformRes<String> validOrderInfo(PreOrderReq productReq, String productIds, Integer productTotalPrice) {
        if (StringUtils.isBlank(productReq.getCabinetNo())) {
            return PlatformRes.error("传入参数柜子编号有错误!");
        }

        if (StringUtils.isBlank(productReq.getDrawerNo())) {
            return PlatformRes.error("传入参数抽屉编号有为空的");
        }

        if (productReq.getProductNum() < 1) {
            return PlatformRes.error("传入参数商品数量最小为1");
        }

        if (StringUtils.isBlank(productReq.getProductId())) {
            return PlatformRes.error("传入参数商品ID为空!");
        }

        Product product = productService.get(productReq.getProductId());

        if (product == null)
            return PlatformRes.error(ResCodeMsgType.PRODUCT_NOT_EXISTS);
        //商品价格
        productReq.setGetProductActualPrice(product.getProductActualPrice());

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

        //柜子是否放餐
        if (!cabinerDrawerHandler.getDrawerStatus().equals("0"))
            return PlatformRes.error(ResCodeMsgType.DRAWER_HAVING_FOOD);


        //设置区域信息给后面订单用,节省查询数据库次数
        productReq.setAreaId(cabinerDrawerHandler.getAreaId());
        productReq.setAreaName(cabinerDrawerHandler.getAreaName());
        //判断当前柜子是否可以放当前的商品
        CabinetProductRelaction cabinetProductRelaction = cabinetProductRelactionDao.get(new CabinetProductRelaction(productReq.getProductId(), productReq.getDrawerNo()));
        if (cabinetProductRelaction == null)
            return PlatformRes.error(ResCodeMsgType.DRAWER_NOT_PUT_PRODUCT);


        return PlatformRes.success(null);
    }

}
