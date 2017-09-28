package com.thinkgem.jeesite.modules.manager.cabinet.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by yangtao on 2017/9/28.
 */
@Controller
@RequestMapping(value = "${adminPath}/cabinettotl/cabinettotl")
public class CabinetTotalController {


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


    @RequiresPermissions("cabinettotal:cabinettotal:view")
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

        return "manager/cabinettotal/cabinetTotalList";
    }


}
