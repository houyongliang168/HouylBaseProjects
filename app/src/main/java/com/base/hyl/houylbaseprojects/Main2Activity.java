package com.base.hyl.houylbaseprojects;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.base.common.log.MyToast;
import com.orhanobut.logger.Logger;

public class Main2Activity extends Activity {
    private static String TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e(TAG + "onDestroy");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.e(TAG + "onResume");
    }
}
