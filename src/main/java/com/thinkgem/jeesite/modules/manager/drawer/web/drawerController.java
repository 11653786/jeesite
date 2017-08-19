package com.thinkgem.jeesite.modules.manager.drawer.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.drawer.service.DrawerService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 抽屉控制器
 * Created by erfeng on 17/8/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/drawer/drawer")
public class DrawerController extends BaseController {

    @Autowired
    private DrawerService drawerService;

    @ModelAttribute
    public Drawer get(@RequestParam(required = false) String id) {
        Drawer entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = drawerService.get(id);
        }
        if (entity == null) {
            entity = new Drawer();
        }
        return entity;
    }

    @RequiresPermissions("drawer:drawer:view")
    @RequestMapping(value = {"list", ""})
    public String list(Drawer drawer, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Drawer> page = drawerService.findPage(new Page<Drawer>(request, response), drawer);
        model.addAttribute("page", page);
        return "manager/drawer/drawerList";
    }



    @RequiresPermissions("drawer:drawer:view")
    @RequestMapping(value = "form")
    public String form(Drawer drawer, Model model) {
        model.addAttribute("drawer", drawer);
        return "manager/drawer/drawerForm";
    }

    @RequiresPermissions("drawer:drawer:edit")
    @RequestMapping(value = "save")
    public String save(Drawer drawer, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, drawer)) {
            return form(drawer, model);
        }
        drawerService.save(drawer);
        addMessage(redirectAttributes, "保存快餐柜管理成功");
        return "redirect:" + Global.getAdminPath() + "/drawer/drawer/?repage";
    }

    @RequiresPermissions("drawer:drawer:edit")
    @RequestMapping(value = "delete")
    public String delete(Drawer drawer, RedirectAttributes redirectAttributes) {
        drawerService.delete(drawer);
        addMessage(redirectAttributes, "删除快餐柜管理成功");
        return "redirect:" + Global.getAdminPath() + "/drawer/drawer/?repage";
    }


}
