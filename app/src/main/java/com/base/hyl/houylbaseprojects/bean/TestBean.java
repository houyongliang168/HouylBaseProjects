package com.base.hyl.houylbaseprojects.bean;

public class TestBean {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public    String url;

    public  String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
