package com.base.hyl.houylbaseprojects.databing.msgBase;

import android.content.Intent;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.databing.msgBase.adpter.MsgBaseAdapter;
import com.base.hyl.houylbaseprojects.databing.msgBase.base.Core_BaseActivity;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.MsgBaseInfoBean;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.ReflashTypes;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.RequestBaseBean;
import com.base.hyl.houylbaseprojects.databing.msgBase.observable.DynamicChangeCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import static com.base.hyl.houylbaseprojects.databing.msgBase.bean.ReflashTypes.LOAD_MORE;
import static com.base.hyl.houylbaseprojects.databing.msgBase.bean.ReflashTypes.REFLASH;


public class MsgBaseActivity extends Core_BaseActivity<MsgBasePresenter> implements IMsgBaseContract.IMsgBaseView<List<MsgBaseInfoBean>> , View.OnClickListener{


    private ImageView iv_back;
    private TextView tv_title;
    private FrameLayout fl_container;
    private SmartRefreshLayout srl_msg;
    private RecyclerView recy_msg;
    private RelativeLayout noneDataView;
    private ReflashTypes reflashTypes;//刷新状态 reflash  与  loadMore
    private int page = 1;
    private int pageNum=10;/*可用pageNum 页面 其与page 比较获取是否包含更多*/
    private MsgBaseAdapter adapter;
    private int type;//获取类型
    private int isDelet=0;//获取是否长按删除 默认为0 为删除  1 为不删除
    private String title="";//获取是否长按删除 默认为0 为删除  1 为不删除

    private ObservableArrayList<MsgBaseInfoBean> allDatas=new ObservableArrayList<>();



