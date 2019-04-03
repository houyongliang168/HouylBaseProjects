package com.base.hyl.houylbaseprojects.序列化;

import android.os.Parcel;
import android.os.Parcelable;


public class UserP implements Parcelable {
    private int userId;
    public String userName;
    public boolean isMale;

    public UserA getUser() {
        return user;
    }

    public void setUser(UserA user) {
        this.user = user;
    }

    public UserA user;


    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }





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

    /**
     * 从 parcel 序列化提供的几个方法 来看，其 主要
     * @return
     */


    /*返回当前对象的内容描述 默认为0 ，如果有文件描述符返回1*/
    @Override
    public int describeContents() {
        return 0;
    }
    /*将对象写入序列化结构中 一般为0  为1 是将对象最为返回值返回*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(user,0);
    }
    /*从序列化对象中创建原始对象*/
    protected UserP(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
        user=in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<UserP> CREATOR = new Creator<UserP>() {
        /*从序列化的对象中创建原始的对象*/
        @Override
        public UserP createFromParcel(Parcel in) {
            return new UserP(in);
        }
        /*创建指定长度的原始数组对象*/
        @Override
        public UserP[] newArray(int size) {
            return new UserP[size];
        }
    };

}
