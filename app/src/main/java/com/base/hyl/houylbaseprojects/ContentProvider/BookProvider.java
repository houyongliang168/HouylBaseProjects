//package com.base.hyl.houylbaseprojects.ContentProvider;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.net.Uri;
//
//import com.base.common.log.MyLog;
//
///**
// *  创建一个内容提供者
// */
//public class BookProvider extends ContentProvider {
//    private static final String TAG=BookProvider.class.getSimpleName();
//    @Override
//    public boolean onCreate() {
//        MyLog.wtf(TAG,"onCreate ,current thread :"+Thread.currentThread());
//
//        return false;
//    }
//
//    @androidx.annotation.Nullable
//    @Override
//    public Cursor query(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String[] projection, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs, @androidx.annotation.Nullable String sortOrder) {
//        MyLog.wtf(TAG,"query :");
//
//        return null;
//    }
//
//    @androidx.annotation.Nullable
//    @Override
//    public String getType(@androidx.annotation.NonNull Uri uri) {
//        MyLog.wtf(TAG,"getType :");
//        return null;
//    }
//
//    @androidx.annotation.Nullable
//    @Override
//    public Uri insert(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values) {
//
//        MyLog.wtf(TAG,"insert :");
//        return null;
//    }
//
//    @Override
//    public int delete(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
//        MyLog.wtf(TAG,"delete :");
//        return 0;
//    }
//
//    @Override
//    public int update(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
//        MyLog.wtf(TAG,"update :");
//        return 0;
//    }
//}
