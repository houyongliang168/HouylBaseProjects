package com.base.hyl.houylbaseprojects.rukou.present;

import com.base.hyl.houylbaseprojects.rukou.bean.AgentBean;
import com.base.hyl.houylbaseprojects.rukou.contract.IAgentContract;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houyongliang on 2018/3/7 16:27.
 * Function(功能):
 */

public class AgentPresenter extends IAgentContract.AgentPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void initData() {
        if (mView != null) {

            mView.setAdapter(getDetailsList());
        }
    }



    public List<AgentBean> getDetailsList() {
        List<AgentBean> list = new ArrayList<>();
        AgentBean agentBean;
        agentBean = new AgentBean();
        agentBean.setDetails("测试AIDL");
        agentBean.setKey("10001");
        list.add(agentBean);

        agentBean = new AgentBean();
        agentBean.setKey("10002");
        agentBean.setDetails("测试其他");


        list.add(agentBean);
        return list;
    }
  
}
