package com.base.common.app.widget.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.hyl.houbasemodule.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houyongliang on 2018/5/3 13:23.
 * Function(功能):
 */

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {
    private List<String> list;
    private Context mContext;
    private List<Boolean> isChoosed = new ArrayList<>();
    private boolean isFirst = true;

    public StringAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.basemodule_item_flowlayout_label, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_label.setText(list.get(position));

        holder.itemView.setClickable(true);
        if (isChoosed.get(position)) {
            holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_checked);
            holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.colorWhite));

        } else {
            holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_unchecked);
            holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.color999999));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChoosed != null) {
//                    Toast.makeText(mContext, "clicked One:" + isChoosed.get(position), Toast.LENGTH_SHORT).show();
                    boolean isChecked = !isChoosed.get(position);
//                    Toast.makeText(mContext, "is clicked Two:" + isChoosed.get(position), Toast.LENGTH_SHORT).show();
//                    if(clicked.get(position)){
//                        holder.tv_label.setBackgroundResource(R.drawable.bg_radiobutton_policy_center_filter_checked);
//                    }else{
//                        holder.tv_label.setBackgroundResource(R.drawable.bg_radiobutton_policy_center_filter_unchecked);
//                    }
                    if (isChecked) {
                        holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_checked);
                        holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    } else {
                        holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_unchecked);
                        holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.color999999));
                    }
                    isChoosed.set(position, isChecked);
                }

            }
        });
    }

    /*定义方法获取 选中的内容*/
    public String getChooseContent() {
        StringBuilder sb = new StringBuilder();
        if (isChoosed.contains(true) && isChoosed.size() == list.size()) {
            for (int i = 0; i < isChoosed.size(); i++) {
                if (isChoosed.get(i)) {
                    sb.append(list.get(i) + ",");
                }

            }
            return sb.toString();
        } else {
            return "";
        }

    }

    @Override
    public int getItemCount() {
        if (isFirst && list != null) {
            isChoosed.clear();
            for (int i = 0; i < list.size(); i++) {
                isChoosed.add(false);
            }
            isFirst = false;
        }
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        FrameLayout fl;
        TextView tv_label;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_label = (TextView) itemView.findViewById(R.id.tv_label);
//            fl = (FrameLayout) itemView.findViewById(R.id.fl);

        }
    }
}
