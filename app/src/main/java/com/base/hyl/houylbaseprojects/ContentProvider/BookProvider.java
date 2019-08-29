package com.base.hyl.houylbaseprojects.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.base.common.log.MyLog;

/**
 *  创建一个内容提供者
 */
public class BookProvider extends ContentProvider {
    private static final String TAG=BookProvider.class.getSimpleName();
    @Override
    public boolean onCreate() {
        MyLog.wtf(TAG,"onCreate ,current thread :"+Thread.currentThread());

        return false;
    }


    @Override
    public Cursor query(Uri uri,  String[] projection, String selection, String[] selectionArgs,  String sortOrder) {
        MyLog.wtf(TAG,"query :");

        return null;
    }


    @Override
    public String getType( Uri uri) {
        MyLog.wtf(TAG,"getType :");
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        MyLog.wtf(TAG,"insert :");
        return null;
    }

    @Override
    public int delete( Uri uri, String selection,  String[] selectionArgs) {
        MyLog.wtf(TAG,"delete :");
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,String selection,  String[] selectionArgs) {
        MyLog.wtf(TAG,"update :");
        return 0;
    }
}
