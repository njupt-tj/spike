package com.spike.enums;

/**
 * Author: tangji
 * Date: 2019 07 17 9:23
 * Description: <...>
 */
public enum SpikeStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWQITE(-3,"数据篡改");
    private int state;
    private String stateInfo;

    SpikeStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SpikeStateEnum stateOf(int index) {
        for (SpikeStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
