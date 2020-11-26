package com.yiung.wechat.controller;

import com.yiung.api.entity.User;
import com.yiung.config.configcenter.params.ReqParams;
import com.yiung.config.configcenter.params.ResponseVo;
import com.yiung.config.configredis.core.RedisOpts;
import com.yiung.wechat.Feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RedisOpts redisOpts;

    /**
     * getUserInfo
     * @author yiung
     * @param reqParams wechatOpenId
     * @return ResponseVo
     **/
    @RequestMapping("/getUserByOpenId")
    public ResponseVo getUserByOpenId(@RequestBody ReqParams reqParams) {
        User userReq = new User();
        userReq.setWechatOpenId(reqParams.getString("wechatOpenId"));
        User user = userFeign.getUserByOpenId(userReq);
        return new ResponseVo(user);

    }

}
