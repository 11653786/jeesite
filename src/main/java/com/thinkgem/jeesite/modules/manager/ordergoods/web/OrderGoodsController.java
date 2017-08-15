/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.ordergoods.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.ordergoods.service.OrderGoodsService;

/**
 * 子订单实体类Controller
 * @author yt
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/ordergoods/orderGoods")
public class OrderGoodsController extends BaseController {

	@Autowired
	private OrderGoodsService orderGoodsService;
	
	@ModelAttribute
	public OrderGoods get(@RequestParam(required=false) String id) {
		OrderGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderGoodsService.get(id);
		}
		if (entity == null){
			entity = new OrderGoods();
		}
		return entity;
	}
	
	@RequiresPermissions("ordergoods:orderGoods:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderGoods orderGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderGoods> page = orderGoodsService.findPage(new Page<OrderGoods>(request, response), orderGoods); 
		model.addAttribute("page", page);
		return "manager/ordergoods/orderGoodsList";
	}

	@RequiresPermissions("ordergoods:orderGoods:view")
	@RequestMapping(value = "form")
	public String form(OrderGoods orderGoods, Model model) {
		model.addAttribute("orderGoods", orderGoods);
		return "manager/ordergoods/orderGoodsForm";
	}

	@RequiresPermissions("ordergoods:orderGoods:edit")
	@RequestMapping(value = "save")
	public String save(OrderGoods orderGoods, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderGoods)){
			return form(orderGoods, model);
		}
		orderGoodsService.save(orderGoods);
		addMessage(redirectAttributes, "保存子订单成功");
		return "redirect:"+Global.getAdminPath()+"/ordergoods/orderGoods/?repage";
	}
	
	@RequiresPermissions("ordergoods:orderGoods:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderGoods orderGoods, RedirectAttributes redirectAttributes) {
		orderGoodsService.delete(orderGoods);
		addMessage(redirectAttributes, "删除子订单成功");
		return "redirect:"+Global.getAdminPath()+"/ordergoods/orderGoods/?repage";
	}

}