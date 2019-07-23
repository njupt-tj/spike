package com.spike.dao;

import com.spike.entity.SpikeSuccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author: tangji
 * Date: 2019 07 15 21:52
 * Description: <...>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-mybatis.xml")
public class SpikeSuccessMapperTest {
    //注入dao的接口
    @Autowired
    private SpikeSuccessMapper spikeSuccessMapper;

    @Test
    public void addSuccessSpike() {
    }

    @Test
    public void addSuccessSpike1() {
        long id=1000L;
        long userPhone=18305169255L;
        int record=spikeSuccessMapper.addSuccessSpikeByIdAndPhone(id, userPhone);
        System.out.println(record);
    }

    @Test
    public void queryByUserPhone() {
    }

    @Test
    public void queryByIdWithSpike() {
        long id=1000L;
        long userPhone=18305169255L;
        SpikeSuccess spikeSuccess=spikeSuccessMapper.queryByIdWithSpike(id, userPhone);
        System.out.println(spikeSuccess);
        System.out.println(spikeSuccess.getSpike());

    }
}