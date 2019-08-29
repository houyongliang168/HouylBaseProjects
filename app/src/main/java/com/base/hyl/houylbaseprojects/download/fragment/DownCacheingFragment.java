package com.base.hyl.houylbaseprojects.download.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.base.common.log.MyLog;
import com.base.common.log.MyToast;
import com.base.common.store.SPUtil;
import com.base.common.utils.NetUtils;
import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.Constant;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.download.activity.DownCacheActivity;
import com.base.hyl.houylbaseprojects.download.adpter.CacheingRVAdapter;
import com.base.hyl.houylbaseprojects.download.contract.DowningFragmentPresenter;
import com.base.hyl.houylbaseprojects.download.contract.IDowningFragmentContract;

import com.base.hyl.houylbaseprojects.download.core.DownloadService;
import com.base.hyl.houylbaseprojects.download.core.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.core.db.DownloadDBController;
import com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo;
import com.base.widget.dialog.MyDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zcc on 2018/1/15.
 * 视频缓存
 */

public class DownCacheingFragment extends CoreBaseFragment<DowningFragmentPresenter> implements IDowningFragmentContract.IDowningFragmentView , DownCacheActivity.EditStatusChangeListener {

    private static final String FRAGMENT_EDIT = "edit";
    private static final String FRAGMENT_DATA = "data";
    private static final String TAG = DownCacheingFragment.class.getSimpleName();
    private DownCacheActivity activity;

    private LinearLayout ll_downing_cache;
    private RecyclerView recy_cache;
    private View ll_cache, ll_cache_pause, ll_cache_start;
    private View include_cache_delete, tv_cache_edit_delete;
    private ImageView iv_cache_edit_check;
    private MyDialog netDialog;
    private MyDialog deletDialog;
    private boolean mIsEdit = false;
    private CacheingRVAdapter mAdapter = null;
    private boolean mIsChecked = false;
    private List<DownloadInfo> listData = new ArrayList<>();

    private boolean isShowNet = false;//是否显示4G对话框提醒
    private String mFilePath;

    private DownloadInfo downloadInfo;
    private DownloadDBController dbController;
    private LoadService ownLS;
    private View root;

    public DownCacheingFragment(){

    }
    /* 获取Fragment 对象*/
    public static DownCacheingFragment getInstance( boolean isEdit,DownloadInfo downloadInfo) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(FRAGMENT_EDIT, isEdit);
        bundle.putSerializable(FRAGMENT_DATA, downloadInfo);
        DownCacheingFragment fragment = new DownCacheingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static DownCacheingFragment getInstance() {
        DownCacheingFragment fragment = new DownCacheingFragment();
        return fragment;
    }


