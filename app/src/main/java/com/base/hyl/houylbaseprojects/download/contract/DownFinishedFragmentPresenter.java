package com.base.hyl.houylbaseprojects.download.contract;

/**
 * Created by houyongliang on 2018/2/2.
 */

public class DownFinishedFragmentPresenter extends IDownFinishedFragmentContract.IDownFinishedFragmentPresenter {

    public DownFinishedFragmentPresenter() {
        this.mModel = new DownFinishedFragmentModel();/*获取 m 层对象*/
    }

    @Override
    public void onStart() {

    }




    @Override
    void httpRequestData(String vodType, int page, int size) {

    }
}
