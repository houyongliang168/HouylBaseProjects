package com.base.hyl.houylbaseprojects.入口;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.common.base.CoreBaseFragment;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.aidl.BookManagerActivity;
import com.base.hyl.houylbaseprojects.入口.bean.AgentBean;
import com.base.hyl.houylbaseprojects.入口.contract.IAgentContract;
import com.base.hyl.houylbaseprojects.入口.present.AgentPresenter;
import com.base.widget.recycler.RecyclerViewClickListener;

import java.util.List;

/**
 * 我的代办
 * Created by houyl on 2018/03/07.
 */

public class MeEntryFragment extends CoreBaseFragment<AgentPresenter> implements IAgentContract.IAgentView {


    private RecyclerView recycl_agent;
    private AgentDetailRecyAdpter recy_adpter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_me_agent;
    }

    @Override
    public void loadData() {
        mPresenter.initData();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        recycl_agent = (RecyclerView) view.findViewById(R.id.recycl_agent);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setAdapter(final List<AgentBean> list) {
        if (list == null) {
            return;
        }
        if (recy_adpter == null) {
            recycl_agent.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycl_agent.addItemDecoration(new DividerItemDecoration(getActivity(),
                    R.drawable.shape_agent, DividerItemDecoration.VERTICAL_LIST));
        }

        recy_adpter = new AgentDetailRecyAdpter(getContext(), list);
        recycl_agent.setAdapter(recy_adpter);
        recycl_agent.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), recycl_agent, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positions) {
                /*设置 跳转*/
                if (list == null) {
                    return;
                }
                switch (list.get(positions).getKey()) {
                    case "10001":/*10001 代办保全*/
                        Intent intent_one = new Intent(getContext(), BookManagerActivity.class);
                        intent_one.putExtra("TAG", "1");//设置标识 展示不同 的抬头
                        startActivity(intent_one);
                  
                        break;
                    case "10002":/*10002 团队生日提醒*/
                     
                        break;
                    default:
                        break;

                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

    }

}
