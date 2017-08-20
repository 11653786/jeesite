/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.redpacket.web;

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
import com.thinkgem.jeesite.modules.manager.redpacket.entity.Redpacket;
import com.thinkgem.jeesite.modules.manager.redpacket.service.RedpacketService;

/**
 * 优惠卷实体类Controller
 * @author yt
 * @version 2017-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/redpacket/redpacket")
public class RedpacketController extends BaseController {

	@Autowired
	private RedpacketService redpacketService;
	
	@ModelAttribute
	public Redpacket get(@RequestParam(required=false) String id) {
		Redpacket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = redpacketService.get(id);
		}
		if (entity == null){
			entity = new Redpacket();
		}
		return entity;
	}
	
	@RequiresPermissions("redpacket:redpacket:view")
	@RequestMapping(value = {"list", ""})
	public String list(Redpacket redpacket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Redpacket> page = redpacketService.findPage(new Page<Redpacket>(request, response), redpacket); 
		model.addAttribute("page", page);
		return "manager/redpacket/redpacketList";
	}

	@RequiresPermissions("redpacket:redpacket:view")
	@RequestMapping(value = "form")
	public String form(Redpacket redpacket, Model model) {
		model.addAttribute("redpacket", redpacket);
		return "manager/redpacket/redpacketForm";
	}

	@RequiresPermissions("redpacket:redpacket:edit")
	@RequestMapping(value = "save")
	public String save(Redpacket redpacket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, redpacket)){
			return form(redpacket, model);
		}
		redpacketService.save(redpacket);
		addMessage(redirectAttributes, "保存优惠卷管理成功");
		return "redirect:"+Global.getAdminPath()+"/redpacket/redpacket/?repage";
	}
	
	@RequiresPermissions("redpacket:redpacket:edit")
	@RequestMapping(value = "delete")
	public String delete(Redpacket redpacket, RedirectAttributes redirectAttributes) {
		redpacketService.delete(redpacket);
		addMessage(redirectAttributes, "删除优惠卷管理成功");
		return "redirect:"+Global.getAdminPath()+"/redpacket/redpacket/?repage";
	}

}