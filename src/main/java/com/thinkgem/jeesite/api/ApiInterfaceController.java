package com.thinkgem.jeesite.api;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.service.CabinetProductRelactionService;
import com.thinkgem.jeesite.modules.manager.drawer.service.DrawerService;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by yangtao on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/interface")
public class ApiInterfaceController {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private OrderService orderService;
    @Autowired
    private DrawerService drawerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CabinetProductRelactionService cabinetProductRelactionService;


    /**
     * localhost:8080/api/order/preorder?productId=1&productPrice=300&paymentType=0
     * 微信预下单生成二维码,生成订单返回二维码
     * <p/>
     * {
     * "code": "0",
     * "message": "成功",
     * "data": "weixin://wxpay/bizpayurl?pr=hMnBust"
     * }
     *
     * @param productsStr 商品信息
     * @param paymentType 支付类型: 0,微信扫码支付 1,微信公众号支付 2,支付宝
     * @param repackgeId  红包id,公众号支付有用
     * @return
     */
    @RequestMapping(value = "/preorder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<String> preorder(String productsStr, Integer paymentType, String repackgeId) {
        String tradeType = null;
        List<PreOrderReq> products = JSONObject.parseArray(productsStr, PreOrderReq.class);

        if (paymentType == 0) {
            tradeType = "NATIVE";
        }
        return orderService.preorder(products, paymentType, tradeType, repackgeId);
    }


    /**
     * 取餐
     *
     * @param cabinetNo   柜子编号
     * @param putPassword 取餐密码,用户订单取餐密码
     * @return
     */
    @RequestMapping(value = "/outFood")
    @ResponseBody
    public PlatformRes<String> outFood(String cabinetNo, String putPassword) {
        return orderService.outFood(cabinetNo, putPassword);
    }

    /**
     * 工作人员放餐
     *
     * @param productId
     * @param foodPassword 工作人员放餐密码
     * @param cabinetNo
     * @param drawerNo
     * @return
     */
    @RequestMapping(value = "/putFood")
    @ResponseBody
    public PlatformRes<String> putFood(String productId, String foodPassword, String cabinetNo, String drawerNo) {
        return drawerService.putFood(productId, foodPassword, cabinetNo, drawerNo);
    }


    /**
     * 获取商品列表
     *
     * @return
     */
    @RequestMapping(value = "/getProductList")
    @ResponseBody
    public List<Product> getProductList() {
        Product product = new Product();
        product.setProductStatus(1 + "");
        return productService.findList(product);
    }


    /**
     * 根据柜子编号获取当前柜子抽屉和商品的关系
     *
     * @param cabinetNo
     * @return
     */
    @RequestMapping(value = "/getDrawerProductRelactionByCabinetNo")
    @ResponseBody
    public List<CabinetProductRelaction> getDrawerProductRelactionByCabinetNo(String cabinetNo) {
        CabinetProductRelaction cabinetProductRelaction = new CabinetProductRelaction();
        cabinetProductRelaction.setCabinetNo(cabinetNo);
        return cabinetProductRelactionService.findList(cabinetProductRelaction);
    }

    /**
     * 根据柜子编号获取当前柜子抽屉和商品的关系
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(String data) {
        logger.info("data: " + data);
        return data;
    }


}
