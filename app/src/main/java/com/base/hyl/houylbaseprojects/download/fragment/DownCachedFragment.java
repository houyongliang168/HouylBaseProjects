package com.base.hyl.houylbaseprojects.download.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.base.common.base.CoreBaseFragment;

import com.base.common.loadmanager.callback.Callback;
import com.base.common.loadmanager.callback.SuccessCallback;
import com.base.common.loadmanager.core.LoadService;
import com.base.common.loadmanager.core.LoadSir;
import com.base.common.loadmanager.owncallback.EmptyCallback;
import com.base.common.loadmanager.owncallback.ErrorCallback;
import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.download.activity.DownCacheActivity;
import com.base.hyl.houylbaseprojects.download.adpter.CachedRVAdapter;
import com.base.hyl.houylbaseprojects.download.contract.DownFinishedFragmentPresenter;
import com.base.hyl.houylbaseprojects.download.contract.IDownFinishedFragmentContract;
import com.base.hyl.houylbaseprojects.download.core.DownloadService;
import com.base.hyl.houylbaseprojects.download.core.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo;

import com.base.widget.dialog.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zcc on 2018/1/15.
 * 视频缓存
 */

public class DownCachedFragment extends CoreBaseFragment<DownFinishedFragmentPresenter> implements IDownFinishedFragmentContract.IDownFinishedFragmentView , DownCacheActivity.EditStatusChangeListener {

    private static final String FRAGMENTEDIT = "edit";

    private LinearLayout ll_downed_cache;
    private RecyclerView recy_cache;
    private View ll_cache, ll_cache_pause, ll_cache_start;
    private View include_cache_delete, tv_cache_edit_delete;
    private ImageView iv_cache_edit_check;
    private CachedRVAdapter mAdapter ;
    private boolean mIsChecked = false;
    private boolean mIsEdit = false;
    /* 数据源 所有数据的源头*/
    private List<DownloadInfo> listData =  new ArrayList<>();

    private DownCacheActivity activity;
    private MyDialog deletDialog;
    private LoadService ownLS;
    private View root;

    public DownCachedFragment(){
    }
    /* 获取Fragment 对象*/
    public static DownCachedFragment getInstance( boolean isEdit) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(FRAGMENTEDIT, isEdit);
        DownCachedFragment fragment = new DownCachedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static DownCachedFragment getInstance() {
        DownCachedFragment fragment = new DownCachedFragment();
        return fragment;
    }



