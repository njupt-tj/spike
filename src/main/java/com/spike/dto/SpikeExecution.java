package com.spike.dto;

import com.spike.entity.SpikeSuccess;
import com.spike.enums.SpikeStateEnum;

/**
 * 封装秒杀执行后的结果
 * Author: tangji
 * Date: 2019 07 16 19:21
 * Description: <...>
 */
public class SpikeExecution {
    private long spikeId;
    //结果状态
    private int status;
    //状态表示
    private String stateInfo;
    //秒杀成功对象
    private SpikeSuccess spikeSuccess;

    public SpikeExecution(long spikeId, SpikeStateEnum spikeStateEnum, SpikeSuccess spikeSuccess) {
        this.spikeId = spikeId;
        this.status = spikeStateEnum.getState();
        this.stateInfo = spikeStateEnum.getStateInfo();
        this.spikeSuccess = spikeSuccess;
    }

    public SpikeExecution(long spikeId, SpikeStateEnum spikeStateEnum) {
        this.spikeId = spikeId;
        this.status = spikeStateEnum.getState();
        this.stateInfo = spikeStateEnum.getStateInfo();
    }

    public long getSpikeId() {
        return spikeId;
    }

    public void setSpikeId(long spikeId) {
        this.spikeId = spikeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SpikeSuccess getSpikeSuccess() {
        return spikeSuccess;
    }

    public void setSpikeSuccess(SpikeSuccess spikeSuccess) {
        this.spikeSuccess = spikeSuccess;
    }
}
