package com.base.hyl.houylbaseprojects.xiazai.adpter;

import android.content.Context;
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
import com.base.hyl.houylbaseprojects.download.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.xiazai.db.DBController;
import com.base.hyl.houylbaseprojects.xiazai.db.DownInfo;
import com.base.widget.dialog.MyDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcc on 2018/1/17.
 * 视频缓存adapter
 */

public class CachedRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CACHE_DONE = 0;
    public static final int TYPE_CACHE_DOING = 1;//将type写入list每个item对象的type属性中
    private Context mContext;
    private boolean mIsEdit = false;
    private boolean mIsAllEdit = false;
    private List<DownloadInfo> mList ;
    private int mDelCount = 0;
    private AnimationDrawable iv_cache_conn_frame = null;
    private  DBController dbController;
    public CachedRVAdapter(Context mContext, List<DownloadInfo> list) {
        this.mContext = mContext;
        this. mList = list;
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
    public void update(List<DownloadInfo> list) {
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
                List<DownloadInfo> resultList = new ArrayList<>();
                for (DownloadInfo info : mList) {
                    DownInfo downInfo = dbController.convertDownInfo(info);
                    if (downInfo.isDelFlag()) {
                        dbController.delete(info);
                    } else {
                        resultList.add(info);
                    }
                }
                mList = resultList;
                notifyDataSetChanged();
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
            final DownInfo item =dbController.convertDownInfo(mList.get(position)) ;
            if(item!=null){
                GlideImgManager.glideLoader(mContext, item.getImageUrl(), R.mipmap.iv_error, R.mipmap.iv_error, vh01.iv_done_image, 1);
                vh01.tv_done_title.setText(item.getTitle());
                vh01.tv_done_content.setText(item.getDescription());
                long countLen = mList.get(position).getSize();
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
            }





        }






    }


    @Override
    public int getItemViewType(int position) {


        return ITEM_TYPE.TYPE_CACHE_DONE.ordinal();

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
