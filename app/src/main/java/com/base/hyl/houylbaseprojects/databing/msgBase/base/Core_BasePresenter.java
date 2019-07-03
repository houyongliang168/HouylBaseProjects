package com.base.hyl.houylbaseprojects.databing.msgBase.base;


/**
 * Created by hpw on 16/10/28.
 */

public abstract class Core_BasePresenter<M extends ICore_BaseControl.ICoreBaseModel, V extends ICore_BaseControl.ICoreBaseView> implements ICore_BaseControl.ICoreBasePresenter<V> {
    public M mModel;
    public V mView;

    @Override
    public void attachV(V v) {
        this.mView = v;
        this.onStart();
    }

    @Override
    public void detachVM() {
        mView = null;
        mModel = null;
    }

    public abstract void onStart();
}
