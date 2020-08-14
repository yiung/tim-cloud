package com.yiung.gateway.handler;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GlobalResponseJsonFormatHandler extends DefaultErrorWebExceptionHandler {

    public GlobalResponseJsonFormatHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            code = 404;
        }else if (error instanceof Exception){
            code = 500;
        }
        return response(code, this.buildMessage(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     * @param errorAttributes
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     * @param errorAttributes
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("code");
        return statusCode;
    }

    /**
     * 构建异常信息
     * @param request
     * @param ex
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        String evn = System.getProperty("spring.profiles.active");
        StringBuilder message = new StringBuilder();
        if (!StringUtils.isEmpty(evn) && "prod".equals(evn)){
            message.append("服务异常，请联系管理员");
        }else {
            message.append("调用请求失败[");
            message.append(request.methodName());
            message.append(" ");
            message.append(request.uri());
            message.append("]");
            if (ex != null) {
                message.append(": ");
                message.append(ex.getMessage());
            }
        }
        return message.toString();
    }

    /**
     * 构建返回的JSON数据格式
     * @param status        状态码
     * @param errorMessage  异常信息
     * @return
     */
    public static Map<String, Object> response(int status, String errorMessage) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("code", status);
        map.put("msg", errorMessage);
        map.put("sysTime", s.format(c.getTime()));
        map.put("data", new HashMap<>());
        return map;
    }
}
