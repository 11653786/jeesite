/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.thinkgem.jeesite.junit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import com.thinkgem.jeesite.util.TenpayUtil;
import com.thinkgem.jeesite.util.XMLUtil;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

    public final static void main(String[] args) throws Exception {

        CloseableHttpClient httpclient = null;
        Map<String, String> result = null;
        try {

            httpclient = getHttpClient();


            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            String body = send();
            System.out.println("executing request" + httpPost.getRequestLine());
            StringEntity se = new StringEntity(body, "utf-8");
            se.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    throw new RuntimeException("微信退款失败:" + body);
                }

                if (entity != null)
                    result = XMLUtil.doXMLParse(EntityUtils.toString(response.getEntity(), "UTF-8"));

            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }


    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpclient = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("/cert/apiclient_cert.p12"));
            try {
                keyStore.load(instream, "1489914282".toCharArray());
            } finally {
                instream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "1489914282".toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        } catch (Exception e) {

        }

        return httpclient;

    }


    public static String send() {


        String appId = "wx3bb5180e192011f3";
        String appsercet = "jzOJq3Y1oWkCSNbpT9WoKUGzNtBpoLVv";
        String mch_id = "1489914282";
        String out_trade_no = "20170930141227";
        String nonce_str = TenpayUtil.getCurrTime();
        String charSet = "utf-8";
        String signType = "MD5";
        String appkey = "jzOJq3Y1oWkCSNbpT9WoKUGzNtBpoLVv";
        String out_refund_no = nonce_str;


        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("mch_id", mch_id);
        params.put("nonce_str", nonce_str);
        params.put("out_trade_no", out_trade_no);
        params.put("out_refund_no", out_refund_no);
        params.put("total_fee", "1");
        params.put("refund_fee", "1");
        String sign = TenpayUtil.createSign(params, charSet, signType, appkey).toUpperCase();
        params.put("sign", sign);

        return XMLUtil.getXmlByMap(params);

    }

}