    /**
     * 添加一条数据
     * @param info
     */
    public void addData( DownloadInfo info) {

        List<DownloadInfo> allDownloading = getDownloadManager().findAllDownloading();
        MyLog.wtf(TAG,"adddata  before listData size:"+allDownloading.size());
        boolean isCanAdd=false;
        if(info!=null){
            MyLog.wtf(TAG,"addData DownloadInfo:"+info);


            String liveId = info.getLiveId();
            DownloadInfo byId = getDownloadManager().getDownloadById(liveId);
            /* 消息提示信息*/
            if(byId!=null){//原来数据库有消息
                if(byId.getStatus()== DownloadInfo.STATUS_COMPLETED){
                    MyToast.showShort("该视频已下载完成");
                }else if(byId.getStatus()!= DownloadInfo.STATUS_REMOVED){
                    MyToast.showShort("该视频已在下载列表中");
                }
                isCanAdd=false;
            }else{//没有消息 ,新建一个任务 (下载文件的时候判断有没有文件)
                getFileDirectory();//初始化 FILe 路径
                String url = info.getUri();
                MyLog.wtf("zcc", "downfileUrl:" + url);
                String[] urlSplit = url.split("/");
                String fileName = urlSplit[urlSplit.length - 1];
                MyLog.wtf("zcc", "downfileName:" + fileName);
                File outputFile = new File(mFilePath, fileName);
                info.setId(info.getLiveId());
                info.setStatus(DownloadInfo.STATUS_NONE);
                info.setPath(outputFile.getAbsolutePath());
                if(!allDownloading.contains(info)){
                  //  allDownloading.add(info);
                  //  getDownloadManager().download(info);
                    isCanAdd=true;
                }else{
                    isCanAdd=false;
                }
            }
        }
        listData.clear();
        listData.addAll(allDownloading);

        if(mAdapter!=null){
            if(info!=null&&isCanAdd){
                listData.add(info);
                mAdapter.addData( info);
            }else{
                mAdapter.notifyDataSetChanged();
            }

        }
        setDataChange();

    }



//    /* 获取数据库的数据*/
//    private DownloadDBController  getDbController(){
//        if(dbController==null){
//             dbController = DownloadService.getDownloadDBController(getActivity());
//        }
//        return dbController;
//    }
    /* 获取下载管理类的数据*/
    private DownloadManager getDownloadManager(){
      return   DownloadService.getDownloadManager(App.getContext());
    }

