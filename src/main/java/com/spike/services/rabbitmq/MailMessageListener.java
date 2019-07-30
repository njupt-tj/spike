package com.spike.services.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.entity.Mail;
import com.spike.services.EmailService;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author: tangji
 * Date: 2019 07 27 15:12
 * Description: <...>
 */
@Component
public class MailMessageListener implements MessageListener {
    private Logger logger = Logger.getLogger(MailMessageListener.class);
    @Autowired
    private EmailService emailService;

    @Override
    public void onMessage(Message message) {
        String body = new String(message.getBody());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Mail mail = mapper.readValue(body, Mail.class);
            logger.info("接收到邮件信息" + mail);
            emailService.sendEmail(mail.getTo(), mail.getSubject(), mail.getText());

        } catch (IOException e) {
            logger.error("邮件接收失败", e);
        }
    }
}
