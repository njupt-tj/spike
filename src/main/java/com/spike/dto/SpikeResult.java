package com.spike.dto;

/**
 * 所以ajax返回的结果，封装json结果
 * Author: tangji
 * Date: 2019 07 17 14:58
 * Description: <...>
 */
public class SpikeResult<T> {
    private boolean success;
    private T data;
    private String error;

    public SpikeResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public SpikeResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }



}
