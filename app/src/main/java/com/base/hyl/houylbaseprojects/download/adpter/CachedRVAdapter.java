package com.base.hyl.houylbaseprojects.download.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.base.common.image.GlideImgManager;
import com.base.common.log.MyToast;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo;

import java.util.List;

/**
 * Created by zcc on 2018/1/17.
 * 视频缓存adapter
 */

public class CachedRVAdapter extends RecyclerView.Adapter<CachedRVAdapter.ViewHolder> {

    private Context mContext;
    private boolean mIsEdit = false;
    private List<DownloadInfo> mList ;


    public CachedRVAdapter(Context mContext, List<DownloadInfo> list , boolean mIsEdit) {
        this.mContext = mContext;
        this. mList = list;
        this.mIsEdit=mIsEdit;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cache_done, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mList==null){
            return;
        }
         final DownloadInfo info = mList.get(position);
        if(info==null){
            return;
        }
        GlideImgManager.glideLoader(mContext, info.getImgUrl(), R.mipmap.iv_error, R.mipmap.iv_error, holder.iv_done_image, 1);
        holder.tv_done_title.setText(info.getTitle());
        holder.tv_done_content.setText(info.getDescription());
        /* 设置大小*/
        long countLen = info.getSize();
        float countLength = countLen / 1048576;
        if (countLength < 1) {
            countLength = countLen / 1024;
            holder.tv_done_size.setText(String.format("%.2f%s", countLength, "K"));
        } else {
            holder.tv_done_size.setText(String.format("%.2f%s", countLength, "M"));
        }
        /* 设置选择 状态*/
        holder.cb_done_edit.setChecked(info.isSelected());
        if(mIsEdit){
            holder.cb_done_edit.setVisibility(View.VISIBLE);
        }else{
            holder.cb_done_edit.setVisibility(View.GONE);
        }
        holder.cb_done_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                info.setSelected( isChecked);
                /* 设置是否全选的状态*/
                if(listener!=null){
                    listener.setCBQXCheckListener(getAllEditStatus());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener==null){
                    return;
                }
                if(!mIsEdit){
                    listener.setItemClickListener(v,info,position);
                }else{
//                    info.setSelected( !info.isSelected());
//                    notifyItemChanged(position);
//                    /* 设置是否全选的状态*/
//                    if(listener!=null){
//                        listener.setCBQXCheckListener(getAllEditStatus());
//                    }

                   // listener.setDeleteClickListener(v,info,position);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
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

    }

}
