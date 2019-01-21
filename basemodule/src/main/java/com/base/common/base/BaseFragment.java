package com.base.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.hyl.houbasemodule.R;


/**
 * Created by zcc on 2017/5/3.
 */

public class BaseFragment extends Fragment {
    private Dialog dialog;
    public Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createProgressDialog();
//        EventBus.getDefault().register(this);
        //注册
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * 显示progressDialog
     */
    public boolean showProgressDialog() {
        if (dialog != null && !activity.isFinishing()) {
            dialog.show();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 关闭progressDialog
     */
    public void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 关闭dialog后，可以使用
     */
    public void nullProgressDialog() {
        dialog = null;
    }

    public void createProgressDialog() {
        View layout = View.inflate(activity, R.layout.basemodule_dialog_loading_bg, null);
        dialog = new Dialog(activity, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }

    private void createProgressDialog2() {
        View layout = View.inflate(activity, R.layout.basemodule_dialog_loading_bg2, null);
        dialog = new Dialog(activity, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }

    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);  //注册
        super.onDestroy();
        dismissProgressDialog();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = this.getActivity();
    }
}
