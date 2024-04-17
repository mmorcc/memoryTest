package com.example.lib;

import java.util.ArrayList;
import java.util.Random;

public class MyClass {
    public static void main(String[] args){

//        // 创建一个数组
//        ArrayList<String> sites = new ArrayList<>();
//
//        sites.add("Google");
//        sites.add("Runoob");
//        sites.add("Taobao");
//        System.out.println("all " + sites);
//        sites.addAll(sites);
//
//        // 删除元素 Taobao
//        String result = sites.remove(0);
//        System.out.println("Titem " + result);
//        System.out.println("all2= " + sites);
        int g=2;
        double percentage = (double)g /5 *100;
        System.out.println("Per="+(getIndex(10)>5));
    }
    public static Random random=new Random();
    public static int getIndex(int max){
        int i=random.nextInt(max);

        return i;
    }
}