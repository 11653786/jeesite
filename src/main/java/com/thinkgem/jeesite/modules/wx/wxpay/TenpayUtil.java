package com.thinkgem.jeesite.modules.wx.wxpay;

/**
 * Created by erfeng on 17/8/17.
 */

import com.thinkgem.jeesite.modules.wx.wxpay.base.util.tenpay.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class TenpayUtil {
    private static final Logger logger = LoggerFactory.getLogger(TenpayUtil.class);

    public static String toString(Object obj) {
        if (obj == null)
            return "";

        return obj.toString();
    }

    public static int toInt(Object obj) {
        int a = 0;
        try {
            if (obj != null)
                a = Integer.parseInt(obj.toString());
        } catch (Exception e) {
        }
        return a;
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String strDate = formatter.format(date);
        return strDate;
    }

    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    public static long getUnixTime(Date date) {
        if (null == date) {
            return 0;
        }

        return date.getTime() / 1000;
    }


    public static String date2String(Date date, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(date);
    }

    /**
     * 生成request URL
     *
     * @return String
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getRequestURL(Map<String, String> params, String charSet, String signType, String key) {
        String reqPars = "";
        try {
            String sign = createSign(params, charSet, signType, key);

            StringBuffer sb = new StringBuffer();
            for (String paraKey : params.keySet()) {
                String k = paraKey;
                String v = params.get(k);
                if (v == null || v.trim().length() == 0) {
                    continue;
                }
                sb.append(k + "=" + URLEncoder.encode(v, charSet) + "&");
            }
            sb.append("sign=" + sign.toUpperCase()); //因为要求大写，所以就大写吧
            reqPars = sb.toString();
        } catch (UnsupportedEncodingException ex) {
        }
        return reqPars;
    }

    /**
     * 生成签名
     *
     * @param paras
     * @param charSet
     * @param signType
     * @param key
     * @return
     */
    public static String createSign(Map<String, String> paras, String charSet, String signType, String key) {
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(paras.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String k = keys.get(i);
            String v = paras.get(k);
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        String sign = "";
        if (signType.equals("MD5")) {
            sign = MD5Util.MD5Encode(sb.toString(), charSet).toLowerCase();
        }

        return sign;
    }

    /**
     * 生成随机数串
     *
     * @return
     */
    public static String genNonceStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }

}

