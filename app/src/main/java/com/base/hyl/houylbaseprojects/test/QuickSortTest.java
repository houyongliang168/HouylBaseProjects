package com.base.hyl.houylbaseprojects.test;

import java.util.ArrayList;
import java.util.List;

public class QuickSortTest {
    /* 测试的入口 未完待续*/
    public static void main(String[] args) {
        QuickBean q1 = new QuickBean(7, "哈哈");
        QuickBean q2 = new QuickBean(6, "哈哈1");
        QuickBean q3 = new QuickBean(15, "哈哈2");

        QuickBean q4 = new QuickBean(4, "哈哈0");
        QuickBean q5 = new QuickBean(3, "哈哈3");
        QuickBean q6 = new QuickBean(89, "哈哈4");
        QuickBean q7 = new QuickBean(1, "哈哈");

        QuickBean[]   ss = {q1, q2, q3, q4, q5, q6};//获取一个数组

       // FastSort(ss,0,5);
//        for (int i = 0; i < ss.length; i++) {
//            System.out.println(ss[i].toString());
//        }

        List<QuickBean> list=new ArrayList<>();
        list.add(q1);
        list.add(q2);
        list.add(q3);
        list.add(q4);
        list.add(q5);
        list.add(q6);
        list.add(q7);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        FastSortList(list,0,6);
        System.out.println("\n \n \n\n");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        String S="ASDFASDF";
        setValue(S);
        System.out.println(S);
        int i=466546;
        setValueInt(i);
        System.out.println(i);

        //发现 基本数据类型 和 String 经过方法处理后 值不变化  String本质是final类型char数组
        // 而数组 和 list 集合 经过 方法处理后  期 里面的值会发生变化
        //说明 基本数据类型 和String 是值拷贝，而 数组和list 集合处理的是引用


        StringBuilder str = new StringBuilder("546");
        setValue(str);

        System.out.println(str.toString()); //输出空字符串


        setValue2(str);
        System.out.println(str.toString()); //输出sss




    }

    public static void setValue(StringBuilder str){
        str = new StringBuilder("sss111");
    }

    public static void setValue2(StringBuilder str){
        str.append("sss222");
    }


    public static void setValue(String ss){
        ss="adfasdfa";

    }
    public static void setValueInt(int ss){
        ss=8888;

    }
    public static class QuickBean {
        int qucikNum;//定义一个数据
        String name;//名称

        public QuickBean(int qucikNum, String name) {
            this.qucikNum = qucikNum;
            this.name = name;
        }

        @Override
        public String toString() {
            return "QuickBean{" +
                    "qucikNum=" + qucikNum +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getQucikNum() {
            return qucikNum;
        }

        public void setQucikNum(int qucikNum) {
            this.qucikNum = qucikNum;
        }
    }


    public static void FastSortList( List<QuickBean> list, int low, int high) {

        if (low > high) {
            return;
        }
        int start = low;
        int end = high;
        QuickBean qb;

        while (true) {



            while (list.get(start).qucikNum <= list.get(low).qucikNum &&start<high) {
                start++;
            }
            //顺序很重要，要从右边开始找
            while (list.get(end).qucikNum >=list.get(low).qucikNum  &&end>low) {
                end--;
            }
            //交换
            if (start < end) {
                qb = list.get(start);
//                list.add(start,list.get(end)) ;
//                list.remove(start + 1);
//                list.add(end,qb) ;
//                list.remove(end + 1);
                list.set(start,list.get(end));
                list.set(end,qb);
            }else{
                break;
            }


        }
        //最终将基准数归位,将start 和 low 互换
        qb = list.get(start);

//        list.add(start,list.get(low)) ;
//        list.remove(start + 1);
//        list.add(low,qb) ;
//        list.remove(low + 1);

        list.set(end,list.get(low));
        list.set(low,qb);
        FastSortList( list,low,  end-1);//继续处理左边的
        FastSortList( list, end+1,  high);//继续处理右边的
        return;

    }

    public static void FastSort(QuickBean[] ss, int low, int high) {

        if (low > high) {
            return;
        }
        int start = low;
        int end = high;
        QuickBean qb;

        while (start != end) {
            //顺序很重要，要从右边开始找
            while (ss[end].qucikNum >= ss[low].qucikNum && start < end) {
                end--;
            }

            while (ss[start].qucikNum <= ss[low].qucikNum && start < end) {
                start++;
            }
            //交换
            if (start < end) {
                qb = ss[start];
                ss[start] = ss[end];
                ss[end] = qb;
            }

        }
        //最终将基准数归位,将start 和 low 互换
        qb = ss[start];
        ss[start] = ss[low];
        ss[low] = qb;
        FastSort( ss,low,  start-1);//继续处理左边的
        FastSort( ss, start+1,  high);//继续处理右边的
        return;

    }
}
