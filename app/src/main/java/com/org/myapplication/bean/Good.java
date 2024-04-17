package com.org.myapplication.bean;

public class Good {
    private String name;
    private int id;
    private String type;
    private String type2;
    private String type3;
    private int index;

    public Good(String name, int id, String type, String type2, String type3) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.type2 = type2;
        this.type3 = type3;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }
}
