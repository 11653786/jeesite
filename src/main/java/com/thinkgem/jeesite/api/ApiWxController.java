package com.thinkgem.jeesite.api;

import com.thinkgem.jeesite.api.service.WechatApiService;
import com.thinkgem.jeesite.util.Decript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/10/2.
 */
@Controller
@RequestMapping(value = "/api/wx")
public class ApiWxController {

    @Autowired
    private WechatApiService wechatApiService;

    private final String token = "niushangshan";

    @RequestMapping(value = "/getMessage",method = RequestMethod.GET)
    public void getMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");


        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);

        //排序
        String sortString = sort(token, timestamp, nonce);
        //加密
        String mytoken = Decript.SHA1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            System.out.println("签名校验通过。");
            response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        } else {
            System.out.println("签名校验失败。");
        }

    }


    /**
     * 接收来自微信发来的消息
     *
     * @param out
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    @ResponseBody
    public void wechatServicePost(PrintWriter out, HttpServletRequest request) {
        String responseMessage = wechatApiService.processRequest(request);
        out.print(responseMessage);
        out.flush();
    }

    /**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }



}
