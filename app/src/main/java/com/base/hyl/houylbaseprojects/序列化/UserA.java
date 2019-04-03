package com.base.hyl.houylbaseprojects.序列化;

import android.os.Parcel;
import android.os.Parcelable;

public class UserA implements Parcelable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected UserA(Parcel in) {
        name = in.readString();
    }

    public static final Creator<UserA> CREATOR = new Creator<UserA>() {
        @Override
        public UserA createFromParcel(Parcel in) {
            return new UserA(in);
        }

        @Override
        public UserA[] newArray(int size) {
            return new UserA[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
