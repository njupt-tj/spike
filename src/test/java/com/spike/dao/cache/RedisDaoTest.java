package com.spike.dao.cache;

import com.spike.dao.SpikeMapper;
import com.spike.entity.Spike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author: tangji
 * Date: 2019 07 18 16:30
 * Description: <...>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-mybatis.xml")
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SpikeMapper spikeMapper;
    @Test
    public void testSpike() throws Exception {
        long id=1001L;
        Spike spike=redisDao.getSpikeByRedis(id);
        if (spike==null){
            //缓存没有，从数据库取
            spike=spikeMapper.getSpikeById(id);
            if (spike!=null){
                //写入缓存
                String result=redisDao.putSpikeIntoRedis(spike);
                System.out.println(result);
                Spike spikeByRedis = redisDao.getSpikeByRedis(id);
                System.out.println(spikeByRedis);
            }
        }
        System.out.println("redis"+spike);

    }
}