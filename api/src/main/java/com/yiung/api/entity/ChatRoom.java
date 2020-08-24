package com.yiung.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Integer type;

    @Column(name = "user_code")
    private Integer userCode;

    @Column(name = "room_code")
    private Integer roomCode;

    @Column
    private Integer state;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String icon;

    @Transient
    private String title;

    @Transient
    private String desc;

    @Transient
    private String navigateTo;


}
