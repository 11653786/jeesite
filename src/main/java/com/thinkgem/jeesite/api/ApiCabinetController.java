package com.thinkgem.jeesite.api;

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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/10/2.
 */
@Controller
@RequestMapping(value = "/api/cabinet")
public class ApiCabinetController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DrawerService drawerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CabinetProductRelactionService cabinetProductRelactionService;


    /**
     * 取餐
     *
     * @param cabinetNo   柜子编号
     * @param putPassword 取餐密码
     * @return
     */
    @RequestMapping(value = "/outFood")
    @ResponseBody
    public PlatformRes<String> outFood(String cabinetNo, String putPassword) {
        return orderService.outFood(cabinetNo, putPassword);
    }

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


}



