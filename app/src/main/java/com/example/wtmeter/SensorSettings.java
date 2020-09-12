package com.example.wtmeter;

public class SensorSettings {
    private String ip;
    private long refreshRate;

    public SensorSettings(String ip, long refreshRate) {
        this.ip = ip;
        this.refreshRate = refreshRate;
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

    public void setRefreshRate(long refreshRate) {
        this.refreshRate = refreshRate;
    }
}
