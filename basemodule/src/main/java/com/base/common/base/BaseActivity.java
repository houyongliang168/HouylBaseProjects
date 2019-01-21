package com.base.common.base;


import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.base.common.activitystack.ActivityStack;
import com.base.widget.dialog.MyDialog;
import com.base.hyl.houbasemodule.R;


/**
 * Created by zcc on 2017/4/28.
 */
public class BaseActivity extends FragmentActivity {
    private Dialog dialog;
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加activity到activity栈
        ActivityStack.getInstance().addActivity(this);
       // EventBus.getDefault().register(this);
        createView();
        myDialog = new MyDialog(this);
    }



    /**
     * 不依赖系统字体大小变化
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 无网络连接时弹出
     */
   /* NetErrorPopupWindow netErrorPopupWindow;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void popupNetWorkWindow(NetWorkChange state) {
        if (netErrorPopupWindow != null && netErrorPopupWindow.isShowing()) {
            netErrorPopupWindow.dismiss();
            netErrorPopupWindow = null;
        }
        if (!state.isConnected()) {
            netErrorPopupWindow = new NetErrorPopupWindow(this);
            netErrorPopupWindow.showAtDropDownRight(tvLoginTitle);
        }
    }*/
    public void dismissProgressDialog() {
        if (dialog != null && !isFinishing()) {
            dialog.dismiss();
        }
    }

    private void createView() {
        View layout = View.inflate(this, R.layout.basemodule_dialog_loading_bg, null);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }

    private void createView2() {
        View layout = View.inflate(this, R.layout.basemodule_dialog_loading_bg2, null);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }

    public void showProgressDialog() {
        if (dialog != null && !isFinishing()) {
            dialog.show();
        }
    }

    public void showProgressDialog(boolean cancelable) {
        if (dialog != null && !isFinishing()) {
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void finish() {
        super.finish();
        //移除activity
        ActivityStack.getInstance().finishActivity(this);
    }


}