    @Override
    public void initView(Bundle savedInstanceState) {

        getIntentData();
        reflashTypes = new ReflashTypes();
        iv_back = findViewById(R.id.iv_back);/* 返回键*/
        tv_title = findViewById(R.id.tv_title);/*标题*/
        fl_container = findViewById(R.id.fl_container);    /*有数据界面*/
        srl_msg = findViewById(R.id.smart_refresh_layout_msg);     /*刷新控件*/
        recy_msg = findViewById(R.id.recy_msg);  /*列表控件*/
        /*设置刷新*/
        srl_msg.setEnableRefresh(true);//是否启用下拉刷新功能
        srl_msg.setEnableLoadMore(true);//是否启用上拉加载功能
        srl_msg.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                reflashTypes.setCurrentStatus(LOAD_MORE);
                page++;
                mPresenter.httpRequest(type+"",page+"",10+"");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                reflashTypes.setCurrentStatus(REFLASH);
                page = 1;
                mPresenter.httpRequest(type+"",page+"",10+"");
            }
        });


        /* 动态加载 无数据页面*/
        LayoutInflater inflater = LayoutInflater.from(MsgBaseActivity.this);
        noneDataView = (RelativeLayout) inflater.inflate(R.layout.item_msg_nodata,null);
        /* 设置点击事件*/
        iv_back.setOnClickListener(this);
        noneDataView.findViewById(R.id.rl_msg).setClickable(true);
        noneDataView.findViewById(R.id.rl_msg).setOnClickListener(this);
        tv_title.setText(title+"");/* 标题赋值*/

        recy_msg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter=new MsgBaseAdapter(MsgBaseActivity.this,allDatas);
        allDatas.addOnListChangedCallback(new DynamicChangeCallback(adapter));
        adapter.setListener(new MsgBaseAdapter.OnClickListener() {
            @Override
            public void OnClickListener(View v, int possion) {//点击事件
                if(  allDatas.size()>possion &&allDatas.get(possion)!=null){
                    OnClickEvent.onClick(MsgBaseActivity.this,type,allDatas.get(possion),possion);
                }

            }

            @Override
            public boolean OnLongClickListener(View v, final int possion) {//长按事件 统一的接口删除
                if(allDatas!=null&&allDatas.size()>possion&&allDatas.get(possion)!=null){
                    if(isDelet==0){
                        showDialogYesOrNo("您确定要删除吗?", false, new DialogListener() {
                            @Override
                            public void onClickListenerYes() {
                                mPresenter.deleteMsg(allDatas.get(possion).getId()+"",type+"", "stfnum",possion);
                            }
                            @Override
                            public void onClickListenerNo() {

                            }
                        });
                    }else if(isDelet==1){
                        OnClickEvent.onLongClick(MsgBaseActivity.this,type,allDatas.get(possion),possion);
                    }
                    return true;
                }
                return false;
            }
        });
        recy_msg.setAdapter(adapter);
        if(srl_msg!=null){
            srl_msg.autoRefresh();
        }
    }


    private void getIntentData() {
        Intent intent=getIntent();
        type= intent.getIntExtra("TYPE",0);
        isDelet= intent.getIntExtra("DELETE",0);
        title= intent.getStringExtra("TITLE");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_base;
    }

    @Override
    public void setReflashStatus(boolean isReflash) {
        if (reflashTypes == null) {
            return;
        }
        @ReflashTypes.ReflashStatus int reflashStatus = reflashTypes.getCurrentStatus();
        switch (reflashStatus) {
            case REFLASH:
                srl_msg.finishRefresh(isReflash);
                break;
            case LOAD_MORE:
                srl_msg.finishLoadMore(isReflash);
                break;
            default:
                break;

        }
    }

    @Override
    public void removeDatas(int possion) {
        if(allDatas.size()> possion&&possion>=0 &&adapter!=null){
            allDatas.remove(possion);
            adapter.notifyDataSetChanged();//刷新数据
        }
    }

    /*展示数据*/
    public void setAdapter(List<MsgBaseInfoBean> list) {
        if (allDatas == null || reflashTypes == null||list==null) {
            return;
        }

            boolean isMore = page >= pageNum ? false : true;
            if (isMore) {
                srl_msg.setNoMoreData(false);
            } else {
                srl_msg.setNoMoreData(true);
            }

            @ReflashTypes.ReflashStatus int reflashStatus = reflashTypes.getCurrentStatus();
            switch (reflashStatus) {
                case REFLASH:
                    allDatas.clear();
                    allDatas.addAll(list);
                    break;
                case LOAD_MORE:
                    allDatas.addAll(list);
                    break;
                default:
                    break;
            }
            if (allDatas == null || allDatas.size() == 0) {
                fl_container.addView(noneDataView);
            } else {
                fl_container.removeView(noneDataView);
//                if(adapter==null){
//                    recy_msg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//                    adapter=new MsgBaseAdapter(MsgBaseActivity.this,allDatas);
//                    allDatas.addOnListChangedCallback(new DynamicChangeCallback(adapter));
//                    adapter.setListener(new MsgBaseAdapter.OnClickListener() {
//                        @Override
//                        public void OnClickListener(View v, int possion) {//点击事件
//                            if(  allDatas.size()>possion &&allDatas.get(possion)!=null){
//                                OnClickEvent.onClick(MsgBaseActivity.this,type,allDatas.get(possion),possion);
//                            }
//
//                        }
//
//                        @Override
//                        public boolean OnLongClickListener(View v, final int possion) {//长按事件 统一的接口删除
//                            if(allDatas!=null&&allDatas.size()>possion&&allDatas.get(possion)!=null){
//                                if(isDelet==0){
//                                  showDialogYesOrNo("您确定要删除吗?", false, new DialogListener() {
//                                        @Override
//                                        public void onClickListenerYes() {
//                                            mPresenter.deleteMsg(allDatas.get(possion).getId()+"",type+"", App.staffBuidler,possion);
//                                        }
//                                        @Override
//                                        public void onClickListenerNo() {
//
//                                        }
//                                    });
//                                }else if(isDelet==1){
//                                    OnClickEvent.onLongClick(MsgBaseActivity.this,type,allDatas.get(possion),possion);
//                                }
//                                return true;
//                            }
//                            return false;
//                        }
//                    });
//                    recy_msg.setAdapter(adapter);
//                }else{
//                    adapter.notifyDataSetChanged();
//                }
            }


}



    @Override
    public void displayData(RequestBaseBean<List<MsgBaseInfoBean>> info) {
        if(info==null){
            return;
        }
        /* 获取 最多加载的页面信息*/
        try {
            pageNum = Integer.parseInt(info.getPageNums());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        setAdapter(info.getInfo());
    }


    @Override
    public void onStart() {
        super.onStart();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_msg:
                if(srl_msg!=null){
                    srl_msg.autoRefresh();
                }
                break;
            default:
                break;
        }

    }



}
