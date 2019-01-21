package com.base.Bean;

/**
 * Created by QunCheung on 2017/12/29.
 */

public class Paramaters{
    private String key;
    private String value;

    public Paramaters(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
