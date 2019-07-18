package com.spike.services;

import com.mysql.cj.protocol.ResultsetRow;
import com.spike.dto.Exposer;
import com.spike.dto.SpikeExecution;
import com.spike.entity.Spike;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.List;

/**
 * Author: tangji
 * Date: 2019 07 17 10:31
 * Description: <...>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class SpikeServiceTest {
    private Logger logger=Logger.getLogger(SpikeServiceTest.class);
    @Autowired
    private SpikeService spikeService;

    @Test
    public void getAllSpikes() {
        List<Spike> list=spikeService.getAllSpikes();
        for(Spike spike:list){
            System.out.println(spike);
        }
    }

    @Test
    public void getSpike() {
        long spikeId=1001L;
        Spike spike=spikeService.getSpike(spikeId);
        System.out.println(spike);

    }

    @Test
    public void testSpikeLogic() {
        long id=1001L;
        Exposer exposer=spikeService.exportSpikeUrl(id);
        if (exposer.isExposed()){
            logger.info(exposer);
            long phone=13101972032L;
            String md5=exposer.getMd5();
            try {
                SpikeExecution spikeExecution= spikeService.executeSpike(id, phone, md5);
                logger.info(spikeExecution);
            } catch (SpikeCloseException e) {
                logger.error(e.getMessage());
            } catch (RepeatSpikeException e) {
                logger.error(e.getMessage());
            }
        }else {
            logger.warn(exposer);
        }
    }
    @Test
    public void testExecuteByProcedure(){
        long id=1000L;
        long phone=13934215342L;
        Exposer exposer = spikeService.exportSpikeUrl(id);
        System.out.println(exposer.isExposed());
        if (exposer.isExposed()){
            String md5=exposer.getMd5();
            SpikeExecution spikeExecution = spikeService.executeSpikeByProcedure(id, phone, md5);
            System.out.println(spikeExecution.getStateInfo());
            logger.info("spikeInfo"+spikeExecution.getStateInfo());
        }


    }
}