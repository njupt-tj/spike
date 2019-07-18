package com.spike.exception;

/**
 * 重复秒杀异常
 * Author: tangji
 * Date: 2019 07 16 22:34
 * Description: <...>
 */
public class RepeatSpikeException extends RuntimeException{
    public RepeatSpikeException(String message) {
        super(message);
    }

    public RepeatSpikeException(String message, Throwable cause) {
        super(message, cause);
    }
}
