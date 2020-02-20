package com.base.hyl.houylbaseprojects.download.contract;


import com.base.common.base.CoreBasePresenter;
import com.base.common.base.ICoreBaseControl;

/**
 * 详情页面接口
 * Created by houyongliang on 2017/11/2.
 */

public interface IDownFinishedFragmentContract {
    interface IDownFinishedFragmentView extends ICoreBaseControl.ICoreBaseView {

        /*设置适配器*/
        void  showDeleteDialog();

    }

    interface IDownFinishedFragmentViewModel extends ICoreBaseControl.ICoreBaseModel {


    }

    abstract class IDownFinishedFragmentPresenter extends CoreBasePresenter<IDownFinishedFragmentViewModel, IDownFinishedFragmentView> {
        /*网络请求具体数据*/
        abstract void httpRequestData(String vodType, int page, int size);
    }
}
