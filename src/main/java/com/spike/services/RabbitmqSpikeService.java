package com.spike.services;

import com.spike.dto.SpikeExecution;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;

/**
 * Author: tangji
 * Date: 2019 07 28 20:05
 * Description: <...>
 */
public interface RabbitmqSpikeService {

    /**
     * 通过rabbitMQ异步下单
     * @param spikeId
     * @param userPhone
     * @param md5
     * @return
     * @throws SpikeCloseException
     * @throws RepeatSpikeException
     */
    SpikeExecution excuteSpikeByRabbitMQ(Long spikeId, Long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException;

}
