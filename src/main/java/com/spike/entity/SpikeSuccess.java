package com.spike.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Author: tangji
 * Date: 2019 07 15 16:23
 * Description: <...>
 */
@Data
public class SpikeSuccess {
    private Long spikeId;
    private Long userPhone;
    private Short status;
    private Date createTime;
    private Spike spike;
}
