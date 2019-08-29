package com.base.hyl.houylbaseprojects.download.contract;


import com.base.common.base.CoreBasePresenter;
import com.base.common.base.ICoreBaseControl;

/**
 * 详情页面接口
 * Created by houyongliang on 2017/11/2.
 */

public interface IDownLoadContract {
    interface IDownLoadView extends ICoreBaseControl.ICoreBaseView {



    }

    interface IDownLoadModel extends ICoreBaseControl.ICoreBaseModel {


    }

    abstract class IDownLoadPresenter extends CoreBasePresenter<IDownLoadModel, IDownLoadView> {
        public abstract void httpRequestData(String liveId);
    }
}
