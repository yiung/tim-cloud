package com.yiung.config.center.annotation;

import com.alibaba.fastjson.annotation.JSONType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.yiung"})
@EnableFeignClients(basePackages = {"com.yiung.*.api"})
@ResponseBody
public @interface ControllerSpringBootApplication {
}
