package com.base.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.base.common.log.MyToast;
import com.base.hyl.houbasemodule.R;
import com.base.widget.dialog.MyDialog;


/**
 * 基类
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter> extends BaseActivity implements ICoreBaseControl.ICoreBaseView {

    protected String TAG;
    public P mPresenter;
    protected Context mContext;
    private boolean isOpen = false;
    private MyDialog myDialog;
    private DialogListener listener;
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
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String msg) {
        MyToast.showShort(msg);
    }
    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void dissmissDialog() {
        dismissProgressDialog();
    }
    @Override
    public void showErrorDialog(String msg) {
        if (myDialog == null) {
            myDialog = new MyDialog(this);
            return;
        }
        myDialog.showDialog(R.layout.basemodule_dialog_verify);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_warn_msg);
        tv_msg.setText(msg);
        myDialog.findViewById(R.id.tv_warn_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    @Override
    public void showDialogYesOrNo(String msg, final boolean isFinish, final DialogListener listener) {
        this.listener=listener;
        if (myDialog == null) {
            myDialog = new MyDialog(this);
        }
        myDialog.showDialog(R.layout.basemodule_dialog_sure_and_cancel_type_one);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_content);
        tv_msg.setText(msg);
        myDialog.findViewById(R.id.dialog_exit_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFinish) {
                    finish();
                }
                if(listener!=null){
                    listener.onClickListenerYes();
                }
                myDialog.dismiss();
            }
        });
        myDialog.findViewById(R.id.dialog_exit_false).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClickListenerNo();
                }
                myDialog.dismiss();
            }
        });
    }

}
