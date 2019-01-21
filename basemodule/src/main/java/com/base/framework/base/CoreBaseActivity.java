package com.base.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * 基类
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter> extends BBaseActivity implements ICoreBaseControl.ICoreBaseView {

    protected String TAG;
    public P mPresenter;
    protected Context mContext;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        mContext = this;
        super.setContentView(this.getLayoutId());
        mPresenter = TUtil.getT(this, 0);
        if (this instanceof ICoreBaseControl.ICoreBaseView) mPresenter.attachV(this);
        this.initView(savedInstanceState);

    }

    public abstract void initView(Bundle savedInstanceState);
     public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }


}
