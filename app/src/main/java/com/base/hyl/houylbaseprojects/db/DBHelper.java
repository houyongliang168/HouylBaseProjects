package com.base.hyl.houylbaseprojects.db;

import android.content.Context;
import android.util.Log;
import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.xiazai.db.DownInfo;
import org.greenrobot.greendao.query.QueryBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houyongliang on 2017/7/10.
 * 定义一个帮助类 帮忙处理 增删改查
 */

public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper instance;
    private static Context appContext;

    /*表内容操作对象*/
    private DaoSession daoSession;

    private DownInfoDao mDownInfoDao;

    private DBHelper() {
    }

    //单例模式，DBHelper只初始化一次
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper();
            if (appContext == null) {
                appContext = App.getContext();
            }
            instance.daoSession = App.getDaoSession();
            instance.mDownInfoDao = instance.daoSession.getDownInfoDao();

        }
        return instance;
    }

//    public void createDB() {
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(appContext, "iscals-db", null);
//        //获取数据库对象
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
//        //获取Dao对象管理者
//        daoSession = daoMaster.newSession();
//        CustomInfoBeanDao contactsInfoDao = daoSession.getCustomInfoBeanDao();
//    }

    /* 插入一条数据*/
    public void deletTable() {
        mDownInfoDao.dropTable(daoSession.getDatabase(), false);
    }


    /* 插入一条数据*/
    public boolean insertDownInfoBean(DownInfo info) {
        boolean flag = false;
        flag = mDownInfoDao.insert(info) == -1 ? false : true;
        Log.i(TAG, "insert CustomInfoBean :" + flag + "-->" + info.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param
     * @return
     */
    public boolean insertMultDownInfoBean(final List<DownInfo> infos) {
        boolean flag = false;
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DownInfo info : infos) {
                        mDownInfoDao.insertOrReplace(info);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @param info
     * @return
     */
    public boolean updateDownInfoBean(DownInfo info) {
        boolean flag = false;
        try {
            daoSession.update(info);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除单条记录
     *
     * @param info
     * @return
     */
    public boolean deleteDownInfoBean(DownInfo info) {
        boolean flag = false;
        try {
            //按照id删除
            daoSession.delete(info);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            daoSession.deleteAll(DownInfo.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<DownInfo> queryAllDownInfoBean() {
        return daoSession.loadAll(DownInfo.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public DownInfo queryDownInfoBeanById(long key) {
        return daoSession.load(DownInfo.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<DownInfo> queryDownInfoBeanByNativeSql(String sql, String[] conditions) {
        return daoSession.queryRaw(DownInfo.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<DownInfo> queryCustomInfoBeanByQueryBuilder(long id) {
        QueryBuilder<DownInfo> queryBuilder = daoSession.queryBuilder(DownInfo.class);
        return queryBuilder.where(DownInfoDao.Properties.Id.eq(id)).list();
    }

    /**
     * 客户id精确查询
     *
     * @param id 服务器返回的 id
     * @return
     * @author zcc
     */
    public List<DownInfo> queryCustomInfoBeanByIdNow(String id) {
        QueryBuilder queryBuilder = mDownInfoDao.queryBuilder();
        return queryBuilder.where(DownInfoDao.Properties.Id.eq(id)).list();
    }


    /**
     * liveid精确查询
     *
     * @param id 服务器返回的 id
     * @return
     * @author zcc
     */
    public List<DownInfo> queryDownInfoBeanByLiveIdNow(String id) {
        QueryBuilder queryBuilder = mDownInfoDao.queryBuilder();
        return queryBuilder.where(DownInfoDao.Properties.LiveId.eq(id)).list();
    }

    /**
     * 客户姓名模糊查询
     *
     * @param name 客户姓名
     * @return
     * @author zcc
     */














    /**
     * 修改多条数据
     *
     * @param infos CustomInfoBean数据类型
     * @return
     */
    public boolean updateMultDownInfoBean(final List<DownInfo> infos) {
        boolean flag = false;
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DownInfo info : infos) {
                        mDownInfoDao.update(info);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    /**
     * 移除多条数据
     *
     * @param infos CustomInfoBean数据类型
     * @return
     */
    public boolean deleteMultDownInfoBean(final List<DownInfo> infos) {
        boolean flag = false;
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DownInfo info : infos) {
                        mDownInfoDao.delete(info);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    /**
     * 移除单条数据 通过id 处理
     *
     * @param id id的集合
     * @return
     */
    public boolean deleteDownInfoById(final long id) {
        boolean flag = false;

        final DownInfo bean=   queryDownInfoBeanById(id);

        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                   mDownInfoDao.delete(bean);
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean deleteMultDownInfoByLiveId(final List<String> ids) {
        boolean flag = false;
        final List<DownInfo> list = new ArrayList<>();
        for (String info : ids) {
            List<DownInfo> beans = queryDownInfoBeanByLiveIdNow(info);
            if (beans != null && beans.size() > 0) {
                list.addAll(beans);
            }
        }
        if(list.size()<1){
            return flag;
        }
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DownInfo info : list) {
                        mDownInfoDao.delete(info);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public boolean deleteDownInfoByLiveId(final String ids) {
        boolean flag = false;
        final List<DownInfo> list = new ArrayList<>();

            List<DownInfo> beans = queryDownInfoBeanByLiveIdNow(ids);
            if (beans != null && beans.size() > 0) {
                list.addAll(beans);
            }

        if(list.size()<1){
            return flag;
        }
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DownInfo info : list) {
                        mDownInfoDao.delete(info);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

}
