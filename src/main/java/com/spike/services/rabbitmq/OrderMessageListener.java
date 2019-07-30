package com.spike.services.rabbitmq;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike.dao.SpikeMapper;
import com.spike.dao.SpikeSuccessMapper;
import com.spike.entity.SpikeSuccess;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Author: tangji
 * Date: 2019 07 30 15:25
 * Description: <...>
 */
@Component
public class OrderMessageListener implements MessageListener {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private SpikeMapper spikeMapper;
    @Autowired
    private SpikeSuccessMapper spikeSuccessMapper;

    @Override
    public void onMessage(Message message) {

        String body = new String(message.getBody());
        ObjectMapper mapper = new ObjectMapper();
        try{
            SpikeSuccess spikeSuccess = mapper.readValue(body, SpikeSuccess.class);
            long spikeId = spikeSuccess.getSpikeId();
            logger.info("接受到订单" + spikeId);
            //更新库存
            spikeMapper.reduceNumber(spikeId, new Date());
            spikeSuccessMapper.addSuccessSpikeByIdAndPhone(spikeId, spikeSuccess.getUserPhone());
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }
}
