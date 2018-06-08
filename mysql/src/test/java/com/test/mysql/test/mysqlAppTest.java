package com.test.mysql.test;

import com.test.mysql.entity.Role;
import com.test.mysql.entity.User;
import com.test.mysql.repository.UserRepository;
import com.test.mysql.service.RoleService;
import com.test.mysql.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application.yml"})
@SpringBootTest
public class mysqlAppTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Test
    public void test(){
        List<User> users = userRepository.findAll();
        System.out.println("====检查使用的连接池===="+dataSource.getClass());
        System.out.println("====检查Redis配置===="+jedisConnectionFactory);
        System.out.println("====检查Redis配置===="+redisTemplate);
    }

    //简单的Role对象模型
    @Test
    public void test2(){
        Role role=new Role();
        role.setName("manager");
        role.setCreateDate(new Date());
        roleService.create(role);
        Assert.notNull(role.getId());
        Role role2 = roleService.findById(role.getId());   //会从redis中查
        System.out.println("========roleId:"+role2.getId());
    }

    //复杂的User对象模型
    @Test
    public void test3(){
        User user=new User();
        user.setName("jackson");
        userRepository.save(user);
        Assert.notNull(user.getId());
        User user1 = userService.findById(user.getId());
        System.out.println("========userId:"+user1.getId());
    }
}
