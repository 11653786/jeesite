/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.modules.manager.drawer.service.DrawerService;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.service.CabinetProductRelactionService;

import java.util.List;

/**
 * 抽屉商品关系配置Controller
 *
 * @author yt
 * @version 2017-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cabinetproductrelaction/cabinetProductRelaction")
public class CabinetProductRelactionController extends BaseController {

    @Autowired
    private CabinetProductRelactionService cabinetProductRelactionService;
    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DrawerService drawerService;

    @ModelAttribute
    public CabinetProductRelaction get(@RequestParam(required = false) String id) {
        CabinetProductRelaction entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cabinetProductRelactionService.get(id);
        }
        if (entity == null) {
            entity = new CabinetProductRelaction();
        }
        return entity;
    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:view")
    @RequestMapping(value = {"list", ""})
    public String list(CabinetProductRelaction cabinetProductRelaction, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CabinetProductRelaction> page = cabinetProductRelactionService.findPage(new Page<CabinetProductRelaction>(request, response), cabinetProductRelaction);
        List<Cabinet> cabinetList = cabinetService.findList(new Cabinet());
        model.addAttribute("page", page);
        model.addAttribute("cabinetList", cabinetList);
        return "manager/cabinetproductrelaction/cabinetProductRelactionList";
    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:view")
    @RequestMapping(value = "form")
    public String form(String id, Model model) {
        //修改或者配置抽屉商品信息
        //获取当前抽屉信息
        Drawer drawer = drawerService.get(id);
        Cabinet cabinet = cabinetService.get(drawer.getCabinetId());
        List<CabinetProductRelaction> cabinetProductRelactionList = cabinetProductRelactionService.findListByDrawerNo(drawer.getDrawerNo());
        //
        model.addAttribute("cabinet", cabinet);
        model.addAttribute("drawer", drawer);
        model.addAttribute("cabinetProductRelactionList", cabinetProductRelactionList);


        return "manager/cabinetproductrelaction/cabinetProductRelactionForm";
    }


//    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:view")
//    @RequestMapping(value = "form1")
//    public String form1(String id, Model model) {
//        //修改或者配置抽屉商品信息
//        //获取当前抽屉信息
//        CabinetProductRelaction cabinetProductRelaction = cabinetProductRelactionService.get(id);
//        Cabinet cabinet = cabinetService.get(cabinetProductRelaction.getCabinetId());
//        List<CabinetProductRelaction> cabinetProductRelactionList = cabinetProductRelactionService.findList(new CabinetProductRelaction(cabinet.getId()));
//        //
//        model.addAttribute("cabinet", cabinet);
//        model.addAttribute("cabinetProductRelactionList", cabinetProductRelactionList);
//
//
//        return "manager/cabinetproductrelaction/cabinetProductRelactionForm";
//    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:edit")
    @RequestMapping(value = "save")
    public String save(String drawerId, String cabinetId, String productId, RedirectAttributes redirectAttributes) {

        if (StringUtils.isBlank(productId) || StringUtils.isBlank(drawerId) || StringUtils.isBlank(cabinetId)) {
            addMessage(redirectAttributes, "传入参数异常");
            return "manager/cabinetproductrelaction/cabinetProductRelactionForm";
        }

        Product product = productService.get(productId);
        Drawer drawer = drawerService.get(drawerId);

        if (product == null || drawer == null) {
            addMessage(redirectAttributes, "传入参数异常");
            return "manager/cabinetproductrelaction/cabinetProductRelactionForm";
        }

        CabinetProductRelaction cabinetProductRelaction = cabinetProductRelactionService.findByCabinetIdAndDrawerId(cabinetId, drawerId);
        if (cabinetProductRelaction == null) {
            cabinetProductRelactionService.save(product, drawer);
        } else {
            cabinetProductRelactionService.updateProduct(cabinetProductRelaction.getId(), productId,product.getProductName());
        }

        addMessage(redirectAttributes, "保存抽屉商品配置成功");
        return "redirect:" + Global.getAdminPath() + "/drawer/drawer/?repage";
    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:edit")
    @RequestMapping(value = "delete")
    public String delete(CabinetProductRelaction cabinetProductRelaction, RedirectAttributes redirectAttributes) {
        cabinetProductRelactionService.delete(cabinetProductRelaction);
        addMessage(redirectAttributes, "删除抽屉商品配置表成功");
        return "redirect:" + Global.getAdminPath() + "/cabinetproductrelaction/cabinetProductRelaction/?repage";
    }

}