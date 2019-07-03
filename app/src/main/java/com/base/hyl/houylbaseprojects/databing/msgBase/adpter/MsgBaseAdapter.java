package com.base.hyl.houylbaseprojects.databing.msgBase.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.base.common.utils.FastUtils;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.databinding.ItemMsgBase1Binding;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.MsgBaseInfoBean;

import java.util.List;

/**
 * Created by zhaoyu on 2017/4/28.类说明：保全消息
 */
public class MsgBaseAdapter extends RecyclerView.Adapter<MsgBaseAdapter.ItemMsgBaseInfoBeanAdapterHolder> {
    private Context context;
    private List<MsgBaseInfoBean> list;
    private OnClickListener listener;

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }


    public MsgBaseAdapter(Context context, List<MsgBaseInfoBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public ItemMsgBaseInfoBeanAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return  new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_base, parent, false));

        ItemMsgBase1Binding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_msg_base_1, parent, false);
        return new ItemMsgBaseInfoBeanAdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemMsgBaseInfoBeanAdapterHolder holder, final int position) {
//        MsgBaseInfoBean info=   list.get(position);
////        holder.item_msg_time.setText(info.getCreateDate()+"");
////        holder.item_msg_title.setText(info.getTitle()+"");
////        holder.item_msg_contain.setText(info.getContent()+"");
////        holder.tv_lookall.setText("查看全文");
        holder.getBinding().setInfo(list.get(position));
        /* 设置点击时间和长按事件*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (FastUtils.isFastClick()) {//防止多次点击事件
                        listener.OnClickListener(v, position);
                    }
                }

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    return listener.OnLongClickListener(v, position);
                }
                return false;
            }
        });
    }


    //    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView item_msg_time,item_msg_title,item_msg_contain,tv_lookall;
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            item_msg_time= itemView.findViewById(R.id.item_msg_time);
//            item_msg_title= itemView.findViewById(R.id.item_msg_title);
//            item_msg_contain= itemView.findViewById(R.id.item_msg_contain);
//            tv_lookall= itemView.findViewById(R.id.tv_lookall);
//        }
//    }
    public class ItemMsgBaseInfoBeanAdapterHolder extends RecyclerView.ViewHolder {
        // TextView item_msg_time,item_msg_title,item_msg_contain,tv_lookall;
        public ItemMsgBase1Binding binding;

        public ItemMsgBaseInfoBeanAdapterHolder(ItemMsgBase1Binding binding) {
            //   super(itemView);
            super(binding.getRoot());
            this.binding = binding;
//            item_msg_time= itemView.findViewById(R.id.item_msg_time);
//            item_msg_title= itemView.findViewById(R.id.item_msg_title);
//            item_msg_contain= itemView.findViewById(R.id.item_msg_contain);
//            tv_lookall= itemView.findViewById(R.id.tv_lookall);

        }

        public ItemMsgBase1Binding getBinding() {
            return binding;
        }


    }
    public interface OnClickListener{
        void OnClickListener (View v, int possion);
        boolean OnLongClickListener (View v, int possion);
    }
}
