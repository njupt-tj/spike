package com.spike.services;

import com.spike.dto.Exposer;
import com.spike.dto.SpikeExecution;
import com.spike.entity.Spike;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;

import java.util.List;

/**
 * Author: tangji
 * Date: 2019 07 16 18:58
 * Description: <...>
 */
public interface SpikeService {
    /**
     * 查询所有商品的秒杀信息
     * @param spikeId
     * @return
     */
    List<Spike> getAllSpikes();

    /**
     * 获取单个商品的秒杀信息
     * @param spikeId
     * @return
     */
    Spike getSpike(Long spikeId);

    /**
     * 秒杀开启时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param spikeId
     */
    Exposer exportSpikeUrl(Long spikeId);

    /**
     * 执行秒杀操作
     * @param spike
     * @param userPhone
     * @param md5
     */
    SpikeExecution executeSpike(Long spikeId, long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException;

}
