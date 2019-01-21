package com.base.Bean;

/**
 * Created by QunCheung on 2017/9/18.
 */

public class MenuPopwindowBean {
    private int icon;
    private String text;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MenuPopwindowBean(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public MenuPopwindowBean() {
    }
}
