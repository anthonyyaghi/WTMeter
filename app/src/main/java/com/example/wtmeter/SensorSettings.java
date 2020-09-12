package com.example.wtmeter;

import java.io.Serializable;

public class SensorSettings implements Serializable {
    private String ip;
    private long refreshRate;
    private String name;

    public SensorSettings(String ip, long refreshRate, String name) {
        this.ip = ip;
        this.refreshRate = refreshRate;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getRefreshRate() {
        return refreshRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRefreshRate(long refreshRate) {
        this.refreshRate = refreshRate;
    }
}
