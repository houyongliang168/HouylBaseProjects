package com.base.hyl.houylbaseprojects.序列化;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 5135842561086074676L;
    private int userId;
    public String userName;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
