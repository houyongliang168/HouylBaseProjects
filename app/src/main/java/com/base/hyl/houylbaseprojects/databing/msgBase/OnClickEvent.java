package com.base.hyl.houylbaseprojects.databing.msgBase;

import android.app.Activity;

import com.base.common.log.MyToast;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.MsgBaseInfoBean;


public class OnClickEvent {
    public static final  int MSG_APPROVAL=14;//待审批
    public static final  int MSG_WANDER=15;//流转中

    /* 普通点击*/
    public static void onClick(Activity activity, int type, MsgBaseInfoBean bean, int possion){

        if(bean==null){
            MyToast.showShort("很抱歉,数据加载失败,请退出后重试..");
            return;
        }

        if(type==MSG_APPROVAL){
            checkMessageToPage(activity,type,bean);
        }else if(type==MSG_WANDER){
//


        }

    }
    /*长按点击 非删除的*/
    public static void onLongClick(Activity activity, int type, MsgBaseInfoBean bean, int possion){
        if(type==MSG_APPROVAL){

        }else if(type==MSG_WANDER){


        }
    }


    //由接口返回--消息页面跳转信息
    private static void checkMessageToPage(final Activity activity, int type, final MsgBaseInfoBean bean) {
        String aGoto = bean.getInternalGoto();
        final String[] aGotos = aGoto.split(",");
        if (aGotos.length <= 1) {
            MyToast.showShort("数据异常,请退出后重试!");
            return;
        }

//        HttpUtil.init(HttpUtil.sspBasicHttpUtils().getMessageToPage(aGotos[1] + "", aGotos[0] + "", App.TOKEN), new BaseSubcriber().setOwnListener(new BaseSubcriber.SubcriberListener<MessageToPage>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                MyToast.showShort("数据异常,请退出后重试!");
//            }
//
//            @Override
//            public void onSuccess(MessageToPage info) {
//                if (info != null && activity != null && !activity.isFinishing()) {
//                    if (info.isToApprovePage()) {//审批页面
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFail(String info) {
//                if (info != null && activity != null && !activity.isFinishing()) {
//                    if (activity instanceof MsgBaseActivity) {
//                        ((MsgBaseActivity) activity).showErrorDialog(info + "");
//                    }
//                }
//            }
//        }));


    }}
