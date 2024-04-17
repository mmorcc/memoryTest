package com.org.myapplication.bean;

import com.org.myapplication.utils.Util;

public class GoodType {
    private int type;
    public int times;
    public int cTimes=0;
    private String name;

    /**
     *   if(type==0){
     *             name="64布局";
     *         }else if(type==1){
     *             name="2布局";
     *         }else if(type==2){
     *             name="4布局";
     *         }else if(type==3){
     *             name="8布局";
     *         }
     * @param type
     * @param times
     */
    public GoodType(int type, int times) {
        this.type = type;
        this.times = times;
        if(type==0){
            name="64布局";
        }else if(type==1){
            name="2布局";
        }else if(type==2){
            name="4布局";
        }else if(type==3){
            name="8布局";
        }
    }

    public int getcTimes() {
        return cTimes;
    }

    public void setcTimes(int cTimes) {
        Util.l("点击数+1");
        this.cTimes = cTimes;
        Util.zhendong();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
