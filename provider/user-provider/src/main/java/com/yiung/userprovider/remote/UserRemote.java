package com.yiung.userprovider.remote;

import api.UserApi;
import com.yiung.config.center.params.ResponseVo;
import com.yiung.userprovider.Mapper.UserMapper;
import com.yiung.userprovider.service.UserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRemote implements UserApi {
    @Autowired
    private UserService userService;

//    @Override
//    public List<User> getUserList(){
//        List<User> userList = userMapper.getUserList();
//        return userList;
//    }

//    @Override
//    public Integer addUser(@RequestBody User user) {
//        Integer result = userMapper.insert(user);
//        return result;
//    }


    @Override
    public Integer processUserLogin(@RequestBody User user) {
        return userService.processUserLogin(user);
    }
}
