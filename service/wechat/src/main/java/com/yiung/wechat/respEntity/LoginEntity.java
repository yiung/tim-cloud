package com.yiung.wechat.respEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginEntity {
    private Integer errcode;
    private String errmsg;
    private String session_key;
    private String unionid;
    private String openid;
    private String msg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        switch (getErrcode()) {
            case 40013: this.msg = "appId错误";
            break;
            case 40029: this.msg = "code无效";
            break;
            case -1: this.msg = "系统繁忙，此时请开发者稍候再试";
            break;
            case 45011: this.msg = "频率限制，每个用户每分钟100次";
            break;
            default:this.msg = "请求成功";
        }

    }
}
