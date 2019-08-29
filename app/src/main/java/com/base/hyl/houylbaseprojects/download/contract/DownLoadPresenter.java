package com.base.hyl.houylbaseprojects.download.contract;

/**
 * Created by houyongliang on 2018/2/2.
 */

public class DownLoadPresenter extends IDownLoadContract.IDownLoadPresenter {

    public DownLoadPresenter() {
        this.mModel = new DownLoadModel();/*获取 m 层对象*/
    }

    @Override
    public void onStart() {

    }




    @Override
    public void httpRequestData(String liveId) {

    }
}
