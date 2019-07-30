package com.spike.services.Impl;

import com.spike.dao.UserMapper;
import com.spike.entity.Mail;
import com.spike.entity.User;
import com.spike.services.EmailService;
import com.spike.services.UserService;
import com.spike.services.rabbitmq.EmailSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

/**
 * Author: tangji
 * Date: 2019 07 26 18:34
 * Description: <...>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailSender emailSender;

    @Override
    public boolean register(User user) {
        int result = userMapper.insertUser(user);
        if (result > 0) {
            Mail mail=new Mail();
            mail.setTo(user.getUserEmail());
            mail.setSubject("注册成功");
            mail.setText(user.getUserPassword());
            emailSender.send(mail);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(Integer userId, String userPassword) {
        int result = userMapper.updateUserPassword(userId, userPassword);
        if (result > 0)
            return true;
        return false;
    }

    @Override
    public boolean getUserInfo(Integer userId) {
        User user = userMapper.getUserById(userId);
        if (user != null)
            return true;
        return false;
    }
}