    @Override
    public void loadData() {
        refrashData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = super.onCreateView(inflater, container, savedInstanceState);
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .setDefaultCallback(SuccessCallback.class)
                .build();
        ownLS = loadSir.register(root, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                refrashData();
            }
        });
        return ownLS.getLoadLayout();
    }

    @Override
    public void initUI(View roots, @Nullable Bundle savedInstanceState) {



        recy_cache = root.findViewById(R.id.recy_cache);
        ll_downed_cache = root.findViewById(R.id.ll_downed_cache);
        include_cache_delete = root.findViewById(R.id.include_cache_delete);
        iv_cache_edit_check =  include_cache_delete.findViewById(R.id.iv_cache_edit_check);
        iv_cache_edit_check.setOnClickListener(mOnClickListener);
        setCBCheck(false);
        /* 删除按钮*/
        tv_cache_edit_delete = include_cache_delete.findViewById(R.id.tv_cache_edit_delete);
        tv_cache_edit_delete.setOnClickListener(mOnClickListener);
        /* 设置activity监听*/
        Activity acty = getActivity();
        if(acty!=null&&acty  instanceof DownCacheActivity){
            activity=  ((DownCacheActivity) acty);
            activity.setEditStatusChangeListener(this);
        }
        /* 设置展示的数据*/
        mAdapter = new CachedRVAdapter(getActivity(), listData,false);
        recy_cache.setLayoutManager(new LinearLayoutManager(getActivity()));
        recy_cache.setAdapter(mAdapter);
        mAdapter.setListener(new CachedRVAdapter.Listener() {
            @Override
            public void setCBQXCheckListener(boolean isCheck) {
                /* 设置全选的选择的状态*/
                setCBCheck(isCheck);

            }

            @Override
            public void setItemClickListener(View v, DownloadInfo info, int possion) {
                /* 条目点击进入 播放界面*/
                if(info==null){
                    return;
                }
//                Intent intent = new Intent(getActivity(), VoidActivity2.class);
//                intent.putExtra("liveId", info.getLiveId());
//                intent.putExtra("voidUrl", info.getUri());
//                intent.putExtra("isBanner", false);
//                intent.putExtra("isFull", true);
//                getActivity().startActivity(intent);
                /**
                 * 播放已缓存视频（点击已缓存界面中已缓存中的视频列表中的某一条木）
                 */
       }

            @Override
            public void setDeleteClickListener() {
                showDeleteDialog();

            }
        });
        //editStatusChangeListener(false);//默认进入为非选择页面

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_down_cache;
    }

    /**
     * 设置全选按钮是否选中
     *
     * @param isCheck
     */
    public void setCBCheck(boolean isCheck) {
        if (isCheck) {
            iv_cache_edit_check.setImageResource(R.mipmap.msg_selected);
        } else {
            iv_cache_edit_check.setImageResource(R.mipmap.msg_unselected);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cache_edit_delete:
                    if(mAdapter!=null){
                        mAdapter.deleteSelectItem();
                    }
                    break;
                case R.id.iv_cache_edit_check:
                    mIsChecked = !mIsChecked;
                    setCBCheck(mIsChecked);
                    if(mAdapter!=null){
                        mAdapter.setAllEditStatus(mIsChecked);
                    }
                    break;

            }
        }
    };


    /* activity 传递 内容给 Fragment*/
    @Override
    public void editStatusChangeListener(boolean isEdit) {
        mIsEdit=isEdit;//重新赋值,刷新列表
        if (mIsEdit) {
            include_cache_delete.setVisibility(View.VISIBLE);
        } else {
            include_cache_delete.setVisibility(View.GONE);
        }
        if(mAdapter!=null){
            mAdapter.setEdit(mIsEdit);
        }
    }

    @Override
    public void downInfoStatusChangeListener() {
        refrashData();
    }

    /**
     * 设置删除布局gone
     */
    public void setDelLayoutGone() {
        mIsEdit = false;
        include_cache_delete.setVisibility(View.GONE);
        mAdapter.setEdit(mIsEdit);
        if(activity!=null){
            activity.setEditStatus(mIsEdit);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        /* 移除监听*/
         if(activity!=null&&activity  instanceof DownCacheActivity){
            ((DownCacheActivity) activity).removeEditStatusChangeListener(this);
        }
    }
  
    @Override
    public void showDeleteDialog() {
        if(deletDialog==null){
            deletDialog= new MyDialog(getActivity());
        }
        deletDialog.showDialog(R.layout.dialog_exit);
        TextView tv_content = deletDialog.findViewById(R.id.tv_content);
        tv_content.setText("是否删除");
        TextView dialog_exit_false =  deletDialog.findViewById(R.id.dialog_exit_false);
        dialog_exit_false.setText("否");
        TextView dialog_exit_true = deletDialog.findViewById(R.id.dialog_exit_true);
        dialog_exit_true.setText("是");
        dialog_exit_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletDialog.cancel();
            }
        });
        dialog_exit_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listData==null||listData.size()==0){
                    return;
                }
                List<DownloadInfo> removeList=new ArrayList<>();
                for (int i = 0; i < listData.size(); i++) {
                    if(listData.get(i).isSelected()){
                        // TODO: 2019/7/23  处理删除逻辑
                        getDownloadManager().remove(listData.get(i));
                        removeList.add(listData.get(i));
                    }
                }
                listData.removeAll(removeList);
                setDataChange();
                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }

                setDelLayoutGone();
                deletDialog.cancel();
            }
        });
    }


    /* 获取下载管理类的数据*/
    private DownloadManager getDownloadManager(){
        return   DownloadService.getDownloadManager(App.getContext());
    }

    /* 刷新数据*/
    private void  refrashData(){
        // TODO: 2019/7/23  获取所有的数据
        List<DownloadInfo> allData = getDownloadManager().findAllDownloaded();
        listData.clear();
        listData.addAll(allData);

        setDataChange();
        if ( mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setDataChange(){
        if(ownLS!=null){
            if(listData.size()==0){
                ownLS.showCallback(EmptyCallback.class);
            }else{
                ownLS.showSuccess();
            }
        }
    }
}