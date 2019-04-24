package com.base.hyl.houylbaseprojects.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 参考:
 * https://www.cnblogs.com/smyhvae/p/4070518.html
 * 与
 * http://www.cnblogs.com/punkisnotdead/p/5158016.html
 * 
 */
public class BookManagerService extends Service {
    private static final String TAG="BMS";
    /**
     * CopyOnWriteArrayList 支持并发的读写
     * ConcurrentHashMap
     */
    private CopyOnWriteArrayList<Book> bookList=new CopyOnWriteArrayList<Book>();
    public BookManagerService() {
    }
    

    private final IBookManager.Stub mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1,"Android"));
        bookList.add(new Book(2,"Ios"));
        
    }

    private IBinder.DeathRecipient mDeathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mBinder==null){
                return;
            }
            mBinder.asBinder().unlinkToDeath(mDeathRecipient,0);
          
        }
    };
}
