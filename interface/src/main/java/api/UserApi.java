package api;

import entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface UserApi {
//    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
//    List<User> getUserList();
//
//    @RequestMapping(value = "/addUser")
//    Integer addUser(@RequestBody User user);

    @RequestMapping(value = "/processUserLogin")
    Integer processUserLogin(@RequestBody User user);
}
