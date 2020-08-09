package com.yiung.workbench.controller;

import com.yiung.userprovider.entity.User;
import com.yiung.workbench.Feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private UserFeign userFeign;

    @GetMapping("/getUser")
    public User getUser(){
        return userFeign.getUser();
    }


}
