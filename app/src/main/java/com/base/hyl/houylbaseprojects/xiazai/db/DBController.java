package com.base.hyl.houylbaseprojects.xiazai.db;

import android.content.Context;

import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.db.DBHelper;
import com.base.hyl.houylbaseprojects.download.DownloadService;
import com.base.hyl.houylbaseprojects.download.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.db.DownloadDBController;
import com.base.hyl.houylbaseprojects.download.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.download.domain.DownloadThreadInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/3/2.
 */

public class DBController implements DownloadDBController {

    private static DBController instance;
    private final Context context;
    private final DBHelper dbHelper;

    private final DownloadManager downloadManager;
    private final DownloadDBController downloadDBController;

    public DBController(Context context) throws SQLException {
        this.context = context;
        dbHelper = DBHelper.getInstance(context);
        downloadManager = DownloadService.getDownloadManager(App.getContext());
        downloadDBController = downloadManager.getDownloadDBController();
    }

    public static DBController getInstance(Context context) throws SQLException {
        if (instance == null) {
            instance = new DBController(context);
        }
        return instance;
    }


    public void createOrUpdateDownloadInfo(DownInfo downInfo)
            throws SQLException {
        dbHelper.updateDownInfoBean(downInfo);
    }

    public boolean deleteDownloadInfoByLiveId(String id)
            throws SQLException {
        return dbHelper.deleteDownInfoByLiveId(id);
    }


    @Override
    public List<DownloadInfo> findAllDownloading() {
        return    downloadDBController.findAllDownloading();

    }


    @Override
    public List<DownloadInfo> findAllDownloaded() {
        return  downloadDBController.findAllDownloaded();

    }

    @Override
    public DownloadInfo findDownloadedInfoById(String id) {
        return  downloadDBController.findDownloadedInfoById(id);

    }

    @Override
    public void pauseAllDownloading() {
        downloadDBController.pauseAllDownloading();

    }

    @Override
    public void createOrUpdate(DownloadInfo downloadInfo) {
        downloadDBController.createOrUpdate(downloadInfo);

    }


    public void createOrUpdate(DownInfo downInfo) {
        List<DownInfo> downInfos = dbHelper.queryDownInfoBeanByLiveIdNow(downInfo.getLiveId());
        if(downInfos==null||downInfos.size()==0){
            dbHelper.insertDownInfoBean(downInfo)   ;
        }else{
            dbHelper .updateDownInfoBean(downInfo);
        }
    }
    @Override
    public void createOrUpdate(DownloadThreadInfo downloadThreadInfo) {
        downloadDBController.createOrUpdate(downloadThreadInfo);

    }

    @Override
    public void delete(DownloadInfo downloadInfo) {
        downloadDBController.delete(downloadInfo);

    }


    public void delete(DownInfo downInfo){
        dbHelper.deleteDownInfoBean(downInfo);
    }

    public void deleteDownInfo(String  liveId){
        dbHelper.deleteDownInfoByLiveId(liveId);

    }

    @Override
    public void delete(DownloadThreadInfo download) {
        downloadDBController.delete(download);

    }



    public DownInfo convertDownInfo(DownloadInfo downloadInf) {
        if (downloadInf == null) {
            return null;
        }
        List<DownInfo> downInfos = dbHelper.queryDownInfoBeanByLiveIdNow(downloadInf.getId());
        if(downInfos!=null&&downInfos.size()>0){
            return downInfos.get(0);

        }
        return  null ;
    }



}
