/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.entity.res.UpdateCabinetPassRes;
import com.thinkgem.jeesite.api.enums.SocketResMsgType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.mina.SessionMap;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 快餐柜实体类Controller
 *
 * @author yt
 * @version 2017-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cabinet/cabinet")
public class CabinetController extends BaseController {

    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private CabinetDao cabinetDao;


    @ModelAttribute
    public Cabinet get(@RequestParam(required = false) String id) {
        Cabinet entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cabinetService.get(id);
        }
        if (entity == null) {
            entity = new Cabinet();
        }
        return entity;
    }

    @RequiresPermissions("cabinet:cabinet:view")
    @RequestMapping(value = {"list", ""})
    public String list(Cabinet cabinet, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Cabinet> page = cabinetService.findPage(new Page<Cabinet>(request, response), cabinet);
        List<Cabinet> groupByCabinet = cabinetDao.groupByProductNameTotal();
        model.addAttribute("page", page);
        model.addAttribute("groupByCabinet", groupByCabinet);

        //查询不同状态的柜子信息
        List<Cabinet> food0 = cabinetDao.groupbyCabinetNosByFoodStatus(0);
        List<Cabinet> food1 = cabinetDao.groupbyCabinetNosByFoodStatus(1);
        List<Cabinet> food2 = cabinetDao.groupbyCabinetNosByFoodStatus(2);

        model.addAttribute("page", page);
        model.addAttribute("food0", food0);
        model.addAttribute("food1", food1);
        model.addAttribute("food2", food2);

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
        if (!beanValidator(model, cabinet)) {
            return form(cabinet, model);
        }
        cabinetService.save(cabinet);
        addMessage(redirectAttributes, "保存快餐柜管理成功");
        return "redirect:" + Global.getAdminPath() + "/cabinet/cabinet/?repage";
    }

    @RequiresPermissions("cabinet:cabinet:edit")
    @RequestMapping(value = "delete")
    public String delete(Cabinet cabinet, RedirectAttributes redirectAttributes) {
        cabinetService.delete(cabinet);
        addMessage(redirectAttributes, "删除快餐成功");
        return "redirect:" + Global.getAdminPath() + "/cabinet/cabinet/?repage";
    }

    @RequiresPermissions("cabinet:cabinet:edit")
    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePassword(Cabinet cabinet, Model model) {

        model.addAttribute("cabinet", cabinet);
        return "manager/cabinet/cabinetUpdatePass";
    }


    @RequiresPermissions("cabinet:cabinet:edit")
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePasswordForm(Cabinet cabinet, String sysPassword, String foodPassword, RedirectAttributes redirectAttributes) {
        PlatformRes<String> result = cabinetService.updatePassword(cabinet, sysPassword, foodPassword);
        addMessage(redirectAttributes, result.getMessage());
        if (result.getCode().equals("0")) {
            UpdateCabinetPassRes updateCabinetPassRes = new UpdateCabinetPassRes(result.getCode(), result.getMessage(), SocketResMsgType.UPDATE_CABINET_PASS.code(), foodPassword, sysPassword);
            String message = JSONObject.toJSONString(updateCabinetPassRes);
            SessionMap.sendMessage(cabinet.getCabinetNos(), message);
        }
//        SessionMap sessionMap=SessionMap.sendMessage(cabinet.getCabinetNos(),);
        return "redirect:" + Global.getAdminPath() + "/cabinet/cabinet/?repage";
    }

    /**
     * 设置快餐柜工作时间
     *
     * @param cabinet
     * @param model
     * @return
     */
    @RequiresPermissions("cabinet:cabinet:edit")
    @RequestMapping(value = "setWorkTime", method = RequestMethod.GET)
    public String setWorkTime(Cabinet cabinet, Model model) {

        model.addAttribute("cabinet", cabinet);
        return "manager/cabinet/cabinetsettime";
    }

    @RequiresPermissions("cabinet:cabinet:edit")
    @RequestMapping(value = "setWorkTime", method = RequestMethod.POST)
    public String setWorkTimeForm(Cabinet cabinet, RedirectAttributes redirectAttributes) {
        Integer isSuccess = cabinetService.setWorkTime(cabinet.getId(), cabinet.getWorkStartTime(), cabinet.getWorkEndTime());
        if (isSuccess == 0) {
            addMessage(redirectAttributes, "操作失败");
        } else {
            addMessage(redirectAttributes, "操作成功");
        }
//        SessionMap sessionMap=SessionMap.sendMessage(cabinet.getCabinetNos(),);
        return "redirect:" + Global.getAdminPath() + "/cabinet/cabinet/?repage";
    }


}