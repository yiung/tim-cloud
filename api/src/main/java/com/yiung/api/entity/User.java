package com.yiung.api.entity;


import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_code")
    private String userCode;//UC008600010001  UC + 国家代码 + 序号
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "wechat_open_id")
    private String wechatOpenId;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "country")
    private String country;
    @Column(name = "language")
    private String language;
    @Column(name = "user_property")
    private Integer userProperty;


    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

//    private String city;
//    private String province;

    @Transient
    private ChatRoom chatRoom;
    @Transient
    private String code;
    @Transient
    private String rawData;
    @Transient
    private String signature;
    @Transient
    private String encryptedData;
    @Transient
    private String iv;
    @Transient
    private String SessionKey;
    @Transient
    private Object userInfo;


}
