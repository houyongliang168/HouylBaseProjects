package com.base.hyl.houylbaseprojects.入口;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.入口.bean.AgentBean;
import java.util.List;


/**
 * Created by houyongliang on 2018/03/07.
 * <p>
 * recyclerview 的适配器
 */

public class AgentDetailRecyAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AgentBean> list;

    public AgentDetailRecyAdpter(Context context, List<AgentBean> list) {
        this.mContext = context;
        this.list = list;
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recy_agent, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MyViewHolder) holder).tv_agent_title.setText(TextUtils.isEmpty(list.get(position).getDetails()) ? "" : list.get(position).getDetails());
        if(list.get(position).getImg()>0){
            ((MyViewHolder) holder).iv_agent.setBackgroundResource(list.get(position).getImg());  
        }
        if (list != null && list.size() !=0 && list.get(position).isHasMsg() ) {
            ((MyViewHolder) holder).ivMsgGzl.setVisibility(View.VISIBLE);
        } else {
            ((MyViewHolder) holder).ivMsgGzl.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_agent;
        TextView tv_agent_title;
        ImageView ivMsgGzl;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_agent= itemView.findViewById(R.id.iv_agent);
            tv_agent_title= itemView.findViewById(R.id.tv_agent_title);
            ivMsgGzl= itemView.findViewById(R.id.iv_msg_gzl);
          
        }
    }


}


