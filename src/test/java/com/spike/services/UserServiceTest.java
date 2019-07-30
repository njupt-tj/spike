package com.spike.services;

import com.spike.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author: tangji
 * Date: 2019 07 26 18:50
 * Description: <...>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void register() {
        User user=new User();
        user.setUserName("李四");
        user.setUserPassword("tanfasdfjksdfs");
        user.setUserPhone("1342423534");
        user.setUserAddress("南京市");
        user.setUserEmail("1947168723@qq.com");
        if(userService.register(user))
            System.out.println("注册成功");
        System.out.println("注册失败");
    }

    @Test
    public void updatePassword() {
    }

    @Test
    public void getUserInfo() {
    }
}