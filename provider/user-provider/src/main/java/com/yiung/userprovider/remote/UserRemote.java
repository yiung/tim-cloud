package com.yiung.userprovider.remote;

import com.yiung.userprovider.api.UserApi;
import com.yiung.userprovider.entity.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRemote implements UserApi {
    @Override
    public User getUser() {
        User user = new User();
        user.setId(3);
        user.setUsername("aaaa");
        return user;
    }
}
