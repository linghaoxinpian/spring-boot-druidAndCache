package com.test.mysql.service;

import com.test.mysql.entity.User;
import com.test.mysql.redis.UserRedis;
import com.test.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRedis userRedis;

    @Autowired
    UserRepository userRepository;

    private static final String keyHead="mysql:get:user";

    public User findById(Long id){
        User user=userRedis.get(keyHead+id);
        if(user==null){
            user=userRepository.findOne(id);
            if(user!=null){
                userRedis.add(keyHead+id,30L,user);
            }
        }
        return user;
    }
}
