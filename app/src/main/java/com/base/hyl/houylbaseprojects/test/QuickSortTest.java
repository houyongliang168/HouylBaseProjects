package com.base.hyl.houylbaseprojects.test;

public class QuickSortTest {
    /* 测试的入口 未完待续*/
    public static void main(String[] args) {
        QuickBean q1 = new QuickBean(25, "哈哈");
        QuickBean q2 = new QuickBean(2, "哈哈1");
        QuickBean q3 = new QuickBean(89, "哈哈2");
        QuickBean q4 = new QuickBean(-5, "哈哈0");
        QuickBean q5 = new QuickBean(28, "哈哈3");
        QuickBean q6 = new QuickBean(0, "哈哈4");

        QuickBean[]   ss = {q1, q2, q3, q4, q5, q6};//获取一个数组

        FastSort(ss,0,5);
        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i].toString());
        }


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

            while (ss[start].qucikNum >= ss[low].qucikNum && start < end) {
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
