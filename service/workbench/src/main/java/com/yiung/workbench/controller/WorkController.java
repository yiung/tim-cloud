package com.yiung.workbench.controller;

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

//    @GetMapping("/getUserList")
//    public List<User> getUserList(){
//        return userFeign.getUserList();
//    }


}
