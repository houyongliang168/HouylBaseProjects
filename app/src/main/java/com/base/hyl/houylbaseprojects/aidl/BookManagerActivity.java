package com.base.hyl.houylbaseprojects.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.common.log.MyLog;
import com.base.common.utils.ProcessUtils;
import com.base.hyl.houylbaseprojects.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";
    private static final int  MESSAGE_NEW_BOOK_ARRIVED = 1;
    private  IBookManager mRemoteBookManager;/*单独拿到引用地址 一个地址多个指向有何意义？*/
    
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    MyLog.wtf(TAG,"receive new book :"+msg.obj);
                    break;
                    default:
                        super.handleMessage(msg);
                
            }
            
            
        }
    };
    
    
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*获取 进程交互的对象*/
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager=bookManager;
                /* 通过aidl  IBookManager 对象 具体的数据*/
                List<Book> list=bookManager.getBookList();
                MyLog.wtf(TAG,"query book list ,list type :"+list.getClass().getCanonicalName());
                MyLog.wtf(TAG,"query book list :"+list.toString());
                /*客户端添加新书*/
                Book newBook=new Book(3,"这位是本新书-");
                bookManager.addBook(newBook);
                MyLog.wtf(TAG,"add book  :"+newBook.toString());

                List<Book> newlist=   bookManager.getBookList();
                MyLog.wtf(TAG,"query book new  list:"+newlist.toString());
                
                bookManager.registerListener(mOnNewBookArrivedListerner);
                
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            /* 进程ID*/
            MyLog.wtf(TAG," android.os.Process.myPid() :"+ android.os.Process.myPid() +"___"+ProcessUtils.getProcessName(getApplicationContext()));
            /*线程名称*/
            MyLog.wtf(TAG," Thread.currentThread().getName() :"+ Thread.currentThread().getName());
            mRemoteBookManager=null;
            if(!isFinishing()){//重新开启服务
                Intent intent=new Intent(BookManagerActivity.this,BookManagerService.class);
                bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(mRemoteBookManager!=null&&mRemoteBookManager.asBinder().isBinderAlive()){//bind 如果存活
                            try {
                                mRemoteBookManager.getBookList();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } 
                    }
                }).start();

              
                
            }
        });
        
        Intent intent=new Intent(BookManagerActivity.this,BookManagerService.class);
//        startService(intent);
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if(mRemoteBookManager!=null&&mRemoteBookManager.asBinder().isBinderAlive()){//bind 如果存活
            try {
                MyLog.wtf(TAG,"unregister listener :"+mOnNewBookArrivedListerner);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListerner);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        
        
        
        unbindService(  mConnection  );
        super.onDestroy();
      
    }

    /**
     * 获取到 book 的监听
     */
    private IOnNewBookArrivedListerner mOnNewBookArrivedListerner=new IOnNewBookArrivedListerner.Stub(){

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            /*两种方法等效*/
            /*方法1*/
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,book).sendToTarget();
            /*方法2*/
//            Message msg=     Message.obtain();
//            msg.what=MESSAGE_NEW_BOOK_ARRIVED;
//            msg.obj=book;
//            mHandler.sendMessage(msg);
          
        }
    };
    
    
    
}
