package com.yiung.userprovider.remote;

import com.yiung.api.api.UserApi;
import com.yiung.api.entity.User;
import com.yiung.userprovider.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRemote implements UserApi {
    @Autowired
    private UserService userService;

    @Override
    public User getUserByOpenId(@RequestBody User user) {
        return userService.getUserByOpenId(user);
    }

    @Override
    public boolean login(@RequestBody User user) {
        return userService.login(user);
    }
}
