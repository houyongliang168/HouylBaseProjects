package com.base.common.Bean;

/**
 * Created by 41113 on 2018/6/11.
 */

public class StringAndBooleanBean {
    private String name;
    private boolean isChoosed=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        this.isChoosed = choosed;
    }

    @Override
    public String toString() {
        return "StringAndBooleanBean{" +
                "name='" + name + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
