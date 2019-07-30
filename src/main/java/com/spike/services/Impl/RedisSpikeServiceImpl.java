package com.spike.services.Impl;

import com.spike.dao.SpikeSuccessMapper;
import com.spike.dao.cache.RedisDao;
import com.spike.dto.SpikeExecution;
import com.spike.entity.SpikeSuccess;
import com.spike.enums.SpikeStateEnum;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;
import com.spike.exception.SpikeException;
import com.spike.services.RedisSpikeService;
import com.spike.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Author: tangji
 * Date: 2019 07 28 20:01
 * Description: <...>
 */
@Service
public class RedisSpikeServiceImpl implements RedisSpikeService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SpikeSuccessMapper spikeSuccessMapper;

    @Override
    public SpikeExecution executeSpikeByRedis(Long spikeId, Long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException {
        if (md5 == null || !md5.equals(MD5Util.getMd5(spikeId))) {
            return new SpikeExecution(spikeId, SpikeStateEnum.DATA_REWQITE);
        }
        try {
            //先标记
            boolean isAccess = redisDao.markUserAccessGood(spikeId, userPhone);
            boolean flag ;
            if (isAccess) {
                // 预减库存
                try {
                    flag = redisDao.reduceStock(spikeId);
                    if (flag) {
                        //减库存成功,创建订单
                        int insertState = spikeSuccessMapper.addSuccessSpikeByIdAndPhone(spikeId, userPhone);
                        SpikeSuccess spikeSuccess = spikeSuccessMapper.queryByIdWithSpike(spikeId, userPhone);
                        return new SpikeExecution(spikeId, SpikeStateEnum.SUCCESS, spikeSuccess);
                    } else {
                        throw new SpikeCloseException("秒杀结束");
                    }
                } catch (SpikeCloseException e2) {
                    throw e2;
                } catch (Exception e3) {
                    logger.error(e3.getMessage(), e3);
                    throw new SpikeException("inner error");
                }
            } else {
                throw new RepeatSpikeException("不能重复秒杀");
            }
        } catch (RepeatSpikeException e1) {
            throw e1;
        }
    }
}