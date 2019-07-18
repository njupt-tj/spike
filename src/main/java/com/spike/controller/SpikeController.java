package com.spike.controller;

import com.spike.dto.Exposer;
import com.spike.dto.SpikeExecution;
import com.spike.dto.SpikeResult;
import com.spike.entity.Spike;
import com.spike.enums.SpikeStateEnum;
import com.spike.exception.RepeatSpikeException;
import com.spike.exception.SpikeCloseException;
import com.spike.services.SpikeService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.log4j.Logger;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Author: tangji
 * Date: 2019 07 17 14:26
 * Description: <...>
 */
@Controller
@RequestMapping("/spike")// url:/模块/资源/{id}/细分
public class SpikeController {
    private Logger logger = Logger.getLogger(SpikeController.class);
    @Autowired
    private SpikeService spikeService;

    /**
     * 列表页请求
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Spike> list = spikeService.getAllSpikes();
        model.addAttribute("list", list);
        return "list";
    }

    /**
     * 详情页请求
     *
     * @param spikeId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{spikeId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("spikeId") Long spikeId, Model model) {
        if (spikeId == null) {
            //重定向
            return "redirect:/spike/list";
        }
        Spike spike = spikeService.getSpike(spikeId);
        if (spike == null) {
            //请求转发
            return "forward:/spike/list";
        }
        model.addAttribute("spike", spike);
        return "detail";
    }

    /**
     * 暴露地址
     *
     * @param spikeId
     */
    @RequestMapping(value = "/{spikeId}/exposer", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SpikeResult<Exposer> exposer(@PathVariable("spikeId") Long spikeId) {
        SpikeResult<Exposer> result;
        try {
            Exposer exposer = spikeService.exportSpikeUrl(spikeId);
            result = new SpikeResult<>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SpikeResult<>(false, e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀
     *
     * @param spikeId
     * @param md5
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{spikeId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SpikeResult<SpikeExecution> execute(@PathVariable("spikeId") Long spikeId,
                                               @PathVariable("md5") String md5,
                                               @CookieValue(value = "spikePhone", required = false) Long userPhone) {
        System.out.println(spikeId+" "+md5+" "+userPhone);
        if (userPhone == null) {
            return new SpikeResult<>(false, "用户未注册");
        }
        SpikeResult<SpikeExecution> result;
        try {
            SpikeExecution spikeExecution = spikeService.executeSpike(spikeId, userPhone, md5);
            result = new SpikeResult<>(true, spikeExecution);
        } catch (RepeatSpikeException e) {
            SpikeExecution execution = new SpikeExecution(spikeId, SpikeStateEnum.REPEAT_KILL);
            return new SpikeResult<>(true, execution);
        } catch (SpikeCloseException e) {
            SpikeExecution execution = new SpikeExecution(spikeId, SpikeStateEnum.INNER_ERROR);
            return new SpikeResult<>(true, execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SpikeResult<>(true, e.getMessage());
        }
        return result;
    }
    /**
     * 验证系统时间
     */
    @RequestMapping(value = "/currentTime",method = RequestMethod.GET)
    @ResponseBody
    public SpikeResult<Long> time(){
        Date date=new Date();
        return new SpikeResult<>(true, date.getTime());
    }
}
