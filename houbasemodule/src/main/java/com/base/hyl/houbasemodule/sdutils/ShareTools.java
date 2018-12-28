package com.base.hyl.houbasemodule.sdutils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.taikanglife.isalessystem.App;
import com.taikanglife.isalessystem.BuildConfig;
import com.taikanglife.isalessystem.Constant;
import com.taikanglife.isalessystem.R;
import com.taikanglife.isalessystem.TCAgent_EventId;
import com.taikanglife.isalessystem.common.utils.MyLog;
import com.taikanglife.isalessystem.common.utils.TCAgentUtils;
import com.taikanglife.isalessystem.common.utils.WeChatShareUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by houyongliang on 2017/6/14.
 */

public class ShareTools {
    //    private static String baseUrl = "http://isaleuat.life.taikang.com/sys/static/liveShare/pages/live.html?liveId=";
//    private static String baseUrl = "https://isales.taikang.com/static/liveShare/pages/live.html?liveId=";
    private static String baseUrl = BuildConfig.VOD_URL;

    /**
     * 分享对外提供的工具
     *
     * @param activity     活动
     * @param url          需要拼接的字段 整合为 http://isaleuat.life.taikang.com/sys/static/liveShare/pages/live.html?liveId
     * @param title        分享标题
     * @param thumb        分享图片
     * @param description  分享描述
     * @param isFromBanner 判断是否来自banner ,统计的时候用
     * @param isLive       判断是否是直播 ,统计的时候用 1 直播  0 点播
     */
    public static void showWeChatShare(final Activity activity, String url, final String title, final Bitmap thumb, final String description, final Boolean isFromBanner, final String isLive) {
        final String liveId = url;
        url = baseUrl + url;
        final String finalUrl = shareToWx(url);
        View view = activity.getLayoutInflater().inflate(R.layout.item_share,
                null);
    /*获取视图*/
        ImageButton img_weixin_logo = (ImageButton) view.findViewById(R.id.img_weixin_logo);
        ImageButton img_pengyouquan_logo = (ImageButton) view.findViewById(R.id.img_pengyouquan_logo);
        Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
        final Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        img_weixin_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeChatShareUtil.getInstance(activity).shareUrl(finalUrl, title, thumb, description, SendMessageToWX.Req.WXSceneSession);
                App.shareBean.setType("直播分享");

                App.shareBean.setId(liveId);
                App.shareBean.setTitle(title);
                App.shareBean.setShareType("微信好友");
            /*返回页面 dialog 消失*/
                if (dialog != null) {
                    dialog.dismiss();
                }

                if (isFromBanner) {
                    TCAgent.onEvent(activity, Constant.EVENT_MAIN, "直播轮播图转发1（微信）");
                }


               /*插码*/
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("liveId",liveId);
                if ("1".equals(isLive)) {
                    /**
                     * 直播转发1（微信）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_2",map);
                }
                if ("0".equals(isLive)) {
                    /**
                     * 点播转发1（微信）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_17",map);
                }





            }
        });
        img_pengyouquan_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeChatShareUtil.getInstance(activity).shareUrl(finalUrl, title, thumb, description, SendMessageToWX.Req.WXSceneTimeline);
                App.shareBean.setType("直播分享");

                App.shareBean.setId(liveId);
                App.shareBean.setTitle(title);
                App.shareBean.setShareType("微信朋友圈");
                   /*返回页面 dialog 消失*/
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (isFromBanner) {
                    TCAgent.onEvent(activity, Constant.EVENT_MAIN, "直播轮播图转发1（朋友圈））");
                }
                /*插码*/
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("liveId",liveId);
                if ("1".equals(isLive)) {
                    /**
                     * 直播转发2（朋友圈）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_3",map);
                }
                if ("0".equals(isLive)) {
                    /**
                     * 点播转发2（朋友圈）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_18",map);
                }



            }
        });

    }

    /**
     * 分享对外提供的工具
     *
     * @param activity     活动
     * @param url          需要拼接的字段 整合为 http://isaleuat.life.taikang.com/sys/static/liveShare/pages/live.html?liveId
     * @param title        分享标题
     * @param thumb        分享图片
     * @param description  分享描述
     * @param isFromBanner 判断是否来自banner ,统计的时候用
     * @param isLive       判断是否是直播 ,统计的时候用 1 直播  0 点播
     */
    public static void showFullWeChatShare(final Activity activity, String url, final String title, final Bitmap thumb, final String description, final Boolean isFromBanner,final String isLive) {
        final String liveId1 = url;
        url = baseUrl + url;
        final String finalUrl = shareToWx(url);
        View view = activity.getLayoutInflater().inflate(R.layout.item_full_share,
                null);
    /*获取视图*/
        ImageButton img_weixin_logo = (ImageButton) view.findViewById(R.id.img_weixin_logo);
        ImageButton img_pengyouquan_logo = (ImageButton) view.findViewById(R.id.img_pengyouquan_logo);
        Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
        final Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        img_weixin_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeChatShareUtil.getInstance(activity).shareUrl(finalUrl, title, thumb, description, SendMessageToWX.Req.WXSceneSession);
                App.shareBean.setType("直播分享");

                App.shareBean.setId(liveId1);
                App.shareBean.setTitle(title);
                App.shareBean.setShareType("微信好友");
            /*返回页面 dialog 消失*/
                if (dialog != null) {
                    dialog.dismiss();
                }

                if (isFromBanner) {
                    TCAgent.onEvent(activity, Constant.EVENT_MAIN, "直播轮播图转发1（微信）");
                }

                /*插码*/
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("liveId",liveId1);
                if ("1".equals(isLive)) {
                    /**
                     * 直播转发1（微信）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_2",map);
                }
                if ("0".equals(isLive)) {
                    /**
                     * 点播转发1（微信）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_17",map);
                }


            }
        });
        img_pengyouquan_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeChatShareUtil.getInstance(activity).shareUrl(finalUrl, title, thumb, description, SendMessageToWX.Req.WXSceneTimeline);
                App.shareBean.setType("直播分享");

                App.shareBean.setId(liveId1);
                App.shareBean.setTitle(title);
                App.shareBean.setShareType("微信朋友圈");
                   /*返回页面 dialog 消失*/
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (isFromBanner) {
                    TCAgent.onEvent(activity, Constant.EVENT_MAIN, "直播轮播图转发1（朋友圈））");
                }

                /*插码*/
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("liveId",liveId1);
                if ("1".equals(isLive)) {
                    /**
                     * 直播转发2（朋友圈）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_3",map);
                }
                if ("0".equals(isLive)) {
                    /**
                     * 点播转发2（朋友圈）
                     */
                    TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_18",map);
                }


            }
        });

    }

    private static String shareToWx(String url) {
        MyLog.wtf("share=", "base64" + Base64.encodeToString(url.getBytes(), 0));
        String bUrl = "";
        if(App.ISDEBUG){//测试
            bUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdbbe2c84a6e68304" +
                    "&redirect_uri=http://wxpt-t.taikang.com/tkmap/wechat/oauth2/redirect/wxdbbe2c84a6e68304?other=" +
                    Base64.encodeToString(url.getBytes(), 0) + "&response_type=code&scope=snsapi_base&state=redict&connect_redirect=1#wechat_redirect";
        }else{//生产
            bUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2f763d09aa9ca523" +
                    "&redirect_uri=http://wxpt.taikang.com/tkmap/wechat/oauth2/redirect/wx2f763d09aa9ca523?other=" +
                    Base64.encodeToString(url.getBytes(), 0)+ "&response_type=code&scope=snsapi_base&state=redict&connect_redirect=1#wechat_redirect";
        }

        MyLog.wtf("share", "shareUrl=" + bUrl);
        return bUrl;


    }


}
