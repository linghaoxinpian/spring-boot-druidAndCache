package com.test.mysql.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//启用Spring Cache以使用以下注解方式调用缓存,
// @Cacheable  存取缓存
// @CachePut    修改缓存
// @CacheEvict  删除缓存
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    //配置使用Redis来做缓存,有多种缓存可选，这里选择Redis的实现
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(43200); //缓存保存12小时
        return redisCacheManager;
    }

    //-----------------------缓存Key生成规则-----------------------begin
    @Bean
    public KeyGenerator simpleKey() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName())
                        .append(":");
                for (Object param : params) {
                    sb.append(param.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public KeyGenerator objectId(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb=new StringBuilder();
                sb.append(target.getClass().getName()).append(":");
                try {
                    sb.append(params[0].getClass().getMethod("getId",null).invoke(params[0],null).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }
        };
    }
    //-----------------------缓存Key生成规则-----------------------end
}
