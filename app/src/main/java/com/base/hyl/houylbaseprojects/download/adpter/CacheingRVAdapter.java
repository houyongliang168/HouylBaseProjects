package com.base.hyl.houylbaseprojects.download.adpter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.base.common.image.GlideImgManager;
import com.base.common.log.MyLog;
import com.base.common.log.MyToast;
import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.download.bean.DownloadStatusChange;
import com.base.hyl.houylbaseprojects.download.contract.MyDownloadListener;
import com.base.hyl.houylbaseprojects.download.core.DownloadService;
import com.base.hyl.houylbaseprojects.download.core.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.core.db.DownloadDBController;
import com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.SoftReference;
import java.util.List;

import static com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo.STATUS_COMPLETED;
import static com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo.STATUS_REMOVED;
import static com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo.STATUS_WAIT;


/**
 * Created by zcc on 2018/1/17.
 * 视频缓存adapter
 */

public class CacheingRVAdapter extends RecyclerView.Adapter<CacheingRVAdapter.ViewHolder2> {
    private Activity mContext;
    private boolean mIsEdit = false;
    private List<DownloadInfo> mList ;
    private static final String TAG=CacheingRVAdapter.class.getSimpleName();

    private final DownloadManager manager;//下载管理
    private final DownloadDBController dbController;//下载数据库管理


    public CacheingRVAdapter(Activity mContext, List<DownloadInfo> list) {
        this.mContext = mContext;
        this. mList = list;
        manager = DownloadService.getDownloadManager(App.getContext());
        dbController = manager.getDownloadDBController();
    }

    /**
     * 全部暂停
     */
    public void allPause() {
        manager.pauseAll();
        notifyDataSetChanged();
    }

    /**
     * 全部开始
     */
    public void allStart() {
        manager.resumeAll();
        notifyDataSetChanged();
    }

