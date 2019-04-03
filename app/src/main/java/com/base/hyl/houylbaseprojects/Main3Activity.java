package com.base.hyl.houylbaseprojects;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.common.log.MyToast;
import com.orhanobut.logger.Logger;

public class Main3Activity extends AppCompatActivity {
    private static String TAG = Main3Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void test1(View view) {
        MyToast.showShort("弹框了");
        startActivity(new Intent(this, Main2Activity.class));

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

    @Override
    protected void onPause() {
        super.onPause();
        Logger.e(TAG + "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.e(TAG + "onRestart");
    }
}
