package com.base.hyl.houylbaseprojects.xiazai.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.common.base.BaseFragment;
import com.base.common.log.MyLog;
import com.base.common.log.MyToast;
import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.xiazai.activity.LiveCacheActivity;
import com.base.hyl.houylbaseprojects.xiazai.adpter.CacheRVAdapter;
import com.base.hyl.houylbaseprojects.xiazai.utils.NetUtils;
import com.base.hyl.houylbaseprojects.xiazai.utils.SPUtils;
import com.base.widget.dialog.MyDialog;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.DbDwonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zcc on 2018/1/15.
 * 视频缓存
 */

public class LiveCacheFragment2 extends BaseFragment {
    private static final String FRAGMENTTYPE = "type";
    private static final String FRAGMENTEDIT = "edit";
    private static final String FRAGMENTDATA = "data";
    private View root;
    private String mType = "1";
    private boolean mIsEdit = false;
    private RecyclerView recy_cache;
    private View ll_cache, ll_cache_pause, ll_cache_start;
    private View include_cache_delete, tv_cache_edit_delete;
    private ImageView iv_cache_edit_check;
    private CacheRVAdapter mAdapter = null;
    private boolean mIsChecked = false;
    private DownInfo mDownInfo = null;
    private List<DownInfo> listData = null;
    private DbDwonUtil dbUtil = null;
    private boolean isShowNet = false;//是否显示4G对话框提醒


    public LiveCacheFragment2(){

    }
    public static LiveCacheFragment2 getInstance(String type, boolean isEdit) {
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENTTYPE, type);
        bundle.putBoolean(FRAGMENTEDIT, isEdit);
        LiveCacheFragment2 fragment = new LiveCacheFragment2();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void updateData(String type, boolean isEdit) {
        //TODO:update
        mType = type;
        mIsEdit = isEdit;

        Bundle bundle = getArguments();
        bundle.putString(FRAGMENTTYPE, type);
        bundle.putBoolean(FRAGMENTEDIT, isEdit);
    }

