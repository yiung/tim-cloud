package com.yiung.config.center.params;

import com.alibaba.fastjson.annotation.JSONField;


public class ResponseVo{
    /**
     * Status description：0-success others-fail
     * */
    @JSONField(serialize = false)
    private Boolean success;

    /**
     * Status code：0-success others-fail
     * */
    private Integer code;

    /**
     * Status info
     * */
    private String msg;

    /**
     * System time
     * */
    private Long sysTime;

    /**
     * Response data
     * */
    private Object data;

    public ResponseVo(Object data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setSysTime(Long sysTime) {
        this.sysTime = System.currentTimeMillis();
    }

    public Long getSysTime() {
        return sysTime;
    }

    public boolean isSuccess() {
        return 0==code ? true:false;
    }

    @Override
    public String toString(){
        return "RespParams{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", sysTime='" + sysTime + '\'' +
                ", data=" + data +
                '}';
    }
}
