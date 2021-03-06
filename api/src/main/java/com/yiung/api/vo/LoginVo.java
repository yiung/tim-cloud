package com.yiung.api.vo;


import com.yiung.api.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class LoginVo {

    private Integer code;
    private String url;
    private String wechatOpenId;
    private String wechatUnionId;
    private String sessionKey;
    private String token;
    private Integer roomCode;
    private User user;
}
