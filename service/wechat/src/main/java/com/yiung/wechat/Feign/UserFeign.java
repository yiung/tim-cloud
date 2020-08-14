package com.yiung.wechat.Feign;

import api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-provider")
public interface UserFeign extends UserApi {
}
