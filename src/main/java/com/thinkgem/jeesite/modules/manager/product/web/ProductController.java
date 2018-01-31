/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品实体类Controller
 *
 * @author yt
 * @version 2017-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/product/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @ModelAttribute
    public Product get(@RequestParam(required = false) String id) {
        Product entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = productService.get(id);
        }
        if (entity == null) {
            entity = new Product();
        }
        return entity;
    }

    @RequiresPermissions("product:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Product> page = productService.findPage(new Page<Product>(request, response), product);
        model.addAttribute("page", page);
        return "manager/product/productList";
    }

    @RequiresPermissions("product:product:view")
    @RequestMapping(value = "form")
    public String form(Product product, Model model) {
        if(StringUtils.isNotBlank(product.getImgurl())){
            product.setImgurl("/"+product.getImgurl());
        }

        model.addAttribute("product", product);
        return "manager/product/productForm";
    }

    @RequiresPermissions("product:product:edit")
    @RequestMapping(value = "save")
    public String save(Product product, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, product)) {
            return form(product, model);
        }


        if (StringUtils.isNotBlank(product.getImgurl())) {
            product.setImgurl(product.getImgurl().replace("|", ""));
            if (product.getImgurl().substring(0, 1).equals("/")) {
                product.setImgurl(product.getImgurl().substring(1));
            }
        }


        productService.save(product);
        addMessage(redirectAttributes, "保存商品管理成功");
        return "redirect:" + Global.getAdminPath() + "/product/product/?repage";
    }

    @RequiresPermissions("product:product:edit")
    @RequestMapping(value = "delete")
    public String delete(Product product, RedirectAttributes redirectAttributes) {
        productService.delete(product);
        addMessage(redirectAttributes, "删除商品管理成功");
        return "redirect:" + Global.getAdminPath() + "/product/product/?repage";
    }

    @RequestMapping(value = "/getProductList")
    @ResponseBody
    public List<Map<String, String>> getProductList() {
        Product product = new Product();
        product.setProductStatus("1");
        List<Product> list = productService.findList(product);
        List<Map<String, String>> productList = new ArrayList<Map<String, String>>();
        if (list != null && !list.isEmpty()) {
            for (Product entity : list) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", entity.getId());
                map.put("name", entity.getProductName());
                productList.add(map);
            }
        }

        return productList;
    }

}