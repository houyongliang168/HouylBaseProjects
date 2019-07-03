package com.base.hyl.houylbaseprojects.databing.msgBase;


import com.base.hyl.houylbaseprojects.databing.msgBase.base.Core_BasePresenter;
import com.base.hyl.houylbaseprojects.databing.msgBase.base.ICore_BaseControl;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.RequestBaseBean;

/**
 * Created by houyongliang on 2018/3/7.
 */

public interface IMsgBaseContract {
    interface IMsgBaseView <T>extends ICore_BaseControl.ICoreBaseView {

       /* 获取数据*/
        void displayData(RequestBaseBean<T> info);
        /* 刷新状态*/
        void setReflashStatus(boolean isReflash);
        /* 移除数据*/
        void removeDatas(int possion);

    }

    interface IMsgBaseModel extends ICore_BaseControl.ICoreBaseModel {

    }

    abstract class AMsgBasePresenter extends Core_BasePresenter<IMsgBaseModel, IMsgBaseView> {

        public abstract void httpRequest(String type, String page, String num);  /*初始化数据*/

        public abstract void  deleteMsg(String msgIds, String type, String staffNum, int possion);  /*删除数据*/
    }
}
