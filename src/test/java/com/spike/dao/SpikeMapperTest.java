package com.spike.dao;

import com.spike.entity.Spike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit的整合，junit启动时加载springIoc容器
 */

/**
 * Author: tangji
 * Date: 2019 07 15 20:45
 * Description: <...>
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration("classpath:spring/spring-mybatis.xml")
public class SpikeMapperTest {
    //注入dao的实现类
    @Autowired
    private SpikeMapper spikeMapper;

    @Test
    public void reduceNumber() {
        Date date = new Date();
        int update = spikeMapper.reduceNumber(1000L, date);
        System.out.println(update);
    }

    @Test
    public void getSpikeById() {
        long id = 1000;
        Spike spike = spikeMapper.getSpikeById(id);
        System.out.println(spike.getName());
        System.out.println(spike.getStartTime());
    }

    @Test
    public void queryAll() {
        List<Spike> list = spikeMapper.queryAll(0, 100);
        for (Spike spike : list)
            System.out.println(spike);
    }
}