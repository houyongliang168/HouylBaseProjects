package com.base.common.base;


/**
 * Created by hpw on 16/10/28.
 */

public abstract class CoreBasePresenter<M extends ICoreBaseControl.ICoreBaseModel, V extends ICoreBaseControl.ICoreBaseView> implements ICoreBaseControl.ICoreBasePresenter<V> {
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
