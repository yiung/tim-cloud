package com.yiung.userprovider.controller;

import com.yiung.userprovider.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getUser")
    public User getUser(){
        User user = new User();
        user.setId(3);
        user.setUsername("aaaa");
        return user;
    }
}
