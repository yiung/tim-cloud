package api;

import entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface UserApi {
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    User getUser();
}
