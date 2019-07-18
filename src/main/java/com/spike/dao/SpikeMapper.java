package com.spike.dao;

import com.spike.entity.Spike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: tangji
 * Date: 2019 07 15 16:50
 * Description: <...>
 */
@Mapper
public interface SpikeMapper {
    /**
     * 减库存
     *
     * @param spikeId   商品库存id
     * @param spikeTime 创建时间
     * @return
     */
    public int reduceNumber(@Param("spikeId") Long spikeId, @Param("spikeTime") Date spikeTime);

    /**
     * 根据商品库存Id,获取商品秒杀信息
     *
     * @param spikeId
     * @return
     */
    public Spike getSpikeById(Long spikeId);

    /**
     * 根据偏移量和数量，查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<Spike> queryAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 通过存储过程执行秒杀
     */
    public void killByProcedure(Map<String, Object> paramMap);
}
