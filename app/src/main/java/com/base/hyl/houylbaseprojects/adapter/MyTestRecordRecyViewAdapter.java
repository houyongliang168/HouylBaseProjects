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

public class MyTestRecordRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> list;//展示数据


    public MyTestRecordRecyViewAdapter(Context mContext, List<String> list) {
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof OneViewHolder) {
            ((OneViewHolder) holder).tv_title.setText(getNoEmptyData(list.get(position)));
            if(position==0){
                ((OneViewHolder) holder).view_up.setVisibility(View.GONE);
            }else{
                ((OneViewHolder) holder).view_up.setVisibility(View.VISIBLE);
            }
        }


    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size());
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_staff_time_one, parent, false));


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





    private String getNoEmptyData(String data) {
        return TextUtils.isEmpty(data) ? "" : data;
    }

}


