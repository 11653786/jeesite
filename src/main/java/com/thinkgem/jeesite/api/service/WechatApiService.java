package com.thinkgem.jeesite.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.config.WechatConfig;
import com.thinkgem.jeesite.mapper.AccessTokenMapper;
import com.thinkgem.jeesite.util.HttpUtil;
import com.thinkgem.jeesite.vo.AccessToken;
import com.thinkgem.jeesite.vo.AccessTokenExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 微信公众号需要的service
 * Created by Administrator on 2017/10/4.
 */
@Service
public class WechatApiService {

    @Autowired
    private AccessTokenMapper accessTokenMapper;
    @Autowired
    private WechatConfig wechatConfig;

    public AccessToken getWechatToken() {
        Date now = new Date();
        AccessTokenExample example = new AccessTokenExample();
        AccessTokenExample.Criteria query = example.createCriteria();
        query.andInTimeLessThan(now).andInOutTimeGreaterThan(now);
        List<AccessToken> list = accessTokenMapper.selectByExample(example);

        if (list.isEmpty()) {
            String result = HttpUtil.sendPostRequest(wechatConfig.token_url, "grant_type=client_credential&appid=" + wechatConfig.app_id + "&secret=" + wechatConfig.app_sercet, true);
            AccessToken token = JSONObject.parseObject(result, AccessToken.class);
            token.setInTime(now);
            token.setInOutTime(getTwoHoursDate(now));
            token.setTokenType(0);
            accessTokenMapper.insert(token);
            return token;
        } else
            return list.get(0);

    }

    private Date getTwoHoursDate(Date now) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.HOUR_OF_DAY, 2);
        return ca.getTime();
    }
}
