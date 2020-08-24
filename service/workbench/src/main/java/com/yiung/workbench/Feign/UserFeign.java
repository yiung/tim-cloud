package com.yiung.workbench.Feign;


import com.yiung.api.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-provider")
public interface UserFeign extends UserApi {
}
