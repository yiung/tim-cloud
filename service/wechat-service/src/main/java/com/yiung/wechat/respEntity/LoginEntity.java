package com.yiung.wechat.respEntity;

import lombok.Data;

@Data
public class LoginEntity {
    private Integer errcode;
    private String errmsg;
    private String session_key;
    private String unionid;
    private String openid;
    private String msg;





//    public void setMsg(String msg) {
//        switch (getErrcode()) {
//            case 40013: this.msg = "appId错误";
//            break;
//            case 40029: this.msg = "code无效";
//            break;
//            case -1: this.msg = "系统繁忙，此时请开发者稍候再试";
//            break;
//            case 45011: this.msg = "频率限制，每个用户每分钟100次";
//            break;
//            default:this.msg = "请求成功";
//        }
//
//    }
}
