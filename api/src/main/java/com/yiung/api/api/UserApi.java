package com.yiung.api.api;

import com.yiung.api.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface UserApi {
      @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
      User getUserByOpenId(@RequestBody User user);

    @RequestMapping(value = "/login")
    boolean login(@RequestBody User user);
}
