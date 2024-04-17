package com.org.myapplication.utils;

public class UserInfo {
    public long rowid; // 行号
    public int xuhao; // 序号

    public String name; // 姓名
    public int age; // 年龄
    public long height; // 身高
    public float weight; // 体重
    public boolean married; // 婚否
    public String password; // 更新时间
    public String sign; // 签到信息
    public UserInfo() {
        rowid = 0L;
        xuhao = 0;
        name = "";
        age = 0;
        height = 0L;
        weight = 0.0f;
        married = false;
        password = "";
    }

    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    public int getXuhao() {
        return xuhao;
    }

    public void setXuhao(int xuhao) {
        this.xuhao = xuhao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
