package com.spike.exception;

/**
 * 秒杀关闭异常
 * Author: tangji
 * Date: 2019 07 17 8:24
 * Description: <...>
 */
public class SpikeCloseException extends RuntimeException {
    public SpikeCloseException(String message) {
        super(message);
    }

    public SpikeCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
