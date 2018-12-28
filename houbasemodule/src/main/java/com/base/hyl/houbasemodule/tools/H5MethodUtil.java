package com.base.hyl.houbasemodule.tools;

import com.rsq.function.tkimagepicker.dao.ImagePicker;
import com.rsq.function.tkimagepicker.loader.GlideImageLoader;
import com.rsq.function.tkimagepicker.view.CropImageView;
import com.taikanglife.isalessystem.App;
import com.taikanglife.isalessystem.common.utils.MyLog;
import com.taikanglife.isalessystem.interacth5.token.Paramaters;
import com.taikanglife.isalessystem.interacth5.token.TokenUtils;

import java.util.ArrayList;

/**
 * Created by REN SHI QIAN on 2018/1/16.
 */

public class H5MethodUtil {
    /**
     * 初始化拍照的ImagePicker
     * @param limitNum
     * @param showCamera
     * @param crop
     */
    public static void initImagePicker(int limitNum, boolean showCamera, boolean crop) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(showCamera);                      //显示拍照按钮
        imagePicker.setCrop(crop);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(limitNum);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }



    /**
     * 分享的url添加token
     * @param url
     * @return
     */
    public static String addTokenToShareUrl(String url,String secret) {
        if ("".equals(url) || null == url) {
            return "";
        } else {
            ArrayList<Paramaters> paramatersList = new ArrayList<>();
            // userid：渠道_分公司码_工号
            String userid = App.staffBuidler;
            Paramaters p1 = null;
            if (null == userid || "".equals(userid)) {
            } else {
                p1 = new Paramaters("userid", userid);
            }
            if (null == p1) {
            } else {
                paramatersList.add(p1);
            }

            // usertype：ag(个险)、bk(银保)、ha(经代)、staff(内勤)、dx (电销)、F1(高客)
            String usertype = null;
            switch (App.perInfo.getUserInfo().getChannel()) {
                case "1":
                    usertype = "ha";
                    break;
                case "2":
                    usertype = "ag";
                    break;
                case "3":
                    usertype = "bk";
                    break;
                case "4":
                    usertype = "dx";
                    break;
                case "5":
                    usertype = "f1";
                    break;
            }
            Paramaters p2 = null;
            if (null == usertype || "".equals(usertype)) {
            } else {
                p2 = new Paramaters("usertype", usertype);
            }
            if (null == p2) {
            } else {
                paramatersList.add(p2);
            }

            // channel：来源渠道，isales-android、isales-ios
            String channel = "isales-android";
            Paramaters p3 = new Paramaters("channel", channel);
            paramatersList.add(p3);

            // state：固定用redict
            String state = "redict";
            Paramaters p4 = new Paramaters("state", state);
            paramatersList.add(p4);

            // timestamp：linux时间戳，long类型
            long timestamp = System.currentTimeMillis();
            Paramaters p5 = new Paramaters("timestamp", "" + timestamp);
            paramatersList.add(p5);

            // secret：
            Paramaters p6 = null;
            if (null == secret || "".equals(secret)) {
            } else {
                p6 = new Paramaters("secret", secret);
            }
            if (null == p6) {
            } else {
                paramatersList.add(p6);
            }


            // token：计算生成
            String token = TokenUtils.getToken(paramatersList);

            // shareUrl: 带token的分享链接
            String shareUrl;
            if (url.contains("token")) {
                shareUrl = TokenUtils.getOrderUrl(url, paramatersList);
            } else {
                shareUrl = TokenUtils.getOrderUrl(url, paramatersList) + "&token=" + token;
            }
            MyLog.wtf("H5分享url", shareUrl);
            return shareUrl;
        }
    }
}
