package com.spike.services.rabbitmq;

import com.spike.entity.Mail;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: tangji
 * Date: 2019 07 27 15:11
 * Description: <...>
 */
@Component
public class EmailSender {
    /**
     * 把发邮件消息交给消息队列
     */
      @Autowired
      private RabbitTemplate rabbitTemplate;

      public void send(Mail mail){
          rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
          rabbitTemplate.convertAndSend("MyExchange", "mail.test", mail);

      }
}
