package com.base.common.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.base.hyl.houbasemodule.R;


/**
 * Created by zhaoyu on 2017/5/4.
 */

public class MyDialog extends Dialog {
    public MyDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, R.style.dialog);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

      public void showDialog(int resid , boolean cancelable) {
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);

        this.show();
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(R.mipmap.basemodule_transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.height = (int) (d.getHeight() * 0.24); // 高度设置为屏幕的0.2
        lp.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        setContentView(resid);

    }

    public void showDialog(int resid) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        this.show();
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(R.mipmap.basemodule_transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.height = (int) (d.getHeight() * 0.24); // 高度设置为屏幕的0.2
        lp.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        setContentView(resid);

    }
    public void showDialog(int resid,double height,double width) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        this.show();
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(R.mipmap.basemodule_transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        if (height==0) {
            lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
            lp.width = (int) (d.getWidth() * width);
        }else{
            lp.height = (int) (d.getHeight() * height); // 高度设置为屏幕的0.2
            lp.width = (int) (d.getWidth() * width); // 宽度设置为屏幕的0.6
        }
        dialogWindow.setAttributes(lp);
        setContentView(resid);

    }
}
