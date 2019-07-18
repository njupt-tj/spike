package com.spike.services.Impl;

import com.spike.dao.SpikeMapper;
import com.spike.dao.SpikeSuccessMapper;
import com.spike.dao.cache.RedisDao;
import com.spike.dto.Exposer;
import com.spike.dto.SpikeExecution;
import com.spike.entity.Spike;
import com.spike.entity.SpikeSuccess;
import com.spike.enums.SpikeStateEnum;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;
import com.spike.exception.SpikeException;
import com.spike.services.SpikeService;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: tangji
 * Date: 2019 07 17 8:30
 * Description: <...>
 */
@Service
public class SpikeServiceImpl implements SpikeService {
    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private SpikeMapper spikeMapper;
    @Autowired
    private SpikeSuccessMapper spikeSuccessMapper;

    @Autowired
    private RedisDao redisDao;
    //用于混淆MD5
    private final String slat = "mfamkfkdmgfd%$$#@*&&jkfdfk";

    @Override
    public List<Spike> getAllSpikes() {
        return spikeMapper.queryAll(0, 4);
    }

    @Override
    public Spike getSpike(Long spikeId) {
        return spikeMapper.getSpikeById(spikeId);

    }

    @Override
    public Exposer exportSpikeUrl(Long spikeId) {
        //所有的秒杀单都要调用暴露接口，所以要放到redis做缓存
        Spike spike = redisDao.getSpikeByRedis(spikeId);
        if (spike == null) {
            //访问数据库
            spike = spikeMapper.getSpikeById(spikeId);
            if (spike == null) {
                return new Exposer(false, spikeId);
            } else {
                //放入redis
                redisDao.putSpikeIntoRedis(spike);
            }
        }
        Date startTime = spike.getStartTime();
        Date endTime = spike.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, spikeId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMd5(spikeId);
        return new Exposer(true, md5, spikeId);
    }

    private String getMd5(long spikeId) {
        String base = spikeId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public SpikeExecution executeSpikeByProcedure(Long spikeId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMd5(spikeId))) {
            return new SpikeExecution(spikeId, SpikeStateEnum.DATA_REWQITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("spikeId", spikeId);
        map.put("userPhone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            spikeMapper.killByProcedure(map);
            //获取结果
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SpikeSuccess spikeSuccess = spikeSuccessMapper.queryByIdWithSpike(spikeId, userPhone);
                return new SpikeExecution(spikeId, SpikeStateEnum.SUCCESS, spikeSuccess);
            } else {
                return new SpikeExecution(spikeId, SpikeStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SpikeExecution(spikeId, SpikeStateEnum.INNER_ERROR);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public SpikeExecution executeSpike(Long spikeId, long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException {
        if (md5 == null || !md5.equals(getMd5(spikeId))) {
            throw new SpikeException("用户数据已修改");
        }
        try {
            //执行插入
            int insertCount = spikeSuccessMapper.addSuccessSpikeByIdAndPhone(spikeId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatSpikeException("不能重复秒杀！");
            } else {
                //执行减库存
                int updateCount = spikeMapper.reduceNumber(spikeId, new Date());
                if (updateCount <= 0) {
                    //没有更新记录
                    throw new SpikeCloseException("秒杀结束！");
                } else {
                    //秒杀成功，进行commit
                    SpikeSuccess spikeSuccess = spikeSuccessMapper.queryByIdWithSpike(spikeId, userPhone);
                    return new SpikeExecution(spikeId, SpikeStateEnum.SUCCESS, spikeSuccess);
                }
            }
        }
        //rollback
        catch (SpikeCloseException e1) {
            throw e1;
        } catch (RepeatSpikeException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SpikeException("spike inner error");
        }
    }
}
