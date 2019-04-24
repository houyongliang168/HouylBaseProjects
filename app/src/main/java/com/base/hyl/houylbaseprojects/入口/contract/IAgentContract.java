package com.base.hyl.houylbaseprojects.入口.contract;


import com.base.common.base.CoreBasePresenter;
import com.base.common.base.ICoreBaseControl;
import com.base.hyl.houylbaseprojects.入口.bean.AgentBean;

import java.util.List;

/**
 * Created by houyongliang on 2018/3/7.
 */

public interface IAgentContract {
    interface IAgentView extends ICoreBaseControl.ICoreBaseView {
        /*设置适配器*/
        void setAdapter(List<AgentBean> list);


    }

    interface IAgentModel extends ICoreBaseControl.ICoreBaseModel {

    }

    abstract class AgentPresenter extends CoreBasePresenter<IAgentModel, IAgentView> {
        /*初始化数据*/
        public abstract void initData();

    }
}
