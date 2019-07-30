package com.spike.services;

import com.spike.dto.SpikeExecution;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;

/**
 * Author: tangji
 * Date: 2019 07 28 20:00
 * Description: <...>
 */
public interface RedisSpikeService {
    /**
     * 通过redis预减库存
     * @param spikeId
     * @param userPhone
     * @param md5
     * @return
     * @throws SpikeCloseException
     * @throws RepeatSpikeException
     */
    SpikeExecution executeSpikeByRedis(Long spikeId, Long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException;

}
