package com.base.hyl.houylbaseprojects.xiazai.adpter;

import android.content.Context;
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
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.xiazai.utils.FileUtil;
import com.base.hyl.houylbaseprojects.xiazai.utils.NetUtils;
import com.base.widget.dialog.MyDialog;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.HttpDownManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.DbDwonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcc on 2018/1/17.
 * 视频缓存adapter
 */

public class CacheRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CACHE_DONE = 0;
    public static final int TYPE_CACHE_DOING = 1;//将type写入list每个item对象的type属性中
    private Context mContext;
    private boolean mIsEdit = false;
    private boolean mIsAllEdit = false;
    private List<DownInfo> mList ;
    private HttpDownManager manager = null;
    private int mDelCount = 0;
    private DbDwonUtil dbUtil;
    private AnimationDrawable iv_cache_conn_frame = null;

    public CacheRVAdapter(Context mContext, List<DownInfo> list) {
        this.mContext = mContext;
        this. mList = list;
        manager = HttpDownManager.getInstance();
        dbUtil = DbDwonUtil.getInstance();
    }



    /**
     * 获取list
     *
     * @return
     */
    public List<DownInfo> getList() {
        return mList;
    }
    /**
     * 更新
     *
     * @param list
     */
    public void update(List<DownInfo> list) {
        mList = list;
        notifyDataSetChanged();
    }
    /**
     * 编辑，全选
     *
     * @param isEdit
     * @param isAllEdit
     */
    public void setEdit(boolean isEdit, boolean isAllEdit) {
        mIsEdit = isEdit;
        mIsAllEdit = isAllEdit;
        notifyDataSetChanged();
    }

    /**
     * 全部暂停
     */
    public void allPause() {
        for (DownInfo info : mList) {
            info.setState(DownState.PAUSE);
        }
        manager.pauseAll();
        notifyDataSetChanged();
    }

    /**
     * 全部开始
     */
    public void allStart() {
        for (DownInfo info : mList) {
            info.setState(DownState.DOWN);
            manager.startDown(info);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除
     */
    public void delete() {
        if (mDelCount != 0) {
            showDialog();
        }
    }

    /**
     * 显示删除对话框
     */
    private void showDialog() {
        final MyDialog dialog = new MyDialog(mContext);
        dialog.showDialog(R.layout.dialog_exit);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        tv_content.setText("是否删除");
        TextView dialog_exit_false = (TextView) dialog.findViewById(R.id.dialog_exit_false);
        dialog_exit_false.setText("否");
        TextView dialog_exit_true = (TextView) dialog.findViewById(R.id.dialog_exit_true);
        dialog_exit_true.setText("是");
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
                List<DownInfo> resultList = new ArrayList<>();
                for (DownInfo info : mList) {
                    if (info.isDelFlag()) {
                        FileUtil.deleteFile(info.getSavePath());
                        dbUtil.deleteDowninfo(info);
                    } else {
                        resultList.add(info);
                    }
                }
                mList = resultList;
                if(listener!=null){
                    listener.  setDelLayoutGone();
                }

            }
        });
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_CACHE_DONE.ordinal()) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cache_done, parent, false));
        } else if(viewType== ITEM_TYPE.TYPE_CACHE_DOING.ordinal()){
            return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cache_doing, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder==null){
            return;
        }
        if (holder instanceof ViewHolder) {
            final ViewHolder vh01=   (ViewHolder)holder;
            final DownInfo item = mList.get(position);
            GlideImgManager.glideLoader(mContext, item.getImageUrl(), R.mipmap.iv_error, R.mipmap.iv_error, vh01.iv_done_image, 1);
            vh01.tv_done_title.setText(item.getTitle());
            vh01.tv_done_content.setText(item.getDescription());
            float countLen = item.getCountLength();
            float countLength = countLen / 1048576;
            if (countLength < 1) {
                countLength = countLen / 1024;
                vh01.tv_done_size.setText(String.format("%.2f%s", countLength, "K"));
            } else {
                vh01.tv_done_size.setText(String.format("%.2f%s", countLength, "M"));
            }
            if(mIsEdit){
                vh01.cb_done_edit.setVisibility(View.VISIBLE);
                if (mIsAllEdit) {
                    vh01.cb_done_edit.setChecked(true);
                    if(listener!=null){
                        listener.setCBCheckListener(true);
                    }
                } else {
                    vh01.cb_done_edit.setChecked(false);
                    if(listener!=null){
                        listener.setCBCheckListener(false);
                    }
                }
                vh01.cb_done_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setDelFlag(isChecked);
                        if (isChecked) {
                            mDelCount++;
                        } else {
                            mDelCount--;
                        }
                        MyLog.wtf("zcc", "delcount:" + mDelCount);
                        if (mDelCount == getItemCount()) {
                            if(listener!=null){
                                listener.setCBCheckListener(true);
                            }
                        } else {
                            if(listener!=null){
                                listener.setCBCheckListener(false);
                            }
                        }
                    }
                });
            }else{
                mDelCount = 0;
                vh01.cb_done_edit.setVisibility(View.GONE);
            }



        }else if(holder instanceof ViewHolder2){
            final ViewHolder2   vh02=  (ViewHolder2)holder;
            final DownInfo downInfo = mList.get(position);
            iv_cache_conn_frame = (AnimationDrawable) vh02.iv_doing_conn.getBackground();

            downInfo.setListener(new HttpDownOnNextListener<DownInfo>() {
                @Override
                public void onNext(DownInfo downInfo) {
                    MyLog.wtf("zcc", "下载完成");
                    vh02.tv_doing_state.setText(downInfo.getSavePath());
                    dbUtil.update(downInfo);
                }

                @Override
                public void onStart() {
                    MyLog.wtf("zcc", "开始下载");
                    iv_cache_conn_frame.stop();
                    vh02.iv_doing_conn.setVisibility(View.GONE);
                    vh02.progressbar_doing.setVisibility(View.VISIBLE);
                }

                @Override
                public void onComplete() {
                    MyLog.wtf("zcc", "下载完成");
                  //  移除条目
                    getList().remove(position);
                    notifyItemRangeRemoved(position,1);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    MyLog.wtf("zcc", "失败:" + e.toString());
                    vh02.iv_doing_conn.setVisibility(View.GONE);
                    vh02.progressbar_doing.setVisibility(View.GONE);
                    vh02.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    vh02.tv_doing_state.setText("下载失败");
                    vh02.iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                }

                @Override
                public void onPuase() {
                    super.onPuase();
                    MyLog.wtf("zcc", "提示：暂停");
                    vh02.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    vh02.tv_doing_state.setText("已暂停");
                    vh02.iv_doing_state.setImageResource(R.mipmap.cache_doing_down);
                }

                @Override
                public void updateProgress(long readLength, long countLength) {
                    MyLog.wtf("zcc", "下载中——总文件大小：" + countLength + "——下载进度：" + readLength);
                    vh02.iv_doing_conn.setVisibility(View.GONE);
                    vh02.progressbar_doing.setVisibility(View.VISIBLE);
                    vh02.tv_doing_state.setTextColor(Color.parseColor("#bab4ae"));
                    float countLenF = countLength;
                    float readLenF = readLength;
                    float countLen = countLenF / 1048576;
                    float readLen = readLenF / 1048576;
                    if (countLen < 1) {
                        countLen = countLenF / 1024;
                        readLen = readLenF / 1024;
                        vh02.tv_doing_state.setText(String.format("%.2f%s%.2f%s", countLen, "K/", readLen, "KB"));
                    } else {
                        vh02.tv_doing_state.setText(String.format("%.2f%s%.2f%s", countLen, "M/", readLen, "MB"));
                    }
                    vh02.iv_doing_state.setImageResource(R.mipmap.cache_doing_pause);
                    vh02.progressbar_doing.setMax((int) countLength);
                    vh02.progressbar_doing.setProgress((int) readLength);
                }
            });
            if (mIsEdit) {
                vh02.cb_doing_edit.setVisibility(View.VISIBLE);
                if (mIsAllEdit) {
                    vh02.cb_doing_edit.setChecked(true);
                    if(listener!=null){
                        listener.setCBCheckListener(true);
                    }

                } else {
                    vh02.cb_doing_edit.setChecked(false);
                    if(listener!=null){
                        listener.setCBCheckListener(false);
                    }
                }
                final DownInfo item = mList.get(position);
                vh02.cb_doing_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setDelFlag(isChecked);
                        if (isChecked) {
                            mDelCount++;
                        } else {
                            mDelCount--;
                        }
                        MyLog.wtf("zcc", "delcount:" + mDelCount);
                        if (mDelCount == getItemCount()) {
                            if(listener!=null){
                                listener.setCBCheckListener(true);
                            }
                        } else {
                            if(listener!=null){
                                listener.setCBCheckListener(false);
                            }
                        }
                    }
                });

            } else {
                mDelCount = 0;
               vh02.cb_doing_edit.setVisibility(View.GONE);
            }


            GlideImgManager.glideLoader(mContext, downInfo.getImageUrl(), R.mipmap.iv_error, R.mipmap.iv_error, vh02.iv_doing_image, 1);
            vh02.tv_doing_title.setText(downInfo.getTitle());
            vh02.tv_doing_content.setText(downInfo.getDescription());
            vh02.progressbar_doing.setMax((int) downInfo.getCountLength());
            vh02.progressbar_doing.setProgress((int) downInfo.getReadLength());
            /**第一次恢复 */
            switch (downInfo.getState()) {
                case START://起始状态
                    break;
                case PAUSE://已暂停
                    vh02.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    vh02.tv_doing_state.setText("已暂停");
                    vh02.iv_doing_conn.setVisibility(View.GONE);
                    vh02.progressbar_doing.setVisibility(View.VISIBLE);
                    vh02.iv_doing_state.setImageResource(R.mipmap.cache_doing_down);
                    break;
                case DOWN://开始下载
                    iv_cache_conn_frame.start();
                    manager.startDown(downInfo);
                    break;
                case STOP://下载停止
                    vh02.tv_doing_state.setText("下载停止");
                    vh02.iv_doing_conn.setVisibility(View.GONE);
                    vh02.progressbar_doing.setVisibility(View.VISIBLE);
                    vh02.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    vh02.iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                    break;
                case ERROR://下载错误
                    vh02.tv_doing_state.setText("下载失败");
                    vh02.iv_doing_conn.setVisibility(View.GONE);
                    vh02.progressbar_doing.setVisibility(View.VISIBLE);
                    vh02.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    vh02.iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                    break;
                case FINISH://下载完成
                    vh02.tv_doing_state.setText("下载完成");
                    break;
            }
            vh02.iv_doing_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownState itemState = downInfo.getState();
                    if (itemState == DownState.START || itemState == DownState.DOWN) {
                        manager.pause(downInfo);
                        downInfo.setState(DownState.PAUSE);
                    } else if (itemState == DownState.PAUSE || itemState == DownState.ERROR) {
                        if (NetUtils.getNetworkType(mContext) == 4) {//2g3g4g
                            if(listener!=null){
                                boolean isOpen=  listener. showNetDialog();
                                if (isOpen) {
                                    downInfo.setState(DownState.DOWN);
                                    manager.startDown(downInfo);
                                }
                            }

                        } else {
                            downInfo.setState(DownState.DOWN);
                            manager.startDown(downInfo);
                        }
                    }
                }
            });
        }






    }


    @Override
    public int getItemViewType(int position) {
        DownInfo info = mList.get(position);
        DownState state = info.getState();
        if (state == DownState.FINISH) {
            return ITEM_TYPE.TYPE_CACHE_DONE.ordinal();
        } else {
            return ITEM_TYPE.TYPE_CACHE_DOING.ordinal();
        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cb_done_edit;
        public ImageView iv_done_image;
        public TextView tv_done_title, tv_done_content, tv_done_size;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_done_edit=itemView.findViewById(R.id.cb_done_edit);
            iv_done_image=itemView.findViewById(R.id.iv_done_image);
            tv_done_title=itemView.findViewById(R.id.tv_done_title);
            tv_done_content=itemView.findViewById(R.id.tv_done_content);
            tv_done_size=itemView.findViewById(R.id.tv_done_size);
            cb_done_edit=itemView.findViewById(R.id.cb_done_edit);
        }
    }

    class ViewHolder2  extends RecyclerView.ViewHolder{
        public CheckBox cb_doing_edit;
        public ImageView iv_doing_image;
        public TextView tv_doing_title, tv_doing_content;

        public TextView tv_doing_state;//下载状态
        public ImageView iv_doing_state;//下载状态
        public ImageView iv_doing_conn;//连接中
        public ProgressBar progressbar_doing;//下载进度

        public ViewHolder2(View itemView) {
            super(itemView);
            cb_doing_edit=itemView.findViewById(R.id.cb_doing_edit);
            iv_doing_image=itemView.findViewById(R.id.iv_doing_image);
            tv_doing_title=itemView.findViewById(R.id.tv_doing_title);
            tv_doing_content=itemView.findViewById(R.id.tv_doing_content);
            tv_doing_state=itemView.findViewById(R.id.tv_doing_state);
            iv_doing_state=itemView.findViewById(R.id.iv_doing_state);
            iv_doing_conn=itemView.findViewById(R.id.iv_doing_conn);
            progressbar_doing=itemView.findViewById(R.id.progressbar_doing);

        }
    }
    public static enum ITEM_TYPE {
        TYPE_CACHE_DONE,
        TYPE_CACHE_DOING
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
        void setCBCheckListener(boolean isCheck);
        boolean  showNetDialog();
        void setDelLayoutGone();
    }

}
