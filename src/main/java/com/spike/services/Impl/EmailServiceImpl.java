package com.spike.services.Impl;


import com.spike.services.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Author: tangji
 * Date: 2019 07 26 21:26
 * Description: <...>
 */
@Service
public class EmailServiceImpl implements EmailService {

    Logger logger = Logger.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        try {
            mailSender.send(mailMessage);
            logger.info("邮件已经发送");
        } catch (Exception e) {
            logger.info("邮件发送失败", e);
        }
    }
}
