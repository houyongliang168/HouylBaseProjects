package com.base.common.base;

import android.content.Context;

/**
 * Created by houyongliang on 2017/9/11.
 */

public interface ICoreBaseControl {
    public interface ICoreBaseView {
        Context getContext();

        void showError(String msg);
        /* 展示弹框*/
        void showDialog();
        /*关闭弹框*/
        void dissmissDialog();
        /*展示 错误内容*/
        void showErrorDialog(String msg);
        /*展示是否框*/
        void showDialogYesOrNo(String msg, boolean isFinish, DialogListener listener);
    }

    public interface ICoreBaseModel {
    }

    interface ICoreBasePresenter<V extends ICoreBaseView> {
        public void detachVM();

        public void attachV(V v);
    }
}