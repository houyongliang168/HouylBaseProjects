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
import com.base.hyl.houylbaseprojects.download.DownloadService;
import com.base.hyl.houylbaseprojects.download.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.xiazai.activity.MyDownloadListener;
import com.base.hyl.houylbaseprojects.xiazai.db.DBController;
import com.base.hyl.houylbaseprojects.xiazai.db.DownInfo;
import com.base.hyl.houylbaseprojects.xiazai.utils.FileUtil;
import com.base.widget.dialog.MyDialog;

import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.base.hyl.houylbaseprojects.download.domain.DownloadInfo.STATUS_COMPLETED;
import static com.base.hyl.houylbaseprojects.download.domain.DownloadInfo.STATUS_REMOVED;
import static com.base.hyl.houylbaseprojects.download.domain.DownloadInfo.STATUS_WAIT;

/**
 * Created by zcc on 2018/1/17.
 * 视频缓存adapter
 */

public class CacheingRVAdapter extends RecyclerView.Adapter<CacheingRVAdapter.ViewHolder2> {
    public static final int TYPE_CACHE_DONE = 0;
    public static final int TYPE_CACHE_DOING = 1;//将type写入list每个item对象的type属性中
    private Context mContext;
    private boolean mIsEdit = false;
    private boolean mIsAllEdit = false;
    private List<DownInfo> mList ;
    private int mDelCount = 0;
    private AnimationDrawable iv_cache_conn_frame = null;

    private DBController dbController;
    private final DownloadManager downloadManager;
    public CacheingRVAdapter(Context mContext, List<DownInfo> list) {
        this.mContext = mContext;
        this. mList = list;
        downloadManager = DownloadService.getDownloadManager(mContext.getApplicationContext());
        try {
            dbController=DBController.getInstance(mContext);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        downloadManager.pauseAll();
        notifyDataSetChanged();
    }

    /**
     * 全部开始
     */
    public void allStart() {

        downloadManager.resumeAll();
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
                        DownloadInfo infoById = dbController.findDownloadedInfoById(info.getLiveId());
                        dbController.delete(infoById);
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
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cache_doing, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder2 holder, final int position) {
        holder.bindData(mList.get(position), position, mContext);
    }



public void  addData( DownInfo data){
notifyDataSetChanged();


}






    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE.TYPE_CACHE_DOING.ordinal();
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

    class ViewHolder2  extends RecyclerView.ViewHolder {
        public CheckBox cb_doing_edit;
        public ImageView iv_doing_image;
        public TextView tv_doing_title, tv_doing_content;

        public TextView tv_doing_state;//下载状态
        public ImageView iv_doing_state;//下载状态
        public ImageView iv_doing_conn;//连接中
        public ProgressBar progressbar_doing;//下载进度
        private DownloadInfo downloadInfo;
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

        }

        private void notifyDownloadStatus(DownloadInfo downloadInfo) {
            if(downloadInfo!=null){
                if (downloadInfo.getStatus() == STATUS_REMOVED) {

                    dbController.deleteDownInfo(downloadInfo.getId());

                }
            }


        }
        public void bindData(final DownInfo data, int position, final Context context) {
            if(data==null){
                return;
            }

            downloadInfo=downloadManager.getDownloadById(data.getLiveId());
            iv_cache_conn_frame = (AnimationDrawable) iv_doing_conn.getBackground();
            GlideImgManager.glideLoader(mContext, data.getImageUrl(), R.mipmap.iv_error, R.mipmap.iv_error, iv_doing_image, 1);
            tv_doing_title.setText(data.getTitle());
            tv_doing_content.setText(data.getDescription());
            /* 基本数据处理*/
            if (mIsEdit) {
                cb_doing_edit.setVisibility(View.VISIBLE);
                if (mIsAllEdit) {
                    cb_doing_edit.setChecked(true);
                    if(listener!=null){
                        listener.setCBCheckListener(true);
                    }

                } else {
                    cb_doing_edit.setChecked(false);
                    if(listener!=null){
                        listener.setCBCheckListener(false);
                    }
                }

                cb_doing_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        data.setDelFlag(isChecked);
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
                cb_doing_edit.setVisibility(View.GONE);
            }

            if(downloadInfo!=null){
                downloadInfo.setDownloadListener(new MyDownloadListener(new SoftReference(CacheingRVAdapter.ViewHolder2.this)) {
                    @Override
                    public void onRefresh() {
                        if (getUserTag() != null && getUserTag().get() != null) {
                            ViewHolder2 viewHolder = (ViewHolder2) getUserTag().get();
                            viewHolder.refresh( data);
                        }

                    }
                });
            }
            refresh(data);
            iv_doing_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {// 设置点击事件
                    if (downloadInfo != null) {

                        switch (downloadInfo.getStatus()) {
                            case DownloadInfo.STATUS_NONE:
                            case DownloadInfo.STATUS_PAUSED:
                            case DownloadInfo.STATUS_ERROR:

                                //resume downloadInfo
                                downloadManager.resume(downloadInfo);
                                break;

                            case DownloadInfo.STATUS_DOWNLOADING:
                            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                            case STATUS_WAIT:
                                //pause downloadInfo
                                downloadManager.pause(downloadInfo);
                                break;
                            case STATUS_COMPLETED:

                              //  downloadManager.remove(downloadInfo);
                                break;
                        }
                    } else {

                        downloadInfo = new DownloadInfo.Builder().setUrl(data.getUrl())
                                .setPath(data.getSavePath())
                                .setId(data.getLiveId())
                                .build();
                        downloadInfo
                                .setDownloadListener(new MyDownloadListener(new SoftReference(CacheingRVAdapter.ViewHolder2.this)) {

                                    @Override
                                    public void onRefresh() {
                                        notifyDownloadStatus(downloadInfo);

                                        if (getUserTag() != null && getUserTag().get() != null) {
                                            ViewHolder2 viewHolder = (ViewHolder2) getUserTag().get();
                                            viewHolder.refresh(data);
                                        }
                                    }
                                });
                        downloadManager.download(downloadInfo);
                        dbController.createOrUpdate(data);
                    }

                }
            });
    }
        public void refresh(DownInfo data) {//刷新方法
            if (downloadInfo == null) {
                defaultStatusUI();
            } else {

                switch (downloadInfo.getStatus()) {
                    case DownloadInfo.STATUS_NONE:
                        tv_doing_state.setText("下载停止");
                        iv_doing_conn.setVisibility(View.GONE);
                        progressbar_doing.setVisibility(View.VISIBLE);
                        tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                        iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                        break;
                    case DownloadInfo.STATUS_PAUSED:
                        tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                        tv_doing_state.setText("已暂停");
                        iv_doing_conn.setVisibility(View.GONE);
                        progressbar_doing.setVisibility(View.VISIBLE);
                        iv_doing_state.setImageResource(R.mipmap.cache_doing_down);
                        break;
                    case DownloadInfo.STATUS_ERROR:
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
                        long countLenF = downloadInfo.getSize();
                        long readLenF = downloadInfo.getProgress();
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
                        break;
                    case STATUS_COMPLETED:
                        tv_doing_state.setText("下载完成");
                        mList.remove(data);
                        notifyDataSetChanged();
                        break;
                    case STATUS_REMOVED:
                        defaultStatusUI();
                        break;
                    case STATUS_WAIT:
                        iv_cache_conn_frame.start();
                        break;
                }
            }
        }

        private void defaultStatusUI() {
            iv_cache_conn_frame.start();
            progressbar_doing.setProgress(0);
            tv_doing_state.setTextColor(Color.parseColor("#f51706"));
            tv_doing_state.setText("点击下载");
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
