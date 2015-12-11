package com.derek.mypractice.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.derek.mypractice.ipc.aidl.Book;

import java.io.Serializable;

/**
 * Created by derek on 15/12/8.
 */
public class User implements Parcelable, Serializable {
    public int userId;
    public String userName;
    public boolean isMale;

    public Book book;


    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
        dest.writeParcelable(book, 0);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
        // 由于book是另一个可序列化对象，所以它的反序列化过程需要传递当前的上下文类加载器，否则会报无法找到类的错误。
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public String toString() {
        return String.format(
                "User:{userId:%s, userName:%s, isMale:%s}, with child:{%s}",
                userId, userName, isMale, book);
    }
}
