package com.yiung.config.configredis.core;

public class RedisKeyManage {
    /**
     * redis key
     * @param group
     * @param key
     * @param name
     * @return
     */
    public static String getKey(String group, Object key, Object name){
        return String.format("%s:%s:%s", group, key, name);
    }

    public static String getKey(String group, Object key, Object name, Object childName){
        return String.format("%s:%s:%s:%s", group, key, name, childName);
    }

    public static String getKey(String group, Object name){
        return String.format("%s:%s", group, name);
    }

    public static String getKey(String group){
        return group;
    }
}
