package com.spike.dao;

import com.spike.entity.SpikeSuccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.net.idn.Punycode;

import java.util.List;

/**
 * Author: tangji
 * Date: 2019 07 15 17:00
 * Description: <...>
 */
@Mapper
public interface SpikeSuccessMapper {

    /**
     * 传入秒杀成功信息插入购买明细
     *
     * @param spikeSuccess
     * @return
     */
    public int addSuccessSpike(SpikeSuccess spikeSuccess);

    /**
     * 根据商品库存id和用户手机号，插入秒杀明细,可过滤重复，也就是避免重复秒杀
     *
     * @param spikeId
     * @param userPhone
     * @return
     */
    public int addSuccessSpikeByIdAndPhone(@Param("spikeId") Long spikeId, @Param("userPhone") Long userPhone);

    /**
     * 根据用户手机号来查询用户的秒杀信息
     *
     * @param userPhone
     * @return
     */
    public List<SpikeSuccess> queryByUserPhone(Long userPhone);

    /**
     * 根据Id和号码查询SpikeSuccess并携带秒杀商品对象实体
     *
     * @param spikeId
     * @return
     */
    public SpikeSuccess queryByIdWithSpike(@Param("spikeId") Long spikeId, @Param("userPhone") Long userPhone);

}
