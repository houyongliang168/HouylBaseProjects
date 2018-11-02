package com.base.hyl.houylbaseprojects.test;

public class TestTen2Two {
    public static void main(String[] args) {
        Ten2Two(-2147483648 );
    }
    /* 十进制 转 换为 二进制 */
    public static void Ten2Two(int n) {
        String result = Integer.toBinaryString(n);
//        int r = Integer.parseInt(result);
        //System.out.println(r);
        System.out.println(result);
    }
}
