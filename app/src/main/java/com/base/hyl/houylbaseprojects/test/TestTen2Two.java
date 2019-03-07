package com.base.hyl.houylbaseprojects.test;

import android.text.TextUtils;

import java.util.regex.Pattern;

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

    public static String getString2(String ss){
        if(TextUtils.isEmpty(ss)){
            return ss;
        }
        StringBuilder sb=new StringBuilder();
        StringBuilder sb2=new StringBuilder();
        for (int i = 0; i < ss.length(); i++) {
            if(isNumeric(ss.charAt(i)+"" ) ){
                sb2.append(i);
            }else{
                sb.append(i);
            }
        }
        return sb.toString()+sb2.toString();

    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
