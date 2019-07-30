package com.spike.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: tangji
 * Date: 2019 07 26 21:10
 * Description: <...>
 */
@Data
public class Mail implements Serializable {
    public static final long serialVersionUID=312983429542002L;
    private String from;
    private String to;
    private String subject;
    private String text;
}
