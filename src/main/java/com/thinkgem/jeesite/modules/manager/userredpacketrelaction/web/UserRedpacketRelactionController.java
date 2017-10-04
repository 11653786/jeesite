/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.userredpacketrelaction.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.manager.redpacket.entity.Redpacket;
import com.thinkgem.jeesite.modules.manager.redpacket.service.RedpacketService;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import com.thinkgem.jeesite.modules.manager.users.service.UsersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;

/**
 * 用户红包关系实体类Controller
 *
 * @author yt
 * @version 2017-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/userredpacketrelaction/userRedpacketRelaction")
public class UserRedpacketRelactionController extends BaseController {

    @Autowired
    private UserRedpacketRelactionService userRedpacketRelactionService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedpacketService redpacketService;

    @ModelAttribute
    public UserRedpacketRelaction get(@RequestParam(required = false) String id) {
        UserRedpacketRelaction entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userRedpacketRelactionService.get(id);
        }
        if (entity == null) {
            entity = new UserRedpacketRelaction();
        }
        return entity;
    }

    @RequiresPermissions("userredpacketrelaction:userRedpacketRelaction:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserRedpacketRelaction userRedpacketRelaction, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserRedpacketRelaction> page = userRedpacketRelactionService.findPage(new Page<UserRedpacketRelaction>(request, response), userRedpacketRelaction);
        model.addAttribute("page", page);
        return "manager/userredpacketrelaction/userRedpacketRelactionList";
    }


    /**
     * 用户分配红包
     * @param id
     * @param redpacketId
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("userredpacketrelaction:userRedpacketRelaction:edit")
    @RequestMapping(value = "userAddRedpacket")
    public String save(String id, String redpacketId, Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(redpacketId)) {
            addMessage(redirectAttributes, "传入参数不能为空!");
            model.addAttribute("id", id);
            return "manager/users/userAddRedpacket";
        }

        Users users = usersService.get(id);
        Redpacket redpacket = redpacketService.get(redpacketId);

        if (users == null || redpacket == null) {
            addMessage(redirectAttributes, "数据有误!");
            model.addAttribute("id", id);
            return "manager/users/userAddRedpacket";
        }

        userRedpacketRelactionService.save(users, redpacket);

        addMessage(redirectAttributes, "分配红包成功!");
        return "redirect:" + Global.getAdminPath() + "/users/users/?repage";
    }

    @RequiresPermissions("userredpacketrelaction:userRedpacketRelaction:edit")
    @RequestMapping(value = "delete")
    public String delete(UserRedpacketRelaction userRedpacketRelaction, RedirectAttributes redirectAttributes) {
        userRedpacketRelactionService.delete(userRedpacketRelaction);
        addMessage(redirectAttributes, "删除用户红包关系管理成功");
        return "redirect:" + Global.getAdminPath() + "/userredpacketrelaction/userRedpacketRelaction/?repage";
    }

}