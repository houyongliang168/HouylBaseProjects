package com.base.hyl.houylbaseprojects.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.base.common.log.MyLog;
import com.base.hyl.houylbaseprojects.Constant;
import com.base.hyl.houylbaseprojects.R;

public class MessengerActivity extends AppCompatActivity {
    
    private static final String TAG="MessengerActivity";
    private Messenger mService;
    
    /*activity 与 Service 进行绑定  bindservice 返回 非空的bind 对象 调用*/
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*链接到服务*/
            mService=new Messenger(service);// 发送的消息
            Message msg=Message.obtain(null,Constant.MSG_FROM_CLIENT);    
            Bundle data=new Bundle();
            data.putString("msg","hello,this is client..");
            msg.setData(data);
    //=============================================================================================
            /*关键回复字段获取*/
            msg.replyTo=mGetReplyMessenger;
    //=============================================================================================
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    /* 客户端接收服务端的消息*/
    
    private Messenger mGetReplyMessenger=new Messenger(new MessengerHandler());
    /*创建一个 handler 对象*/
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.MSG_FROM_CLIENT:
                    /* 服务端接收消息后 进行回复对应的客户端*/
                    MyLog.wtf(TAG,"receice msg from Service :"+msg.getData());
//                    msg.getData(). getString("reply");//获取对应的消息

                    MyLog.wtf(TAG,"receice msg from Service  reply :"+  msg.getData(). getString("reply"));


                    break;
                default:
                    super.handleMessage(msg);

            }




        }

     
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent=new Intent(this,MessengerService.class);
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
