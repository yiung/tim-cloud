package http;

import converter.WXMappingJackson2HttpMessageConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpClient {
    public static <T> ResponseEntity getForEntity(String url, Class<T> clz, Map<String,Object> params){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WXMappingJackson2HttpMessageConverter());
        ResponseEntity responseEntity = restTemplate.getForEntity(url,clz,params);
        return responseEntity;
    }

//    public static <T> ResponseEntity getObject(String url){
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity responseEntity = restTemplate.getForObject(url);
//        return responseEntity;
//    }

}
