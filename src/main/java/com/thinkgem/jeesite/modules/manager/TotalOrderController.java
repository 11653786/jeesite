package com.thinkgem.jeesite.modules.manager;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yangtao on 2017/9/25.
 */
@Controller
@RequestMapping(value = "${adminPath}/totalorder/totalorder")
public class TotalOrderController {

    @RequiresPermissions("totalorder:totalorder:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "manager/totalorder/totalordersList";
    }


    @RequiresPermissions("totalorder:totalorder:export")
    @RequestMapping(value = "/export")
    public void export() {
    }

}
