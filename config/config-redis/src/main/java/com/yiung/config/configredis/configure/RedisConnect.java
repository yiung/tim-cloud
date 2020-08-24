package com.yiung.config.configredis.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConnect extends CachingConfigurerSupport {

    /**
     * Logger
     */
    private static final Logger lg = LoggerFactory.getLogger(RedisConnect.class);

    @Bean
    public KeyGenerator exactKeyGenerator() {
        //  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
        // 使用:进行分割，可以很多显示出层级关系
        // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append("-");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append("-" + obj);
            }
            String rsToUse = String.valueOf(sb);
//            lg.info("自动生成Redis Key -> [{}]", rsToUse);
            return rsToUse;
        };
    }

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(10)) //设置缓存的默认超时时间：10秒
                .disableCachingNullValues()             //如果是空值，不缓存
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))         //设置key序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer())));  //设置value序列化器

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer()); // key序列化
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer()); // value序列化
        redisTemplate.setHashKeySerializer(keySerializer()); // Hash key序列化
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer()); // Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        lg.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                lg.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                lg.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                lg.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                lg.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }

    /**
     * 此内部类就是把yml的配置数据，进行读取，创建JedisConnectionFactory和JedisPool，以供外部类初始化缓存管理器使用
     */
    @Configuration
    @PropertySource("classpath:redis.properties")
    class DataJedisProperties {
        @Value("${spring.redis.host}")
        private String host;
        @Value("${spring.redis.host.test}")
        private String host_test;
        @Value("${spring.redis.host.uat}")
        private String host_uat;
        @Value("${spring.redis.host.prod}")
        private String host_prod;

        @Value("${spring.redis.password}")
        private String password;
        @Value("${spring.redis.password.test}")
        private String password_test;
        @Value("${spring.redis.password.uat}")
        private String password_uat;
        @Value("${spring.redis.password.prod}")
        private String password_prod;

        @Value("${spring.redis.port}")
        private int port;
        @Value("${spring.redis.timeout}")
        private int timeout;
        @Value("${spring.redis.jedis.pool.max-idle}")
        private int maxIdle;
        @Value("${spring.redis.jedis.pool.max-wait}")
        private long maxWaitMillis;

        @Bean
        JedisConnectionFactory jedisConnectionFactory() {
            lg.info("Create JedisConnectionFactory successful");
            JedisConnectionFactory factory = new JedisConnectionFactory();

            String evn = System.getProperty("spring.profiles.active");
            if (!StringUtils.isEmpty(evn)) {
                if ("prod".equals(evn)){
                    factory.setDatabase(0);
                    factory.setHostName(host_prod);
                    factory.setPassword(password_prod);
                }else if ("test".equals(evn)){
                    factory.setDatabase(1);
                    factory.setHostName(host_prod);
                    factory.setPassword(password_prod);
                }else if ("uat".equals(evn)){
                    factory.setDatabase(2);
                    factory.setHostName(host_uat);
                    factory.setPassword(password_uat);
                }
            } else {
                factory.setDatabase(1);
                factory.setHostName(host);
                factory.setPassword(password);
            }
            factory.setPort(port);
            factory.setTimeout(timeout);
            return factory;
        }

        @Bean
        public JedisPool redisPoolFactory() {
            lg.info("JedisPool init successful，host -> [{}]；port -> [{}]", host, port);
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

            String redisHost, redisPassword;
            String property = System.getProperty("spring.profiles.active");
            if (!StringUtils.isEmpty(property)){
                if ("prod".equals(property)){
                    redisHost = host_prod;
                    redisPassword = password_prod;
                }else if ("uat".equals(property)){
                    redisHost = host_uat;
                    redisPassword = password_uat;
                }else if ("test".equals(property)){
                    redisHost = host_test;
                    redisPassword = password_test;
                }else {
                    redisHost = host;
                    redisPassword = password;
                }
            }else {

                redisHost = host;
                redisPassword = password;
            }

            JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisHost, port, timeout, redisPassword);
            return jedisPool;
        }
    }
}
