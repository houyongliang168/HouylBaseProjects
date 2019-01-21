package com.base.common.app.widget.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.common.Bean.StringAndBooleanBean;
import com.base.hyl.houbasemodule.Bean.StringAndBooleanBean;
import com.base.hyl.houbasemodule.R;

import java.util.List;

/**
 * Created by houyongliang on 2018/5/3 13:23.
 * Function(功能):
 */

public class StringBetterAdapter extends RecyclerView.Adapter<StringBetterAdapter.ViewHolder> {
    private List<StringAndBooleanBean> list;//该集合默认为 false
    private Context mContext;
    private List<String> oldList;
    public StringBetterAdapter(Context context, List<StringAndBooleanBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public StringBetterAdapter(Context context, List<StringAndBooleanBean> list, List<String> oldList) {
        this.mContext = context;
        this.list = list;
        this.oldList=oldList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.basemodule_item_flowlayout_label, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_label.setText(list.get(position).getName());

        holder.itemView.setClickable(true);
        if (list.get(position).isChoosed()) {
            holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_checked);
            holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.colorWhite));

        } else {
            holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_unchecked);
            holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.color999999));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = !list.get(position).isChoosed();
                list.get(position).setChoosed(isChecked);
                if (isChecked) {
                    holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_checked);
                    holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                } else {
                    holder.tv_label.setBackgroundResource(R.drawable.basemodule_shape_bg_flow_recyclerview_filter_unchecked);
                    holder.tv_label.setTextColor(mContext.getResources().getColor(R.color.color999999));
                }


            }
        });
    }

    /*定义方法获取 选中的内容*/
    public String getChooseContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChoosed()) {
                sb.append(list.get(i).getName() + ",");
            }
        }
        return sb.toString();
    }

    @Override
    public int getItemCount() {

        if(list!=null&&oldList!=null&&oldList.size()!=0){
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < oldList.size(); j++) {
                    if(list.get(i).getName().equals(oldList.get(j))){
                        list.get(i).setChoosed(true);
                    }
                }
            }
        }

        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_label;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_label = (TextView) itemView.findViewById(R.id.tv_label);

        }
    }
}
