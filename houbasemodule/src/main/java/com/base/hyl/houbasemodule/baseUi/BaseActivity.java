package com.base.hyl.houbasemodule.baseUi;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taikanglife.isalessystem.common.bean.LogOut;
import com.taikanglife.isalessystem.common.service.MyDownloadService;
import com.taikanglife.isalessystem.common.bean.NetWorkStateChange;
import com.taikanglife.isalessystem.common.utils.GaodeUtils;
import com.taikanglife.isalessystem.common.utils.MyDialog;
import com.taikanglife.isalessystem.common.utils.MyLog;
import com.taikanglife.isalessystem.common.utils.MyToast;
import com.taikanglife.isalessystem.common.utils.SPUtils;
import com.tendcloud.tenddata.TCAgent;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.HttpDownManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.DbDwonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by zcc on 2017/4/28.
 */
public class BaseActivity extends FragmentActivity {

    private Dialog dialog;

    private MyDialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        app.addActivity(this);

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


    public void dismissProgressDialog() {
        if (dialog != null && !isFinishing()) {
            dialog.dismiss();
        }
    }

    private void createView() {
        View layout = View.inflate(this, R.layout.dialog_loading_bg, null);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }

    private void createView2() {
        View layout = View.inflate(this, R.layout.dialog_loading_bg2, null);
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
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();

    }


}
