//package com.yiung.gateway.handler;
//
//import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.List;
//
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    /**
//     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
//     *
//     * @param binder 绑定器
//     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//    }
//
//    /**
//     * GlobalException.
//     *
//     * @param e Exception
//     * @return Response
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RateLimiter.Response handleGlobalException(Exception e) {
//        log.error("全局异常 ex={}", e.getMessage(), e);
//        return Response.failed(ErrorCode.GLOBAL_EXCEPTION.getCode(), e.getMessage());
//    }
//
//    /**
//     * MethodArgumentNotValidException
//     *
//     * @param e MethodArgumentNotValidException
//     * @return Response
//     */
//    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public RateLimiter.Response handleBodyValidException(MethodArgumentNotValidException e) {
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        log.error("参数校验异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
//        return RateLimiter.Response.failed(ErrorCode.VALIDATION_EXCEPTION.getCode(),
//                fieldErrors.get(0).getDefaultMessage());
//    }
//
//    /**
//     * BadCredentialsException.
//     *
//     * @param e IllegalArgumentException
//     * @return Response
//     */
//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public RateLimiter.Response handleArgumentException(BadCredentialsException e) {
//        log.error("凭证错误异常 ex={}", e.getMessage(), e);
//        return RateLimiter.Response.failed(ErrorCode.BAD_CREDENTIALS.getCode(), e.getMessage());
//    }
//
//
//}
