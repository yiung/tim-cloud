//package com.yiung.userprovider.controller;
//
//import com.yiung.config.center.params.ReqParams;
//import com.yiung.config.center.params.ResponseVo;
//import com.yiung.userprovider.Mapper.UserMapper;
//import entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    @Autowired
//    private UserMapper userMapper;
//
//    @RequestMapping("/getUser")
//    public ResponseVo getUser(){
//        List<User> userList = userMapper.getUserList();
//        return new ResponseVo(userList);
//    }
//
//    @RequestMapping("/addUser")
//    public ResponseVo addUser(@RequestBody ReqParams reqParams){
//        int result = userMapper.insert(reqParams.getEntity(User.class));
//        return new ResponseVo(result);
//    }
//}
