package com.base.hyl.houbasemodule.base;

import android.content.Context;

/**
 * Created by houyongliang on 2017/9/11.
 */

public interface ICoreBaseControl {
    public interface ICoreBaseView {
        Context getContext();

        void showError(String msg);
    }

    public interface ICoreBaseModel {
    }

    interface ICoreBasePresenter<V extends ICoreBaseView> {
        public void detachVM();

        public void attachV(V v);
    }
}