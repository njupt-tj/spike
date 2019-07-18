package com.spike.exception;

/**
 * Author: tangji
 * Date: 2019 07 17 9:04
 * Description: <...>
 */
public class SpikeException extends RuntimeException {

    public SpikeException(String message) {
        super(message);
    }

    public SpikeException(String message, Throwable cause) {
        super(message, cause);
    }
}
