package com.yiung.workbench.Feign;

import com.yiung.userprovider.api.UserApi;
import com.yiung.userprovider.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-provider")
public interface UserFeign extends UserApi {
}
