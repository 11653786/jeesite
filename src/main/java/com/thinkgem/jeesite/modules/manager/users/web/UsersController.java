/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.users.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.DictType;
import com.thinkgem.jeesite.util.MD5Util;
import oracle.net.aso.MD5;
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
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import com.thinkgem.jeesite.modules.manager.users.service.UsersService;

/**
 * 客户实体类Controller
 * @author yt
 * @version 2017-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/users/users")
public class UsersController extends BaseController {

	@Autowired
	private UsersService usersService;
	
	@ModelAttribute
	public Users get(@RequestParam(required=false) String id) {
		Users entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = usersService.get(id);
		}
		if (entity == null){
			entity = new Users();
		}
		return entity;
	}
	
	@RequiresPermissions("users:users:view")
	@RequestMapping(value = {"list", ""})
	public String list(Users users, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Users> page = usersService.findPage(new Page<Users>(request, response), users); 
		model.addAttribute("page", page);
		return "manager/users/usersList";
	}

	@RequiresPermissions("users:users:view")
	@RequestMapping(value = "form")
	public String form(Users users, Model model) {
		model.addAttribute("users", users);
		return "manager/users/usersForm";
	}

	@RequiresPermissions("users:users:edit")
	@RequestMapping(value = "save")
	public String save(Users users, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, users)){
			return form(users, model);
		}
		users.setPassword(MD5Util.MD5Encode(users.getPassword(),"utf-8"));
		usersService.save(users);
		addMessage(redirectAttributes, "保存客户管理成功");
		return "redirect:"+Global.getAdminPath()+"/users/users/?repage";
	}
	
	@RequiresPermissions("users:users:edit")
	@RequestMapping(value = "delete")
	public String delete(Users users, RedirectAttributes redirectAttributes) {
		usersService.delete(users);
		addMessage(redirectAttributes, "删除客户管理成功");
		return "redirect:"+Global.getAdminPath()+"/users/users/?repage";
	}

	/**
	 * 限制登录
	 * @param id
	 * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("users:users:edit")
	@RequestMapping(value = "limitLogin")
	public String limitLogin(String id, RedirectAttributes redirectAttributes) {
		Users users=usersService.get(id);
		users.setUserStatus(DictType.user_status_limit_login.getValue());
		usersService.save(users);
		addMessage(redirectAttributes, "删除客户管理成功");
		return "redirect:"+Global.getAdminPath()+"/users/users/?repage";
	}

}