package com.spike.services.rabbitmq;

import com.spike.entity.SpikeSuccess;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: tangji
 * Date: 2019 07 30 15:21
 * Description: <...>
 */
@Component
public class OrderSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendOrder(SpikeSuccess spikeSuccess){
        rabbitTemplate.convertAndSend(spikeSuccess);
    }
}
