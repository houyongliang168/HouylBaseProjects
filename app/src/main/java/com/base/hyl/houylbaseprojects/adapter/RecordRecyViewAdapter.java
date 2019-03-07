package com.base.hyl.houylbaseprojects.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.bean.StaffRecordBean;


import java.util.List;

/**
 * Created by houyongliang on 2018/01/09
 *  服务记录类的信息处理
 */

public class RecordRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<StaffRecordBean> list;//展示数据


    public RecordRecyViewAdapter(Context mContext, List<StaffRecordBean> list) {
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof OneViewHolder) {
            ((OneViewHolder) holder).tv_title.setText(getNoEmptyData(list.get(position).getTitle()));
            if(position==0){
                ((OneViewHolder) holder).view_up.setVisibility(View.GONE);
            }else{
                ((OneViewHolder) holder).view_up.setVisibility(View.VISIBLE);
            }


        } else if (holder instanceof TwoViewHolder) {
            if (list.get(position).getType() == StaffRecordBean.SUC_INFO) {//成功

                ((TwoViewHolder) holder).iv_two.setBackgroundResource(R.mipmap.add_staff_record_success);

            } else if (list.get(position).getType() == StaffRecordBean.FAIL_INFO) {//失败
                ((TwoViewHolder) holder).iv_two.setBackgroundResource(R.mipmap.add_staff_record_fail);
            } else {//标注
                ((TwoViewHolder) holder).iv_two.setBackgroundResource(R.drawable.shape_add_staff_record);
            }
            ((TwoViewHolder) holder).tv_title.setText(getNoEmptyData(list.get(position).getTitle()));
            ((TwoViewHolder) holder).tv_head.setText(getNoEmptyData(list.get(position).getTime()));

        } else if (holder instanceof ThreeViewHolder) {

        }


    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size());
    }


    @Override
    public int getItemViewType(int position) {
        int item_type = list.get(position).getType();
        return item_type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == StaffRecordBean.ONLE_TITLE) {
            return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_staff_time_one, parent, false));
        } else if (viewType == StaffRecordBean.SUC_INFO) {
            return new TwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_staff_time_two, parent, false));
        } else if (viewType == StaffRecordBean.FAIL_INFO) {
            return new TwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_staff_time_two, parent, false));
        } else if (viewType == StaffRecordBean.MARK_INFO) {
            return new TwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_staff_time_two, parent, false));
        } else {
            return new ThreeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_staff_time_three, parent, false));

        }


    }


    public static class OneViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        View view_up;//上面的view

        public OneViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);//标题
            view_up =  itemView.findViewById(R.id.view_up);//标题
        }
    }


    public static class TwoViewHolder extends RecyclerView.ViewHolder {
        TextView tv_head, tv_title;
        ImageView iv_two;

        public TwoViewHolder(View itemView) {
            super(itemView);
            tv_head = (TextView) itemView.findViewById(R.id.tv_head);//时间
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);//标题
            iv_two = (ImageView) itemView.findViewById(R.id.iv_two);//图标
        }

    }

    public static class ThreeViewHolder extends RecyclerView.ViewHolder {

        public ThreeViewHolder(View itemView) {
            super(itemView);
        }

    }

    private String getNoEmptyData(String data) {
        return TextUtils.isEmpty(data) ? "" : data;
    }

}


