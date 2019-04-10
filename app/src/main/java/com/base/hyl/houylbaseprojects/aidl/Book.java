package com.base.hyl.houylbaseprojects.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String name;
    public int bookId;


    public Book() {
    }
  public Book(String name) {
      this.name = name;
    }

    public Book(String name, int bookId) {
        this.name = name;
        this.bookId = bookId;
    }

    protected Book(Parcel in) {
        name = in.readString();
        bookId = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }


    @Override
    public String toString() {
        return "book name：" + name+"_id:"+bookId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(bookId);
    }
}
