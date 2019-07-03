package com.base.hyl.houylbaseprojects.databing.msgBase;


import com.base.common.http.HttpUtil;
import com.base.common.log.MyLog;
import com.base.hyl.houylbaseprojects.Constant;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.MsgBaseInfoBean;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.RequestBaseBean;
import com.base.hyl.houylbaseprojects.http.HttpUtilsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by houyongliang on 2018/3/7 16:27.
 * Function(功能):
 */

public class MsgBasePresenter extends IMsgBaseContract.AMsgBasePresenter {

    public MsgBasePresenter() {

    }

    @Override
    public void onStart() {

    }


    @Override
    public void httpRequest(String type, String page, String num) {
      if(mView!=null){
          mView.showDialog();
      }
        HttpUtil.init(HttpUtilsService.getApiServie().getMsgBaseChildMsg("staffbuilder", "token", type, page), new Subscriber<RequestBaseBean<List<MsgBaseInfoBean>>>() {
            @Override
            public void onCompleted() {
                MyLog.wtf("MsgBasePresenter","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
              MyLog.wtf("MsgBasePresenter","Throwable +"+e.getMessage());
              if(mView!=null){
                  mView. setReflashStatus(false);
                  mView.dissmissDialog();
              }
            }

            @Override
            public void onNext(RequestBaseBean<List<MsgBaseInfoBean>> info) {
                MyLog.wtf("hyl","httpRequest info :"+info.toString());
                if(mView==null){
                    return;
                }
                mView.dissmissDialog();
                switch (info.getRspCode()) {
                    case Constant.GET_DATA_SUCCESS:
                        mView. setReflashStatus(true);
                        mView.displayData(info);
                        break;
                    default:
                        mView. setReflashStatus(false);
                        mView.showErrorDialog(info.getRspDesc()+"");
                        break;
                }
            }
        });
    }


    @Override
    public void deleteMsg(String msgIds, String type, String staffNum, final int possion) {
        Map<String, String> params = new HashMap<>();
        params.put("msgIds", msgIds+"");
        params.put("staffNumber",staffNum);
        params.put("msgType", type);



    }

}
