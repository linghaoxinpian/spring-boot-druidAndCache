package com.test.mysql.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.mysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

//复杂的对象模型，需要手动利用RedisTemplate构建Redis的缓存,而对于普通的字段对象模型（如Role）直接用缓存注解
@Repository
public class UserRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, Long time, User user) {
        redisTemplate.opsForValue().set(key, new Gson().toJson(user), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<User> users) {
        redisTemplate.opsForValue().set(key, new Gson().toJson(users), time, TimeUnit.MINUTES);
    }

    public User get(String key){
        String jsonUser = redisTemplate.opsForValue().get(key);
        User user = null;
        if (!StringUtils.isEmpty(jsonUser)) {
            user = new Gson().fromJson(jsonUser,User.class);
        }
        return user;
    }

    public List<User> getList(String key) {
        String jsonUser = redisTemplate.opsForValue().get(key);
        List<User> users = null;
        if (!StringUtils.isEmpty(jsonUser)) {
            users = new Gson().fromJson(jsonUser, new TypeToken<List<User>>() {
            }.getType());
        }
        return users;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
