/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
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
 * 柜子商品关系配置Controller
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
        model.addAttribute("page", page);
        return "manager/cabinetproductrelaction/cabinetProductRelactionList";
    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:view")
    @RequestMapping(value = "form")
    public String form(String id, Model model) {
        //修改或者配置柜子商品信息
        //获取当前柜子信息
        Cabinet cabinet = cabinetService.get(id);
        List<CabinetProductRelaction> cabinetProductRelactionList = cabinetProductRelactionService.findList(new CabinetProductRelaction(cabinet.getId()));
        //
        model.addAttribute("cabinet", cabinet);
        model.addAttribute("cabinetProductRelactionList", cabinetProductRelactionList);


        return "manager/cabinetproductrelaction/cabinetProductRelactionForm";
    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:edit")
    @RequestMapping(value = "save")
    public String save(CabinetProductRelaction cabinetProductRelaction, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cabinetProductRelaction)) {
            return form(cabinetProductRelaction.getCabinetId(), model);
        }
        cabinetProductRelactionService.save(cabinetProductRelaction);
        addMessage(redirectAttributes, "保存柜子商品配置表成功");
        return "redirect:" + Global.getAdminPath() + "/cabinetproductrelaction/cabinetProductRelaction/?repage";
    }

    @RequiresPermissions("cabinetproductrelaction:cabinetProductRelaction:edit")
    @RequestMapping(value = "delete")
    public String delete(CabinetProductRelaction cabinetProductRelaction, RedirectAttributes redirectAttributes) {
        cabinetProductRelactionService.delete(cabinetProductRelaction);
        addMessage(redirectAttributes, "删除柜子商品配置表成功");
        return "redirect:" + Global.getAdminPath() + "/cabinet/cabinet/?repage";
    }

}