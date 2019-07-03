package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wzgiceman.rxretrofitlibrary.db.DaoMaster;
import com.wzgiceman.rxretrofitlibrary.db.DaoSession;
import com.wzgiceman.rxretrofitlibrary.db.DownInfoDao;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * 断点续传
 * 数据库工具类-geendao运用
 * Created by WZG on 2016/10/25.
 */

public class DbDwonUtil {
    private static DbDwonUtil db;
    private final static String dbName = "live_cache_db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;


    public DbDwonUtil() {
        context = RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static DbDwonUtil getInstance() {
        if (db == null) {
            synchronized (DbDwonUtil.class) {
                if (db == null) {
                    db = new DbDwonUtil();
                }
            }
        }
        return db;
    }


    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    public void save(DownInfo info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
        downInfoDao.insert(info);
    }

    public void update(DownInfo info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
        downInfoDao.update(info);
    }

    public void deleteDowninfo(DownInfo info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
        downInfoDao.delete(info);
    }


    /**
     * 根据liveId判断视频已存在数据库中(null不存在，downinfo存在)
     *
     * @param liveId
     * @return
     */
    public DownInfo queryDownById(String liveId) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
        QueryBuilder<DownInfo> qb = downInfoDao.queryBuilder();
        qb.where(DownInfoDao.Properties.LiveId.eq(liveId));
        List<DownInfo> list = qb.list();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 数据库中所有记录
     *
     * @return
     */
    public List<DownInfo> queryDownAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
        QueryBuilder<DownInfo> qb = downInfoDao.queryBuilder();
        return qb.list();
    }

    /**
     * 增加 通过url 查询DownInfo 的信息
     *
     * @param url
     * @return
     */
    public DownInfo queryDownByUrl(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
        QueryBuilder<DownInfo> qb = downInfoDao.queryBuilder();
        qb.where(DownInfoDao.Properties.Url.eq(url));
        List<DownInfo> list = qb.list();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
