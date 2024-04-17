package com.org.myapplication.bean;

public class Device {
    private Integer id;
    private String deviceName;//设备名称
    private String deviceId;//设备名称
    private long lastTime;//设备最后在线时间
    private String pwd;//设备密码
    private Calling calling;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", lastTime=" + lastTime +
                ", pwd='" + pwd + '\'' +
                ", calling=" + calling +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Calling getCalling() {
        return calling;
    }

    public void setCalling(Calling calling) {
        this.calling = calling;
    }
}
