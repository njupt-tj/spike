package com.spike.dto;

/**
 * Author: tangji
 * Date: 2019 07 16 19:11
 * Description: <...>
 */
public class Exposer {
    //是否开启秒杀
    private boolean exposed;
    //一种加密措施
    private String md5;
    private long spikeId;
    //系统当前时间
    private long now;
    //秒杀开始时间
    private long start;
    //秒杀结束时间
    private long end;

    public Exposer(boolean exposed, String md5, long spikeId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.spikeId = spikeId;
    }



    public Exposer(boolean exposed, long spikeId, long now, long start, long end) {
        this.exposed = exposed;
        this.spikeId=spikeId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long spikeId) {
        this.exposed = exposed;
        this.spikeId = spikeId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSpikeId() {
        return spikeId;
    }

    public void setSpikeId(long spikeId) {
        this.spikeId = spikeId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", spikeId=" + spikeId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
