/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.web;

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
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;

/**
 * 快餐柜实体类Controller
 * @author yt
 * @version 2017-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cabinet/cabinet")
public class CabinetController extends BaseController {

	@Autowired
	private CabinetService cabinetService;
	
	@ModelAttribute
	public Cabinet get(@RequestParam(required=false) String id) {
		Cabinet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cabinetService.get(id);
		}
		if (entity == null){
			entity = new Cabinet();
		}
		return entity;
	}
	
	@RequiresPermissions("cabinet:cabinet:view")
	@RequestMapping(value = {"list", ""})
	public String list(Cabinet cabinet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cabinet> page = cabinetService.findPage(new Page<Cabinet>(request, response), cabinet); 
		model.addAttribute("page", page);
		return "manager/cabinet/cabinetList";
	}

	@RequiresPermissions("cabinet:cabinet:view")
	@RequestMapping(value = "form")
	public String form(Cabinet cabinet, Model model) {
		model.addAttribute("cabinet", cabinet);
		return "manager/cabinet/cabinetForm";
	}

	@RequiresPermissions("cabinet:cabinet:edit")
	@RequestMapping(value = "save")
	public String save(Cabinet cabinet, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cabinet)){
			return form(cabinet, model);
		}
		cabinetService.save(cabinet);
		addMessage(redirectAttributes, "保存快餐柜管理成功");
		return "redirect:"+Global.getAdminPath()+"/cabinet/cabinet/?repage";
	}
	
	@RequiresPermissions("cabinet:cabinet:edit")
	@RequestMapping(value = "delete")
	public String delete(Cabinet cabinet, RedirectAttributes redirectAttributes) {
		cabinetService.delete(cabinet);
		addMessage(redirectAttributes, "删除快餐柜管理成功");
		return "redirect:"+Global.getAdminPath()+"/cabinet/cabinet/?repage";
	}

}