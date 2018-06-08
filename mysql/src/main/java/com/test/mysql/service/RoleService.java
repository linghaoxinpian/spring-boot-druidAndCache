package com.test.mysql.service;

import com.test.mysql.entity.Role;
import com.test.mysql.redis.RoleRedis;
import com.test.mysql.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Cacheable(value = "mysql:findById:role",keyGenerator = "simpleKey")
    public Role findById(Long id){
        return roleRepository.findOne(id);
    }

    @CachePut(value = "mysql:findById:role",keyGenerator = "objectId")
    public Role create(Role role){
        return roleRepository.save(role);
    }

    @CachePut(value = "mysql:findById:role",keyGenerator = "objectId")
    public Role upate(Role role){
        return roleRepository.save(role);
    }

    @CacheEvict(value = "mysql:findById:role",keyGenerator = "simpleKey")
    public void delete(Long id){
        roleRepository.delete(id);
    }
}



























