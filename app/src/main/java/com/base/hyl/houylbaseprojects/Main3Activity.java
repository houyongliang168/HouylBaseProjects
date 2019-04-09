package com.base.hyl.houylbaseprojects;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.base.common.log.MyToast;
import com.orhanobut.logger.Logger;


public class Main3Activity extends AppCompatActivity implements Test1Fragment.OnFragmentInteractionListener {
    private static String TAG = Main3Activity.class.getSimpleName();
    
    
    private Test1Fragment fg1;
    private FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fl = (FrameLayout) findViewById(R.id.fl);
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
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                fg1=  new Test1Fragment();
                startFragment(fg1);
            }
        }, 2000);
//        fg1=  new Test1Fragment();
//        startFragment(fg1);
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                f1=  new Fragment();
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, 2000);

        // 通过静态方法创建ScheduledExecutorService的实例
//         ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(4);

        // 延时任务
//        mScheduledExecutorService.schedule(threadFactory.newThread(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("lzp", "first task");
//            }
//        }), 1, TimeUnit.SECONDS);


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

   public void  startFragment(Test1Fragment t1){
       android.support.v4.app.FragmentManager supportFragmentManager = getSupportFragmentManager();
       android.support.v4.app.FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
       fragmentTransaction.replace(R.id.fl, t1);
       fragmentTransaction.commit();
        
   }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
