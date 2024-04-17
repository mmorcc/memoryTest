package com.org.myapplication.bean;


public class Calling {
    private Integer id;
    private String deviceName;//设备名称
    private String deviceId;//设备id
    private long callTime;//设备呼叫时间
    private long answerTime;//设备响应时间
    private long finishTime;//设备完成呼叫时间
    private int typeId;//呼叫类型
    private String typeName;//呼叫名称
    private String des;//其它描述

    @Override
    public String toString() {
        return "Calling{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", callTime=" + callTime +
                ", answerTime=" + answerTime +
                ", finishTime=" + finishTime +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", des='" + des + '\'' +
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

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(long answerTime) {
        this.answerTime = answerTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
