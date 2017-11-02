package com.thinkgem.jeesite;

/**
 * Created by Administrator on 2017/11/2.
 */
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class StringUtilsTest {

    public static void testDataToMap() {
        String data = "certificatetype=0&certificateno=220182&depositacct=622848";
        Map map = new HashMap();

        if (null != data) {
            String[] param = data.split("&");
            for (int i = 0; i < param.length; i++) {
                int index = param[i].indexOf('=');
                map.put(param[i].substring(0,index), param[i].substring((index + 1)));
            }
        }

        System.out.println(map);

        System.out.println("----------------分割线---------------");
        Map result = new HashMap();
        String[] params = data.split("\\&");
        for (String entry : params) {
            if (entry.contains("=")) {
                String[] sub = entry.split("\\=");
                if (sub.length > 1) {
                    result.put(sub[0], sub[1]);
                } else {
                    result.put(sub[0], "");
                }
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args){
        testDataToMap();
    }

}

