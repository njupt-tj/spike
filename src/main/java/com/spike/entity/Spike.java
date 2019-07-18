package com.spike.entity;

import lombok.Data;

import java.util.Date;


/**
 * Author: tangji
 * Date: 2019 07 15 16:13
 * Description: <...>
 */
@Data
public class Spike {
    private Long spikeId;
    private Integer number;
    private String name;
    private Date startTime;
    private Date endTime;
    private Date createTime;
}
