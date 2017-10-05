/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.wechatmenu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.api.service.WechatApiService;
import com.thinkgem.jeesite.mapper.AccessTokenMapper;
import com.thinkgem.jeesite.vo.AccessToken;
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
import com.thinkgem.jeesite.modules.manager.wechatmenu.entity.WechatMenu;
import com.thinkgem.jeesite.modules.manager.wechatmenu.service.WechatMenuService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 微信菜单Controller
 *
 * @author yt
 * @version 2017-10-04
 */
@Controller
@RequestMapping(value = "${adminPath}/wechatmenu/wechatMenu")
public class WechatMenuController extends BaseController {

    @Autowired
    private WechatMenuService wechatMenuService;
    @Autowired
    private WechatApiService wechatApiService;

    @ModelAttribute
    public WechatMenu get(@RequestParam(required = false) String id) {
        WechatMenu entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wechatMenuService.get(id);
        }
        if (entity == null) {
            entity = new WechatMenu();
        }
        return entity;
    }

    @RequiresPermissions("wechatmenu:wechatMenu:view")
    @RequestMapping(value = {"list", ""})
    public String list(WechatMenu wechatMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WechatMenu> page = wechatMenuService.findPage(new Page<WechatMenu>(request, response), wechatMenu);
        model.addAttribute("page", page);
        return "manager/wechatmenu/wechatMenuList";
    }

    @RequiresPermissions("wechatmenu:wechatMenu:view")
    @RequestMapping(value = "form")
    public String form(WechatMenu wechatMenu, Model model) {
        model.addAttribute("wechatMenu", wechatMenu);
        return "manager/wechatmenu/wechatMenuForm";
    }

    @RequiresPermissions("wechatmenu:wechatMenu:edit")
    @RequestMapping(value = "save")
    public String save(WechatMenu wechatMenu, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, wechatMenu)) {
            return form(wechatMenu, model);
        }
        wechatMenu.setMenu(wechatMenu.getMenu().replace("&quot;", "\""));
        wechatMenu.setMenu(wechatMenu.getMenu().replace("&amp;", "&"));
        AccessToken accessToken = wechatApiService.getWechatToken();
        String tips = createMenu(accessToken.getAccess_token(), wechatMenu.getMenu());
        wechatMenuService.save(wechatMenu);

        addMessage(redirectAttributes, "保存微信菜单成功:" + tips);
        return "redirect:" + Global.getAdminPath() + "/wechatmenu/wechatMenu/?repage";
    }

    @RequiresPermissions("wechatmenu:wechatMenu:edit")
    @RequestMapping(value = "delete")
    public String delete(WechatMenu wechatMenu, RedirectAttributes redirectAttributes) {
        wechatMenuService.delete(wechatMenu);
        addMessage(redirectAttributes, "删除微信菜单成功");
        return "redirect:" + Global.getAdminPath() + "/wechatmenu/wechatMenu/?repage";
    }

    public static String createMenu(String access_token, String menu) {

        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(menu.getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            return "返回信息" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "createMenu 失败";
    }

}