    public void addData(String type,  DownInfo info) {
        //TODO:update
        mType = type;
        mIsEdit = isEdit;

        Bundle bundle = getArguments();
        bundle.putString(FRAGMENTTYPE, type);
        bundle.putBoolean(FRAGMENTEDIT, isEdit);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString(FRAGMENTTYPE);
            mIsEdit = bundle.getBoolean(FRAGMENTEDIT);
            if (!mType.equals("1")) {
                mDownInfo = (DownInfo) bundle.getSerializable(FRAGMENTDATA);
            }
        }
        RxRetrofitApp.init(App.getContext());
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (mType.equals("1")) {//done
                isShowNet = false;
            } else {
                isShowNet = true;
                if (mAdapter != null) {
                    MyLog.wtf("zcc", "show");
                    if (NetUtils.getNetworkType(getActivity()) == 4) {//2g3g4g
                        showNetDialog();
                    }
                }
            }
            if (dbUtil != null && mAdapter != null) {
                List<DownInfo> allData = dbUtil.queryDownAll();
                listData = new ArrayList<>();
                if (mType.equals("1")) {//done
                    for (DownInfo info : allData) {
                        if (info.getState() == DownState.FINISH) {
                            listData.add(info);
                        }
                    }
                } else {
                    for (DownInfo info : allData) {
                        if (info.getState() != DownState.FINISH) {
                            listData.add(info);
                        }
                    }
                }
                mAdapter.update(listData);
            }
        } else {
            if (mType.equals("2") && dbUtil != null && mAdapter != null) {
                List<DownInfo> dataList = mAdapter.getList();
             /*记录退出时下载任务的状态-复原用*/
                for (DownInfo downInfo : dataList) {
                    dbUtil.update(downInfo);
                }
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    private void initData() {
        /* 获取文件夹*/
        String mFilePath = "/storage/emulated/0/hyl/";
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//无sd 卡
            if (Environment.getExternalStorageDirectory().getPath() != "") {
                mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "hyl"+ File.separatorChar  ;
            }
        } else {//有sd 卡
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "hyl"+ File.separatorChar;
        }
         File folder = new File(mFilePath);
        //File folder = Environment.getExternalStoragePublicDirectory(mFilePath);
        if (!folder.exists()) {
            folder.mkdirs();
            MyToast.showShort("不存在");
        }else{
            MyToast.showShort("存在");
        }

        dbUtil = DbDwonUtil.getInstance();
        if (mDownInfo != null) {
            String liveId = mDownInfo.getLiveId();
            MyLog.wtf("zcc", "liveId:" + liveId);
            DownInfo downInfo = dbUtil.queryDownById(liveId);
            if (downInfo != null) {
                //已在数据库中
                if (downInfo.getState() == DownState.FINISH) {
                    MyToast.showShort("该视频已下载完成");
                } else {
                    MyToast.showShort("该视频已在下载列表中");
                }
            } else {

                String url = mDownInfo.getUrl();
                MyLog.wtf("zcc", "downfileUrl:" + url);
                String[] urlSplit = url.split("/");
                String fileName = urlSplit[urlSplit.length - 1];
                MyLog.wtf("zcc", "downfileName:" + fileName);
                File outputFile = new File(mFilePath, fileName);
                mDownInfo.setId(System.currentTimeMillis());
                mDownInfo.setState(DownState.DOWN);
                mDownInfo.setSavePath(outputFile.getAbsolutePath());
                MyLog.wtf("zcc", "downfileSavepath:" + outputFile.getAbsolutePath());
                dbUtil.save(mDownInfo);
            }
        }
        List<DownInfo> allData = dbUtil.queryDownAll();
        listData = new ArrayList<>();
        if (mType.equals("1")) {//done
            for (DownInfo info : allData) {
                if (info.getState() == DownState.FINISH) {
                    listData.add(info);
                }
            }
        } else {
            for (DownInfo info : allData) {
                if (info.getState() != DownState.FINISH) {
                    listData.add(info);
                }
            }
        }
        mAdapter = new CacheRVAdapter(this.getActivity(), listData);
        mAdapter.setListener(new CacheRVAdapter.Listener() {
            @Override
            public void setCBCheckListener(boolean isCheck) {
            setCBCheck(isCheck);
            }

            @Override
            public boolean showNetDialog() {
              return   showNetDialog();

            }

            @Override
            public void setDelLayoutGone() {
                setDelLayoutGone();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (root == null) {
            root = inflater.inflate(R.layout.live_fragment_cache, container, false);
        } else {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null) {
                parent.removeView(root);
            }
        }
        recy_cache = root.findViewById(R.id.recy_cache);

        ll_cache = root.findViewById(R.id.ll_cache);
        ll_cache_pause = root.findViewById(R.id.ll_cache_pause);
        ll_cache_pause.setOnClickListener(mOnClickListener);
        ll_cache_start = root.findViewById(R.id.ll_cache_start);
        ll_cache_start.setOnClickListener(mOnClickListener);

        include_cache_delete = root.findViewById(R.id.include_cache_delete);
        iv_cache_edit_check = (ImageView) include_cache_delete.findViewById(R.id.iv_cache_edit_check);
        mIsChecked = false;
        setCBCheck(false);
        iv_cache_edit_check.setOnClickListener(mOnClickListener);
        tv_cache_edit_delete = include_cache_delete.findViewById(R.id.tv_cache_edit_delete);
        tv_cache_edit_delete.setOnClickListener(mOnClickListener);

        if (mType.equals("1")) {//done
            ll_cache.setVisibility(View.GONE);
            if (mIsEdit) {
                include_cache_delete.setVisibility(View.VISIBLE);
                mAdapter.setEdit(true, false);
            } else {
                include_cache_delete.setVisibility(View.GONE);
                mAdapter.setEdit(false, false);
            }

        } else {//doing
            if (isShowNet) {
                MyLog.wtf("zcc", "show");
                if (NetUtils.getNetworkType(getActivity()) == 4) {//2g3g4g
                    showNetDialog();
                }
            }
            if (mIsEdit) {
                include_cache_delete.setVisibility(View.VISIBLE);
                ll_cache.setVisibility(View.GONE);
                mAdapter.setEdit(true, false);
            } else {
                ll_cache.setVisibility(View.VISIBLE);
                include_cache_delete.setVisibility(View.GONE);
                mAdapter.setEdit(false, false);
            }
        }
        recy_cache.setLayoutManager(new LinearLayoutManager(getActivity()));
        recy_cache.setAdapter(mAdapter);

        return root;
    }

    /**
     * 设置允许4G下载视频
     */
    public boolean showNetDialog() {
        SPUtils spUtils = new SPUtils("autoLogin");
        boolean isGLoad = spUtils.getBoolean("isGLoad");
        if (isGLoad) {
            MyToast.showShort("您已允许4G下载视频");
            return true;
        } else {
            if (mAdapter != null) {
                mAdapter.allPause();
            }
            final MyDialog dialog = new MyDialog(getActivity());
            dialog.showDialog(R.layout.dialog_exit);
            TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
            tv_content.setText("当前为非WIFI环境，如需流量下载请点击开启“允许运营商网络下载”");
            TextView dialog_exit_false = (TextView) dialog.findViewById(R.id.dialog_exit_false);
            dialog_exit_false.setText("取消");
            TextView dialog_exit_true = (TextView) dialog.findViewById(R.id.dialog_exit_true);
            dialog_exit_true.setText("去开启");
            dialog_exit_false.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog_exit_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();

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

    /**
     * 设置删除布局gone
     */
    public void setDelLayoutGone() {
        mIsEdit = false;
        if (mType.equals("1")) {//done
            include_cache_delete.setVisibility(View.GONE);
            mAdapter.setEdit(false, false);
        } else {
            ll_cache.setVisibility(View.VISIBLE);
            include_cache_delete.setVisibility(View.GONE);
            mAdapter.setEdit(false, false);
        }
        LiveCacheActivity activity = (LiveCacheActivity) getActivity();
        activity.setEditFalse();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cache_edit_delete:
                    mAdapter.delete();
                    break;
                case R.id.iv_cache_edit_check:
                    if (mIsChecked) {
                        iv_cache_edit_check.setImageResource(R.mipmap.msg_unselected);
                    } else {
                        iv_cache_edit_check.setImageResource(R.mipmap.msg_selected);
                    }
                    mIsChecked = !mIsChecked;
                    mAdapter.setEdit(true, mIsChecked);
                    break;
                case R.id.ll_cache_pause:
                    mAdapter.allPause();
                    break;
                case R.id.ll_cache_start:
                    if (NetUtils.getNetworkType(getActivity()) == 4) {//2g3g4g
                        boolean isOpen = showNetDialog();
                        if (isOpen) {
                            mAdapter.allStart();
                        }
                    } else {
                        mAdapter.allStart();
                    }
                    break;
            }
        }
    };

}