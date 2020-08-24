package com.yiung.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.yiung.api.entity.User;
import com.yiung.api.vo.LoginVo;
import com.yiung.config.configcenter.params.ReqParams;
import com.yiung.config.configcenter.params.ResponseVo;
import com.yiung.config.configredis.core.RedisKeyManage;
import com.yiung.config.configredis.core.RedisOpts;
import com.yiung.tools.wechat.DecryptionUtil;
import com.yiung.tools.wechat.WechatLogin;
import com.yiung.wechat.Feign.UserFeign;
import com.yiung.wechat.respEntity.LoginEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import com.yiung.config.configcenter.common.ExporeTime;
import com.yiung.config.configcenter.common.constant;
import com.yiung.tools.token.TokenUtil;

/**
 * WeChat authentication interface 2020/08/14 15:49
 * @author yiung
 * @version V1.0
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserFeign userFeign;

    /**
     * Register or login method 2020/08/14 15:49
     * @author yiung
     * @param reqParams login information
     * @return ResponseVo
     **/
    @RequestMapping(value = "/getOpenId")
    public ResponseVo getOpenId(@RequestBody ReqParams reqParams) {
        User user = reqParams.getEntity(User.class);
        @SuppressWarnings("unchecked")
        ResponseEntity<LoginEntity> responseEntity = WechatLogin.login(LoginEntity.class, reqParams.getJSONObject());

        LoginEntity loginEntity = responseEntity.getBody();
        String openId = loginEntity == null ? "" : loginEntity.getOpenid();
        int resultCode;
        LoginVo loginVo = new LoginVo();
        if(openId!= null && !"".equals(openId)){
            user.setWechatOpenId(loginEntity.getOpenid());
//            String encryptedData = user.getEncryptedData();
//            String iv = user.getIv();
//            String sessionKey = loginEntity.getSession_key();
//            String json = DecryptionUtil.getUserInfo(encryptedData, sessionKey, iv);
//            String wxDecrypt = DecryptionUtil.wxDecrypt(encryptedData, sessionKey, iv);

//            resultCode = userFeign.processUserLogin(user);
            resultCode = 0;
        }else {
            resultCode = -1000;
        }
        if(resultCode == 0){
//            loginVo.setUrl("../index/index");
            loginVo.setWechatOpenId(openId);
//            loginVo.setWechatUnionId(loginEntity.getUnionid());
            loginVo.setSessionKey(loginEntity.getSession_key());
//            loginVo.setToken("abc");
            loginVo.setToken("virtualToken");
        } else {
//            loginVo.setUrl("#");
        }
        loginVo.setCode(resultCode);
        return new ResponseVo(loginVo);
    }


    /**
     * Register or login method 2020/08/14 15:49
     * @author yiung
     * @param reqParams login information
     * @return ResponseVo
     **/
    @RequestMapping(value = "/login")
    public ResponseVo login(@RequestBody ReqParams reqParams) {
        User user = reqParams.getEntity(User.class);
        System.out.println("user:"+user);
        boolean result = false;
        LoginVo loginVo = new LoginVo();
            String encryptedData = user.getEncryptedData();
            String iv = user.getIv();
            String sessionKey = user.getSessionKey();
            String json = DecryptionUtil.getUserInfo(encryptedData, sessionKey, iv);
            String wxDecrypt = DecryptionUtil.wxDecrypt(encryptedData, sessionKey, iv);
            JSONObject phoneInfo = JSONObject.parseObject(json);
            if(phoneInfo!=null && phoneInfo.getString("phoneNumber")!=null) {
                String phoneNumber = phoneInfo.getString("phoneNumber");
                user.setUsername(phoneNumber);
                user.setPhoneNumber(phoneNumber);
                result = userFeign.login(user);
            }
        if(result){
//            loginVo.setUrl("../login/loginByCode");
            loginVo.setUrl("/pages/index/index");
            loginVo.setCode(0);
//            loginVo.setRoomCode(1);

            long redisExpireDate = 3600 * 24 * 7L;
            RedisOpts redisOpts = new RedisOpts();
            redisOpts.set(RedisKeyManage.getKey(constant.TOKEN_PRE_KEY,user.getUsername()),TokenUtil.getNewToken(),ExporeTime.TOKEN_WECHAT_MINI_EXPIRE_TIME);
        } else {
            loginVo.setCode(-1);
            loginVo.setUrl("#");
//            loginVo.setRoomCode(1);
        }
        String token = UUID.randomUUID().toString() + System.currentTimeMillis();
        loginVo.setToken(token);
        return new ResponseVo(loginVo);
    }


    /**
     * Register or login method 2020/08/14 15:49
     * @author yiung
     * @param reqParams login information
     * @return ResponseVo
     **/
    @RequestMapping(value = "/checkLoginStatus")
    public ResponseVo checkLoginStatus(@RequestBody ReqParams reqParams) {
//        JSONObject jsonObject = reqParams.getJSONObject("token");
        //暂且返回都是成功，不校验token
        LoginVo loginVo = new LoginVo();
        loginVo.setCode(0);
//        loginVo.setToken(jsonObject.getString("token"));
        return new ResponseVo(loginVo);
    }

//    /**
//     * Register or login method 2020/08/14 15:49
//     * @author yiung
//     * @param reqParams login information
//     * @return ResponseVo
//     **/
//    @RequestMapping(value = "/login")
//    public ResponseVo login(@RequestBody ReqParams reqParams) {
//        User user = reqParams.getEntity(User.class);
//        @SuppressWarnings("unchecked")
//        ResponseEntity<LoginEntity> responseEntity = WechatLogin.login(LoginEntity.class, reqParams.getJSONObject());
//
//        LoginEntity loginEntity = responseEntity.getBody();
//        String openId = loginEntity == null ? "" : loginEntity.getOpenid();
//        int resultCode;
//        LoginVo loginVo = new LoginVo();
//        if(openId!= null && !"".equals(openId)){
//            user.setWechatOpenId(loginEntity.getOpenid());
//            String encryptedData = user.getEncryptedData();
//            String iv = user.getIv();
//            String sessionKey = loginEntity.getSession_key();
////            String json = DecryptionUtil.getUserInfo(encryptedData, sessionKey, iv);
////            String wxDecrypt = DecryptionUtil.wxDecrypt(encryptedData, sessionKey, iv);
//
////            resultCode = userFeign.processUserLogin(user);
//            resultCode = 0;
//        }else {
//            resultCode = -1000;
//        }
//        if(resultCode == 0){
//            loginVo.setUrl("../index/index");
//            loginVo.setWechatOpenId(openId);
//            loginVo.setWechatUnionId(loginEntity.getUnionid());
//            loginVo.setSessionKey(loginEntity.getSession_key());
//            loginVo.setToken("abc");
//        } else {
//            loginVo.setUrl("#");
//        }
//        loginVo.setCode(resultCode);
//        return new ResponseVo(loginVo);
//    }
}
