package com.yiung.gateway.filter;


import com.yiung.config.configredis.core.RedisKeyManage;
import com.yiung.config.configredis.core.RedisOpts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisOpts redisOpts;

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            ServerHttpResponse resp = exchange.getResponse();

            HttpHeaders headers = req.getHeaders();
            List<String> token = headers.get("token");
//            List<String> bussCode = headers.get("buss_code");
            // 如果地址为其中之一则不经过认证中心，降低服务器压力
            if (isCheck(req.getURI().getPath())){
                // 登录认证
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("token", (token == null || token.size() == 0) ? "" : token.get(0));
                params.add("url", req.getURI().getPath());
//                params.add("bussCode", (bussCode == null || bussCode.size() == 0) ? "" : bussCode.get(0));
                // 发起认证请求
//                Map<String, String> m = restTemplate.postForObject("http://JD-AUTH/oauth/authUrlVerify", params, Map.class);
//                String verifyResult = (m == null) ? "down" : m.get("verifyResult");
//                verifyResult = (verifyResult == null) ? "down" : verifyResult;
                // 认证失败
//                if (!"success".equalsIgnoreCase(verifyResult)){
//                    resp.getHeaders().set("Content-Type", "application/json;charset=UTF-8");
//                    byte[] bs;
//                    if ("forbidden".equalsIgnoreCase(verifyResult)){
//                        bs = outBytes(30000, "没有访问权限");
//                    }else if ("down".equalsIgnoreCase(verifyResult)){
//                        bs = outBytes(10002, "鉴权失败");
//                    }else{
//                        bs = outBytes(10001, "登录过期");
//                    }
//                    DataBuffer buffer = resp.bufferFactory().wrap(bs);
//                    return resp.writeWith(Flux.just(buffer));
//                }

//                redisOpts.set(RedisKeyManage.getKey(tokenPreKey,token),token,redisExpireDate);

                //临时
                Object o = redisOpts.get(RedisKeyManage.getKey("token", token));
//                Object o = redisOpts.get(RedisKeyManage.getKey("account_definition_config", accountId));
            }
            // 认证通过
            return chain.filter(exchange);
        };
    }

    /**
     * 是否发起检查
     * @param url
     * @return
     */
    private boolean isCheck(String url){
        String[] u = new String[]{"/getOnlineInfo", "/savePosition"};
        for (int i = 0; i < u.length; i++) {
            if (url.contains(u[i])){
                return false;
            }
        }
        return true;
    }

    private byte[] outBytes(int code, String msg){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = String.format("{\"success\":false, \"code\":%s, \"msg\": \"%s\", \"sysTime\": \"%s\", \"data\":{}}", code, msg, s.format(c.getTime()));
        return format.getBytes(StandardCharsets.UTF_8);
    }

    public static class Config {
    }
}
