package com.yiung.tools.wechat;

import com.alibaba.fastjson.JSONObject;

import com.yiung.config.configcenter.wechat.WechatConfig;
import com.yiung.tools.http.HttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class WechatLogin {
    public static <T> ResponseEntity login(Class<T> tClass, JSONObject jsonObject){
         RestTemplate restTemplate = new RestTemplate();
        jsonObject.put("secret", WechatConfig.WECHAT_MINI_PROGRAM_SECRET);
        jsonObject.put("appId", WechatConfig.WECHAT_MINI_PROGRAM_APP_ID);
         String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={code}&grant_type=authorization_code";
        ResponseEntity<T> responseEntity = HttpClient.getForEntity(url,tClass,(Map<String,Object>) jsonObject);
        return responseEntity;
    }
}
