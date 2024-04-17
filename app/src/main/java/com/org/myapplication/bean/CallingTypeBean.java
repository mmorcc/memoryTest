package com.org.myapplication.bean;

import java.util.List;

public class CallingTypeBean {

    private long count;
    private List<CallingType> devices;//类型名称

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<CallingType> getDevices() {
        return devices;
    }

    public void setDevices(List<CallingType> devices) {
        this.devices = devices;
    }
}
