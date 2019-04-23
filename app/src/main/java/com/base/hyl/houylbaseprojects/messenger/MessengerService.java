package com.base.hyl.houylbaseprojects.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.base.common.log.MyLog;
import com.base.hyl.houylbaseprojects.Constant;


public class MessengerService extends Service {
    private static final String TAG="MessengerService";
    
    public MessengerService() {
    }
    /*创建一个 handler 对象*/
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                
                case Constant.MSG_FROM_CLIENT:
                    /* 服务端接收消息后 进行回复对应的客户端*/
                    MyLog.wtf(TAG,"receice msg from Client :"+msg.getData().getString("msg"));
                    
                    Messenger client=msg.replyTo;//获取客户端的对象
                    Message replyMessgae=Message.obtain(null,Constant.MSG_FROM_CLIENT);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","嗯，你的消息已经收到，稍后回复你。");
                    replyMessgae.setData(bundle);

                    try {
                        client.send(replyMessgae);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                    break;
                    default:
                        super.handleMessage(msg); 

            }
            
            
            
        
        }
    }
    /*Messenger 的对象 构造方法有  Handler 与 IBind 对象*/
    private final Messenger mMessenger=new Messenger(new MessengerHandler());
  
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
      return mMessenger.getBinder();
    }
    
}
