package com.yiung.wechat.comtroller;

import com.yiung.config.center.params.ReqParams;
import com.yiung.config.center.params.ResponseVo;
import com.yiung.wechat.Feign.UserFeign;
import com.yiung.wechat.respEntity.LoginEntity;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.LoginVo;
import wechat.WechatLogin;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserFeign userFeign;

    /**
     * @param reqParams
     * */
    @RequestMapping(value = "/login")
    public ResponseVo login(@RequestBody ReqParams reqParams) {
        User user = reqParams.getEntity(User.class);
        ResponseEntity<LoginEntity> responseEntity = WechatLogin.login(LoginEntity.class, reqParams.getJSONObject());

        LoginEntity loginEntity = responseEntity.getBody();
        System.out.println("loginEntity:"+loginEntity.getErrmsg());
        String openId = loginEntity.getOpenid();
        int resultCode;
        LoginVo loginVo = new LoginVo();
        if(openId!= null && openId != ""){
            user.setWechatOpenId(loginEntity.getOpenid());
            resultCode = userFeign.processUserLogin(user);
        }else {
            resultCode = -1000;
        }
        if(resultCode == 0){
            loginVo.setUrl("http://www.baidu.com");
            loginVo.setWechatOpenId(openId);
            loginVo.setWechatUnionId(loginEntity.getUnionid());
        } else {
            loginVo.setUrl("#");
        }
        loginVo.setCode(resultCode);
        return new ResponseVo(loginVo);
    }
}