    @Override
    public void loadData() {
        addData(downloadInfo);
        /* 网络提醒处理*/
        isShowNet = true;
        if(listData.size()>0){
            if (NetUtils.getNetworkType(getActivity()) == 4) {//2g3g4g
                showNetDialog();
            }
        }

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

            }
        });
        return ownLS.getLoadLayout();
    }

    @Override
    public void initUI(View roots, @Nullable Bundle savedInstanceState) {
        getBinderData();
        /* 初始化控件信息*/
        recy_cache = root.findViewById(R.id.recy_cache);
        ll_downing_cache = root.findViewById(R.id.ll_downing_cache);
        ll_cache = root.findViewById(R.id.ll_cache);
        ll_cache_pause = root.findViewById(R.id.ll_cache_pause);
        ll_cache_pause.setOnClickListener(mOnClickListener);
        ll_cache_start = root.findViewById(R.id.ll_cache_start);
        ll_cache_start.setOnClickListener(mOnClickListener);

        include_cache_delete = root.findViewById(R.id.include_cache_delete);
        iv_cache_edit_check =  include_cache_delete.findViewById(R.id.iv_cache_edit_check);
        mIsChecked = false;
        setCBCheck(false);
        iv_cache_edit_check.setOnClickListener(mOnClickListener);
        tv_cache_edit_delete = include_cache_delete.findViewById(R.id.tv_cache_edit_delete);
        tv_cache_edit_delete.setOnClickListener(mOnClickListener);
        /* 设置相关参数*/
        mAdapter = new CacheingRVAdapter(this.getActivity(), listData);
        recy_cache.setLayoutManager(new LinearLayoutManager(getActivity()));
        recy_cache.setAdapter(mAdapter);
        mAdapter.setListener(new CacheingRVAdapter.Listener() {
            @Override
            public void setCBQXCheckListener(boolean isCheck) {
                setCBCheck(isCheck);
            }
            @Override
            public void setItemClickListener(View v, DownloadInfo info, int possion) {

            }
            @Override
            public void setDeleteClickListener() {
                showDeleteDialog();
            }

            @Override
            public void countChangeListener(int count) {
               setDataChange();
            }
        });
        /* 设置状态*/
        setEditStatus(mIsEdit);

        /* 设置activity监听*/
        Activity acty = getActivity();
        if(acty!=null&&acty  instanceof DownCacheActivity){
            activity=  ((DownCacheActivity) acty);
            activity.setEditStatusChangeListener(this);
        }
    }

    /**
     *  获取传递的信息
     */
    private void getBinderData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            downloadInfo = (DownloadInfo) bundle.getSerializable(FRAGMENT_DATA);
            mIsEdit = bundle.getBoolean(FRAGMENT_EDIT);
        }
    }

    /* 获取文件下载 参数相关信息*/
    private void getFileDirectory() {
        /* 获取文件夹*/
        mFilePath = Constant.DOWNLOAD_PATH;
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//无sd 卡
            if (!TextUtils.isEmpty(Environment.getExternalStorageDirectory().getPath())) {
                mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "com.hyl"+ File.separatorChar  ;
            }
        } else {//有sd 卡
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "com.hyl"+ File.separatorChar;
        }
        File folder = new File(mFilePath);
        if (!folder.exists()) {
            folder.mkdirs();
            MyLog.wtf(TAG,"File 不存在");
        }else{
            MyLog.wtf(TAG,"File 存在");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_downing_cache;
    }

    /**
     * 设置允许4G下载视频
     */

    public boolean showNetDialog() {
        SPUtil spUtils = new SPUtil("autoLogin");
        boolean isGLoad = spUtils.getBoolean("isGLoad");
        if (isGLoad) {
            MyToast.showShort("您已允许4G下载视频");
            return true;
        } else {
            if (mAdapter != null) {
                mAdapter.allPause();
            }
            final MyDialog netDialog = new MyDialog(getActivity());
            netDialog.showDialog(R.layout.dialog_exit);
            TextView tv_content = (TextView) netDialog.findViewById(R.id.tv_content);
            tv_content.setText("当前为非WIFI环境，如需流量下载请点击开启“允许运营商网络下载”");
            TextView dialog_exit_false = (TextView) netDialog.findViewById(R.id.dialog_exit_false);
            dialog_exit_false.setText("取消");
            TextView dialog_exit_true = (TextView) netDialog.findViewById(R.id.dialog_exit_true);
            dialog_exit_true.setText("去开启");
            dialog_exit_false.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    netDialog.cancel();
                }
            });
            dialog_exit_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    netDialog.cancel();

                }
            });
            isShowNet = false;
            return false;
        }
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

    public void setEditStatus(boolean mIsEdit){
        this.mIsEdit=mIsEdit;
        if (mIsEdit) {
            include_cache_delete.setVisibility(View.VISIBLE);
            ll_cache.setVisibility(View.GONE);
        } else {
            ll_cache.setVisibility(View.VISIBLE);
            include_cache_delete.setVisibility(View.GONE);
        }
        if(mAdapter!=null){
            mAdapter.setEdit(mIsEdit);
        }
    }

    /**
     * 设置删除布局gone
     */
    public void setDelLayoutGone() {
        mIsEdit = false;
        ll_cache.setVisibility(View.VISIBLE);
        include_cache_delete.setVisibility(View.GONE);
        mAdapter.setEdit(false);
        if(activity!=null){
            activity.setEditStatus(false);
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
                case R.id.ll_cache_pause:
                    if(mAdapter!=null){
                        mAdapter.allPause();
                    }
                    break;
                case R.id.ll_cache_start:
                    if(listData==null||listData.size()==0){
                        MyToast.showShort("暂无相关数据..");
                        return;
                    }
                    if (NetUtils.getNetworkType(getActivity()) == 4) {//2g3g4g
                        boolean isOpen = showNetDialog();
                        if (isOpen) {
                            if(mAdapter!=null){
                                mAdapter.allStart();
                            }

                        }
                    } else {
                        if(mAdapter!=null){
                            mAdapter.allStart();
                        }

                    }
                    break;
            }
        }
    };


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
                        if(mAdapter!=null){
                            mAdapter.remove(listData.get(i));
                        }
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

    @Override
    public void editStatusChangeListener(boolean isEdit) {
        /* 是否处于编辑状态  */
        mIsEdit=isEdit;//重新赋值,刷新列表
        setEditStatus(isEdit);
    }

    @Override
    public void downInfoStatusChangeListener() {
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