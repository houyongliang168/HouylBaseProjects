package com.base.hyl.houylbaseprojects.rukou;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.base.common.base.CoreBaseFragment;
import com.base.hyl.houylbaseprojects.Main2Activity;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.aidl.BookManagerActivity;
import com.base.hyl.houylbaseprojects.rukou.bean.AgentBean;
import com.base.hyl.houylbaseprojects.rukou.contract.IAgentContract;
import com.base.hyl.houylbaseprojects.rukou.present.AgentPresenter;

import com.base.hyl.houylbaseprojects.xiazai.activity.LiveCacheActivity;
import com.base.widget.recycler.RecyclerViewClickListener;


import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 我的代办
 * Created by houyl on 2018/03/07.
 */

public class MeEntryFragment extends CoreBaseFragment<AgentPresenter> implements IAgentContract.IAgentView {



    private RecyclerView recycl_agent;
    private AgentDetailRecyAdpter recy_adpter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_me_agent;
    }

    @Override
    public void loadData() {
        mPresenter.initData();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        recycl_agent = (RecyclerView) view.findViewById(R.id.recycl_agent);


    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setAdapter(final List<AgentBean> list) {
        if (list == null) {
            return;
        }
        if (recy_adpter == null) {
            recycl_agent.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycl_agent.addItemDecoration(new DividerItemDecoration(getActivity(),
                    R.drawable.shape_agent, DividerItemDecoration.VERTICAL_LIST));
        }

        recy_adpter = new AgentDetailRecyAdpter(getContext(), list);
        recycl_agent.setAdapter(recy_adpter);
        recycl_agent.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), recycl_agent, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positions) {
                /*设置 跳转*/
                if (list == null) {
                    return;
                }
                switch (list.get(positions).getKey()) {
                    case "10001":/*10001 代办保全*/
                        Intent intent_one = new Intent(getContext(), BookManagerActivity.class);
                        intent_one.putExtra("TAG", "1");//设置标识 展示不同 的抬头
                        startActivity(intent_one);

                        break;
                    case "10002":/*10002 测试通知*/
                        sendNotification();
                        break;
                    case "10003":/*10003 测试通知*/
                       initNotification();
//                        String aGoto="113232&22121";
//                        String[] aGotos= aGoto.split("&");
//                        if(aGotos.length>1){
//                            Log.e(aGotos[0],aGotos[1]+"");
//                        }

                        break;
                    case "10004":/*10004 测试下载*/

                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

//                        Intent intent = new Intent(getActivity(), LiveCacheActivity.class);
//                        getActivity().startActivity(intent);
                        break;
                    default:
                        break;

                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

    }


    @SuppressLint("WrongConstant")
    private void initNotification() {
        //1.获取系统通知的管理者
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        //2.初始化一个notification的对象
        Notification.Builder mBuilder = new Notification.Builder(getActivity());
        //android 8.0 适配     需要配置 通知渠道NotificationChannel
        NotificationChannel b;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            b = new NotificationChannel("1", "乱七八糟的其他信息", NotificationManager.IMPORTANCE_MIN);

            mNotificationManager.createNotificationChannel(b);
            mBuilder.setChannelId("1");
        }

        Intent intent3 = new Intent();
//        intent.setClass(this.getActivity(),PlayMusicReceiver.class) ;
        //设置广播

        PendingIntent p3 = PendingIntent.getBroadcast(getActivity(), 0, intent3, PendingIntent.FLAG_CANCEL_CURRENT);

        //添加自定义视图  activity_notification
        RemoteViews mRemoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.item_recy_agent1);
        //主要设置setContent参数  其他参数 按需设置
        mBuilder.setContent(mRemoteViews);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("hyl zai 测试中");
        mBuilder.setOngoing(true);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(p3);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        Notification build = mBuilder.build();
        build.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent();
//        intent.setClass(this.getActivity(),PlayMusicReceiver.class) ;
        //设置广播
        intent.setAction("android.service.notification.playandpause");
        //为控件绑定事件

        mRemoteViews.setOnClickPendingIntent(R.id.tv_agent_title, PendingIntent.getBroadcast(getActivity(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        Intent intent1 = new Intent(getActivity(),Main2Activity.class);

        //为控件绑定事件
        mRemoteViews.setOnClickPendingIntent(R.id.iv_agent_goto, PendingIntent.getActivity(this.getActivity(), 2, intent1, PendingIntent.FLAG_UPDATE_CURRENT));

        //更新Notification
        mNotificationManager.notify(1,build);

    }

    private void sendNotification() {

        //获取PendingIntent
        Intent mainIntent = new Intent(getActivity(), Main2Activity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(getActivity(), 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("最简单的Notification")
                //设置通知内容
                .setContentText("只有小图标、标题、内容")
                .setContentIntent(mainPendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());



    }

}