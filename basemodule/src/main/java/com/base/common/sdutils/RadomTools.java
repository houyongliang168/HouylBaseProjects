package com.base.common.sdutils;

import java.util.Random;

/**
 * Created by 41113 on 2018/10/10.
 */

public class RadomTools {


    public static String getRandomNum8() {
        StringBuilder str = new StringBuilder();//定义变长字符串
        Random random = new Random();
        //随机生成数字，并添加到字符串
        for (int i = 0; i < 8; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
