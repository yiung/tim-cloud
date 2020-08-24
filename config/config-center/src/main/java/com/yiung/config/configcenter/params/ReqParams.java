package com.yiung.config.configcenter.params;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 接受客户端入参
 */
public class ReqParams {
    // 客户端类型
    private static final String CLIENT_WEB = "web";
    private static final String CLIENT_ANDROID = "android";
    private static final String CLIENT_IOS = "ios";

    private Gson gson = new Gson();
    // 系统版本
    private String version;
    // 终端
    private String client;
    // 业务参数
    private Object data;

    /**
     * 构建一个指定类型数据。如果客服端传入参数为空，则返回空
     * @param tClass
     * @return
     */
    public <T> T getEntity(Class<T> tClass) {
        return (isEmpty()) ? null : this.getData(tClass);
    }

    /**
     * 获取集合对象类型
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getEntityList(Class<T> tClass){
        if (isEmpty()){
            return null;
        }else {
            String s = gson.toJson(this.data);
            return JSONObject.parseArray(s, tClass);
        }
    }

    /**
     * 获取json格式
     * @param key
     * @return
     */
    public JSONObject getJSONObject(String key){
        return getJSONObject().getJSONObject(key);
    }

    private JSONObject json = null;

    /**
     * 根据参数名获取数据字符串
     * @param name
     * @return
     */
    public String getString(String name){
        return getJSONObject().getString(name);
    }

    /**
     * 根据参数名获取整形参数
     * @param name
     * @return
     */
    public int getIntValue(String name){
        return getJSONObject().getIntValue(name);
    }

    public Integer getInt(String name){
        return getJSONObject().getInteger(name);
    }

    /**
     * 根据参数名获取float类型数据
     * @param name 参数名
     * @param decimal 保留小数位后几位。如：保留一位，decimal=1.不传值则不保留小数位
     * @return
     */
    public float getFloat(String name, int... decimal){
        if (decimal == null || decimal.length == 0){
            return getJSONObject().getFloatValue(name);
        }

        return new BigDecimal(getJSONObject().getString(name)).setScale(decimal[0], BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 根据参数名获取double类型数据
     * @param name 参数名key
     * @param decimal 保留小数位后几位。如：保留一位，decimal=1.不传值则不保留小数位
     * @return
     */
    public double getDouble(String name, int... decimal){
        if (decimal == null || decimal.length == 0){
            return getJSONObject().getDouble(name);
        }

        return new BigDecimal((getJSONObject().getString(name) == null) ? "0" : getJSONObject().getString(name)).setScale(decimal[0], BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 根据参数名获取long类型参数
     * @param name
     * @return
     */
    public long getLong(String name){
        return getJSONObject().getLongValue(name);
    }

    public boolean getBooleanValue(String name){
        return getJSONObject().getBooleanValue(name);
    }

    public Boolean getBoolean(String name){
        return getJSONObject().getBoolean(name);
    }

    private <T> T getData(Class<T> tClass){
        return JSON.parseObject(gson.toJson(data), tClass);
    }

    public JSONObject getJSONObject(){
        setJSONObject();
        return json;
    }

    public JSONArray getJSONArray(String key){
        return this.getJSONObject().getJSONArray(key);
    }

    public JSONArray getJSONArray(){
        if (isEmpty()){
            return new JSONArray();
        }
        return this.getData(JSONArray.class);
    }

    private void setJSONObject(){
        if (json == null){
            json = isEmpty() ? new JSONObject() : getData(JSONObject.class);
        }
    }

    private boolean isEmpty(){
        return (StringUtils.isEmpty(this.data)) ? true : false;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setData(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ReqParams{" +
                "gson=" + gson +
                ", version='" + version + '\'' +
                ", client='" + client + '\'' +
                ", data=" + data +
                ", json=" + json +
                '}';
    }
}
