package com.base.hyl.houylbaseprojects.http;

import com.base.hyl.houylbaseprojects.databing.msgBase.bean.MessageToPage;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.MsgBaseInfoBean;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.RequestBaseBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService  {

    /**
     * 获取msg 基本消息
     */
    //http://ip(xxxx):port/isales /msg/childMsg
    @GET("isales/msg/childMsg")
    Observable<RequestBaseBean<List<MsgBaseInfoBean>>> getMsgBaseChildMsg(
            @Query("staffNumber") String staffNumber,
            @Query("token") String token,
            @Query("msgType") String msgType,
            @Query("currentPage") String currentPage

    );

    /* 获取消息页面跳转信息     */
    @GET("attendance/message/messageToPage")
    Observable<RequestBaseBean<MessageToPage>> getMessageToPage(
            @Query("id") String currentPage,
            @Query("type") String msgType,
            @Query("token") String token
    );
}
