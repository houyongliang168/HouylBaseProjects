package com.base.hyl.houylbaseprojects.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;

import com.base.common.log.MyLog;
import com.base.common.utils.ProcessUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 参考:
 * https://www.cnblogs.com/smyhvae/p/4070518.html
 * 与
 * http://www.cnblogs.com/punkisnotdead/p/5158016.html
 * 
 * 
 * 权限验证待处理
 */
public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    /**
     * AtomicBoolean 线程安全的
     * 比较是否 改变    mIsServiceDestoryed.compareAndSet(false,true)  期望是false 则变为 true
     * <p>
     * 首先我们要知道compareAndSet的作用，判断对象当时内部值是否为第一个参数，如果是则更新为第二个参数，且返回ture，否则返回false。那么默认初始化为false，则一个线程把他变为ture，compareAndSet返回ture，进入方法体执行逻辑，那么其他的任何线程进入该方法执行compareAndSet时第一个参数为false，而对象的内部值已经被修改为true，则永远过不了if。
     * 完事后 重新进行设置
     * if (exists.compareAndSet(false, true)) {
     * <p>
     * exists.set(false);
     * } else {
     * System.out.println(name + " give up");
     * }
     */
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private AtomicBoolean mIsBookAdded = new AtomicBoolean(false);

    /**
     * CopyOnWriteArrayList 支持并发的读写
     * ConcurrentHashMap
     */
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListerner> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListerner>();
    /* CopyOnWriteArrayList 监听 Listener 有问题, 使用 RemoteCallbackList 代替*/
//    private CopyOnWriteArrayList<IOnNewBookArrivedListerner> mListenerList=new CopyOnWriteArrayList<IOnNewBookArrivedListerner>();


    public BookManagerService() {
    }

    /* 通过 aidl 中的代理stub 获取 AIDL 接口的对象*/
    private  Binder mBinder = new IBookManager.Stub() {
        /* 客户端 可以 进行的操作  获取集合 添加  注册 取消注册*/
        @Override
        public List<Book> getBookList() throws RemoteException {
//            SystemClock.sleep(5000);
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListerner listener) throws RemoteException {
            mListenerList.register(listener);
            /* 之前 销毁有问题的代码  采用 Remote..List 代替*/
//            if(!mListenerList.contains(listener)){
//                mListenerList.add(listener); 
//            }else{
//                MyLog.wtf(TAG,"already exists.");
//                
//            }
//            MyLog.wtf(TAG,"registerListener ，size :"+mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListerner listener) throws RemoteException {
            mListenerList.unregister(listener);
            MyLog.wtf(TAG,"unregisterListener succesed current size :"+mListenerList.getRegisteredCallbackCount());
            /* 之前 销毁有问题的代码  采用 Remote..List 代替*/
           /* if(mListenerList.contains(listener)){
                mListenerList.remove(listener);
                MyLog.wtf(TAG,"unregisterListener succesed");
            }else{
                MyLog.wtf(TAG,"not found,can not unregister"); 
                *//* 在 BookManagerActivity 调用 unregisterListener 的时候 提示 该错误, 因为 跨进程 回创建不同的对象,所以回找不到*//*
                //使用 RemoteCallBackList 来处理
            }
            MyLog.wtf(TAG,"unregisterListener ，current size :"+mListenerList.size());*/

        }
        /* 可以在里面验证权限  及时阻断binder 传递数据*/
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            int check=checkCallingOrSelfPermission("com.base.hyl.houylbaseprojects.permission");
            MyLog.wtf(TAG,"check :"+check);
            MyLog.wtf(TAG,"PackageManager.PERMISSION_DENIED :"+PackageManager.PERMISSION_DENIED);
//            if(check==PackageManager.PERMISSION_DENIED){/*如果权限未通过 则不处理*/
//                return false;
//            }
//            String packageName=null;
//            String[] packages=getPackageManager().getPackagesForUid(getCallingUid());
//            /* 拿到包名*/
//            if(packages!=null&&packages.length>0){
//                packageName=packages[0];
//            }
//            if(TextUtils.isEmpty(packageName)){
//                return false;
//            }else if(!packageName.startsWith("com.base.hyl")){
//                return false;
//            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
//        /* 增加权限判断*/
//        int check=checkCallingOrSelfPermission("com.base.hyl.houylbaseprojects.permission.ACCESS_BOOK_SERVICE");
//        if(check==PackageManager.PERMISSION_DENIED){/*如果权限未通过 则不处理*/
//            return null;
//        }
//        
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "Android"));
        bookList.add(new Book(2, "Ios"));
        /*开启一个线程进行处理*/
        new Thread(new SerciceWorker()).start();

    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }
    /* binder 意外 死亡的处理*/
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinder == null) {
                return;
            }
            /* 取消链接*/
            mBinder.unlinkToDeath(mDeathRecipient, 0);
/*            android.os.Process.myPid()：获取该进程的ID。
            android.os.Process.myTid()：获取该线程的ID。
            android.os.Process.myUid()：获取该进程的用户ID。*/
            MyLog.wtf(TAG," android.os.Process.myPid() :"+ android.os.Process.myPid() +"___"+ProcessUtils.getProcessName(getApplicationContext()));

            MyLog.wtf(TAG," Thread.currentThread().getName() :"+ Thread.currentThread().getName());   
            mBinder=null;
        }
    };

    /**
     * Remote 远程
     *
     * 预防 耗时操作 开线程进行处理
     * @param book
     * @throws RemoteException
     */
    private void onNewBookArrived(final Book book) throws RemoteException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* 有问题的代码 销毁接受不到 unregisterListener*/
                if(mIsBookAdded.compareAndSet(false,true)){
                    bookList.add(book);
                    final int N = mListenerList.beginBroadcast();
                    MyLog.wtf(TAG, "onNewBookArrived ,notify listeners :" + N);

                    for (int i = 0; i < N; i++) {
                        IOnNewBookArrivedListerner listener = mListenerList.getBroadcastItem(i);//获取监听
                        MyLog.wtf(TAG, "onNewBookArrived, notify listener :" + listener);
                        try {
                            if (listener != null) {
                                listener.onNewBookArrived(book);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    mListenerList.finishBroadcast();/* 每次结束就关闭*/
                    mIsBookAdded.set(false);
                }
              
            }
        }).start();
        
     

        /* 有问题的代码 销毁接受不到 unregisterListener*/
//        bookList.add(book);
//        MyLog.wtf(TAG,"onNewBookArrived ,notify listeners :"+mListenerList.size());
//
//        for (int i = 0; i < mListenerList.size(); i++) {
//            IOnNewBookArrivedListerner listener = mListenerList.get(i);//获取监听
//            MyLog.wtf(TAG,"onNewBookArrived, notify listener :"+listener);
//            listener.onNewBookArrived(book);
//
//        }

    }


    /*开启一个线程接收信息*/
    private class SerciceWorker implements Runnable {
        @Override
        public void run() {

            while (!mIsServiceDestoryed.get()) {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = bookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