    public void  remove(DownloadInfo downloadInfo){
        manager.remove(downloadInfo);
    }
    /**
     * 删除 选中的条目
     */
    public void deleteSelectItem() {
        if(getAllEditCount()<=0){
            MyToast.showShort("请选择需要删除的内容..");
            return;
        }
        if(listener!=null){
            listener. setDeleteClickListener();
        }
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cache_doing, parent, false));
    }

    @Override
    public int getItemCount() {
        MyLog.wtf(TAG,"mList.size() :"+mList.size());
        return mList==null?0:mList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder2 holder, final int position) {
      final DownloadInfo data=  mList.get(position);
      if(data==null){return;}
      /* 基本参数设置*/
        GlideImgManager.glideLoader(mContext, data.getImgUrl(), R.mipmap.iv_error, R.mipmap.iv_error,  holder.iv_doing_image, 1);
        holder.tv_doing_title.setText(data.getTitle());
        holder. tv_doing_content.setText(data.getDescription());
        holder. cb_doing_edit.setChecked(data.isSelected());
        /* 基本数据处理*/
        if (mIsEdit) {
            holder.cb_doing_edit.setVisibility(View.VISIBLE);
        } else {
            holder.cb_doing_edit.setVisibility(View.GONE);
        }

       /* 选择框的点击事件*/
        holder. cb_doing_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.setSelected(isChecked);
                    /* 设置是否全选的状态*/
                    if(listener!=null){
                        listener.setCBQXCheckListener(getAllEditStatus());
                    }

                }
            });
        /* 控件的点击事件*/
        holder. iv_doing_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 设置点击事件
                downLoadControl(data,true);
            }});

        data.setDownloadListener(new MyDownloadListener(new SoftReference(holder)) {
                @Override
                public void onRefresh() {
                    if (getUserTag() != null && getUserTag().get() != null) {
                        ViewHolder2 viewHolder = (ViewHolder2) getUserTag().get();
                        viewHolder.refresh( data,position);
                    }

                }
            });
       // downLoadControl(data,false);
        holder.refresh( data,position);/* 数据展示*/
    }

    private void downLoadControl(DownloadInfo info,boolean isClick){
    if (info == null||manager==null) {
        return;
    }
    if(isClick){
        switch (info.getStatus()) {
            case DownloadInfo.STATUS_NONE:
            case DownloadInfo.STATUS_PAUSED:
            case DownloadInfo.STATUS_ERROR:
                //resume downloadInfo
                manager.resume(info);
                break;
            case DownloadInfo.STATUS_DOWNLOADING:
            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
            case STATUS_WAIT:
                //pause downloadInfo
                manager.pause(info);
                break;
            case STATUS_COMPLETED:
                //  downloadManager.remove(downloadInfo);
                break;
        }
    }else{
        switch (info.getStatus()) {
            case DownloadInfo.STATUS_NONE:
                manager.download(info);
                break;
            case DownloadInfo.STATUS_PAUSED:
            case DownloadInfo.STATUS_ERROR:
                manager.pause(info);
                break;
            case DownloadInfo.STATUS_DOWNLOADING:
            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
            case STATUS_WAIT:
                manager.resume(info);
                break;
            case STATUS_COMPLETED:
                //  downloadManager.remove(downloadInfo);
                break;
        }
    }
    }


    public void  addData( DownloadInfo data){
        if(data==null){
            return;
        }
        MyLog.wtf(TAG,"addData"+data.toString());
        manager.download(data);
      // dbController.createOrUpdate(data);
        notifyDataSetChanged();
    }

    /**
     * 编辑
     * @param isEdit
     */
    public void setEdit(boolean isEdit) {
        mIsEdit = isEdit;
        notifyDataSetChanged();
    }


    /**
     * 设置所有 的编辑状态
     * @param isEdit  true  全选  false  全部未选择
     */
    public void  setAllEditStatus(boolean isEdit){
        if(mList==null&&mList.size()==0){
            return;
        }
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(isEdit);
        }
        notifyDataSetChanged();
    }



    /**
     *  判断 选中状态的数量
     * @return
     */
    private int getAllEditCount(){
        if(mList==null&&mList.size()==0){
            return 0;
        }
        int m=0;
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).isSelected()){
                m++;
            }
        }
        return m;
    }

    private boolean  getAllEditStatus(){
        if(mList==null&&mList.size()==0){
            return false;
        }
        if(getAllEditCount()==mList.size()){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder2  extends RecyclerView.ViewHolder {
        public CheckBox cb_doing_edit;
        public ImageView iv_doing_image;
        public TextView tv_doing_title, tv_doing_content;

        public TextView tv_doing_state;//下载状态
        public ImageView iv_doing_state;//下载状态
        public ImageView iv_doing_conn;//连接中
        public ProgressBar progressbar_doing;//下载进度
        public AnimationDrawable iv_cache_conn_frame ;/* 动画*/
        public DownloadInfo ownData ;/*数据*/
        public ViewHolder2(View itemView) {
            super(itemView);
            cb_doing_edit = itemView.findViewById(R.id.cb_doing_edit);
            iv_doing_image = itemView.findViewById(R.id.iv_doing_image);
            tv_doing_title = itemView.findViewById(R.id.tv_doing_title);
            tv_doing_content = itemView.findViewById(R.id.tv_doing_content);
            tv_doing_state = itemView.findViewById(R.id.tv_doing_state);
            iv_doing_state = itemView.findViewById(R.id.iv_doing_state);
            iv_doing_conn = itemView.findViewById(R.id.iv_doing_conn);
            progressbar_doing = itemView.findViewById(R.id.progressbar_doing);
            iv_cache_conn_frame = (AnimationDrawable) iv_doing_conn.getBackground();
        }
        public void refresh(DownloadInfo data,int possion) {//刷新方法
            if (data == null) {
                MyLog.wtf(TAG,"refresh(DownloadInfo data) NULL");
               return;
            }
            ownData=data;
            switch (data.getStatus()) {
                case DownloadInfo.STATUS_NONE:
                    iv_cache_conn_frame.start();
                    tv_doing_state.setText("连接中..");
                    iv_doing_conn.setVisibility(View.VISIBLE);
                    progressbar_doing.setVisibility(View.GONE);
                    iv_doing_state.setImageResource(R.mipmap.cache_doing_pause);
                    tv_doing_state.setTextColor(Color.parseColor("#bab4ae"));
                    break;
                case STATUS_WAIT:
                    iv_cache_conn_frame.start();
                    tv_doing_state.setText("队列中..");
                    iv_doing_conn.setVisibility(View.VISIBLE);
                    progressbar_doing.setVisibility(View.GONE);
                    iv_doing_state.setImageResource(R.mipmap.cache_doing_pause);
                    tv_doing_state.setTextColor(Color.parseColor("#bab4ae"));
                    break;
                case DownloadInfo.STATUS_PAUSED:
                    iv_cache_conn_frame.stop();
                    tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    tv_doing_state.setText("已暂停");
                    iv_doing_conn.setVisibility(View.GONE);
                    progressbar_doing.setVisibility(View.VISIBLE);
                    iv_doing_state.setImageResource(R.mipmap.cache_doing_down);
                    break;
                case DownloadInfo.STATUS_ERROR:
                    iv_cache_conn_frame.stop();
                    tv_doing_state.setText("下载失败");
                    iv_doing_conn.setVisibility(View.GONE);
                    progressbar_doing.setVisibility(View.VISIBLE);
                    tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                    break;
                case DownloadInfo.STATUS_DOWNLOADING:
                    iv_cache_conn_frame.stop();
                    iv_doing_conn.setVisibility(View.GONE);
                    progressbar_doing.setVisibility(View.VISIBLE);
                    tv_doing_state.setTextColor(Color.parseColor("#bab4ae"));
                    long countLenF = data.getSize();
                    long readLenF = data.getProgress();
                    float countLen = countLenF / 1048576;
                    float readLen = readLenF / 1048576;
                    if (countLen < 1) {
                        countLen = countLenF / 1024;
                        readLen = readLenF / 1024;
                        tv_doing_state.setText(String.format("%.2f%s%.2f%s", countLen, "K/", readLen, "KB"));
                    } else {
                        tv_doing_state.setText(String.format("%.2f%s%.2f%s", countLen, "M/", readLen, "MB"));
                    }
                    iv_doing_state.setImageResource(R.mipmap.cache_doing_pause);
                    progressbar_doing.setMax((int) countLenF);
                    progressbar_doing.setProgress((int) readLenF);
                    break;
                case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                    iv_cache_conn_frame.start();
                    tv_doing_state.setText("连接中..");
                    iv_doing_conn.setVisibility(View.VISIBLE);
                    progressbar_doing.setVisibility(View.GONE);
                    iv_doing_state.setImageResource(R.mipmap.cache_doing_pause);
                    tv_doing_state.setTextColor(Color.parseColor("#bab4ae"));

                    break;
                case STATUS_COMPLETED:
                    tv_doing_state.setText("下载完成");
                    progressbar_doing.setVisibility(View.VISIBLE);
                    tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    mList.remove(possion);
                    CacheingRVAdapter. this. notifyItemRemoved(possion);
                    notifyItemRangeChanged(possion, mList.size());
                    if(listener!=null){
                    listener.countChangeListener(mList.size());
                }
                    EventBus.getDefault().post(new DownloadStatusChange());
                    break;
                case STATUS_REMOVED:

//                    CacheingRVAdapter. this. notifyItemRemoved(possion);
//                    notifyItemRangeChanged(possion, mList.size());
//                    EventBus.getDefault().post(new DownloadStatusChange());
                    break;

        }
        }

    }

    /* 设置监听*/
    private Listener listener;
    public Listener getListener() {
        return listener;
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public interface Listener{
        void setCBQXCheckListener(boolean isCheck);/* 设置全选的状态*/
        void  setItemClickListener(View v, DownloadInfo info, int possion);
        void  setDeleteClickListener();
        void  countChangeListener(int count);
    }

}
