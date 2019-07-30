package com.spike.services;

import com.spike.entity.Mail;
import com.spike.services.rabbitmq.EmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author: tangji
 * Date: 2019 07 26 21:31
 * Description: <...>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class EmailServiceTest {

    @Autowired
    private EmailSender sender;
    @Test
    public void sendEmail() {
        Mail mail=new Mail();
        mail.setFrom("1947168723@qq.com");
        mail.setTo("tangji2019@163.com");
        mail.setSubject("hello");
        mail.setText("你好啊");
        sender.send(mail);
    }
}