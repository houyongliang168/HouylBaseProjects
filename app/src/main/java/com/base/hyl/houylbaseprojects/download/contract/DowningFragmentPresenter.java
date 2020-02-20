package com.base.hyl.houylbaseprojects.download.contract;


/**
 * Created by houyongliang on 2018/2/2.
 */

public class DowningFragmentPresenter extends IDowningFragmentContract.IDowningFragmentPresenter {

       public DowningFragmentPresenter() {
        this.mModel = new DowningFragmentModel();/*获取 m 层对象*/
    }

    @Override
    public void onStart() {

    }


    @Override
    void httpRequestData(int page, int size) {

    }
}
