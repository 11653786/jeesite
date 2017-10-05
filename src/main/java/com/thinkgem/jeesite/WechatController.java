package com.thinkgem.jeesite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/10/5.
 */
@Controller
@RequestMapping(value = "/api/wechat")
public class WechatController {

    /**
     *  美食介绍
     * @return
     */
    @RequestMapping(value = "/food")
    public String food(){
        return "wechat/food";
    }

    /**
     * 微信公众号品牌页面
     * @return
     */
    @RequestMapping(value = "/brand")
       public String brand(){
        return "wechat/pinpai";
    }

    /**
     * 附近快餐柜
     * @return
     */
    @RequestMapping(value = "/allcabinet")
    public String allcabinet(){
        return "wechat/allcabinet";
    }
}
