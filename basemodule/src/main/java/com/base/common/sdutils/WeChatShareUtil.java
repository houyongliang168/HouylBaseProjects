package com.base.common.sdutils;

import android.content.Context;
import android.graphics.Bitmap;

import com.base.hyl.houbasemodule.MyLog;
import com.base.hyl.houbasemodule.constant.Constant;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WeChatShareUtil {

    //从官网申请的合法appId
    public static final String APP_ID = Constant.APP_ID;
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    /*scene*/
    /*scene  参数传入   SendMessageToWX.Req.WXSceneSession 微信本身  SendMessageToWX.Req.WXSceneTimeline 非本身*/
    //IWXAPI是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private Context context;
    public static WeChatShareUtil weChatShareUtil;
    private String tag;
    private byte[] bytes;

    public static WeChatShareUtil getInstance(Context context) {
        if (weChatShareUtil == null) {
            weChatShareUtil = new WeChatShareUtil();
        }
        if (weChatShareUtil.api != null) {
            weChatShareUtil.api.unregisterApp();
        }
        weChatShareUtil.context = context;
        weChatShareUtil.regToWx();
        return weChatShareUtil;
    }

    public static WeChatShareUtil getInstance(Context context, String tag) {
        if (weChatShareUtil == null) {
            weChatShareUtil = new WeChatShareUtil();
        }
        if (weChatShareUtil.api != null) {
            weChatShareUtil.api.unregisterApp();
        }
        weChatShareUtil.context = context;
        weChatShareUtil.tag = tag;
        weChatShareUtil.regToWx();
        return weChatShareUtil;
    }

    public IWXAPI getAPI() {
        if (api != null)
            return api;
        return null;
    }

    //注册应用id到微信
    private void regToWx() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        //将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    /**
     * 分享文字到朋友圈或者好友
     *
     * @param text  文本内容
     * @param scene 分享方式：好友还是朋友圈
     */
    public boolean shareText(String text, int scene) {
        //初始化一个WXTextObject对象，填写分享的文本对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        return share(textObj, text, scene);
    }

    /**
     * 分享图片到朋友圈或者好友
     *
     * @param bmp   图片的Bitmap对象
     * @param scene 分享方式：好友还是朋友圈
     */
    public boolean sharePic(Bitmap bmp, int scene) {
        //初始化一个WXImageObject对象
        WXImageObject imageObj = new WXImageObject(bmp);
        //设置缩略图
        Bitmap thumb = Bitmap.createScaledBitmap(bmp, 60, 60, true);
        //此处如果调用recycle()方法会导致崩溃
        return share(imageObj, thumb, scene);
    }

    /**
     * 分享网页到朋友圈或者好友，视频和音乐的分享和网页大同小异，只是创建的对象不同。
     * 详情参考官方文档：
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317340&token=&lang=zh_CN
     *
     * @param url         网页的url
     * @param title       显示分享网页的标题
     * @param description 对网页的描述
     * @param scene       分享方式：好友还是朋友圈
     */
    public boolean shareUrl(String url, String title, Bitmap thumb, String description, int scene) {
        //初试话一个WXWebpageObject对象，填写url
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = url;
        return share(webPage, title, thumb, description, scene);
    }

    /**
     * 使用：WeChatShareUtil.getInstance(this).ShareFile("storage/emulated/0/share.pdf","share.pdf","pdf pdf pdf");
     * 分享文件
     */
    public void ShareFile(String filePath, String title, String description) {
//        InputStream abpath = getClass().getResourceAsStream("/assets/"+fileName);
        WXFileObject fileObj = new WXFileObject();
        fileObj.setContentLengthLimit(1024 * 1024 * 10);
//        filePath = "storage/emulated/0"+filePath;
        MyLog.wtf("zcc", filePath + title + description);
        fileObj.filePath = filePath;
//        fileObj.fileData = inputStreamToByte(filePath);//文件路径
//        try {
//            fileObj.fileData =InputStreamToByte(abpath);//文件路径
//            //fileObj.filePath = new String(InputStreamToByte(abpath));
//            MyLog.wtf("zcc:file share",fileObj.type()+":"+abpath.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //fileObj.filePath = filePath + fileName ;

        //使用媒体消息分享
        WXMediaMessage msg = new WXMediaMessage(fileObj);
        msg.title = title;
        msg.description = description;
        msg.mediaObject = fileObj;

        //发送请求
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //创建唯一标识
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    /**
     * 分享小程序到微信好友
     */
    public void shareSmallProgram(String webpageUrl, String userName, String path, String title, String description, Bitmap bitmap) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = webpageUrl; // 兼容低版本的网页链接
        //miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = userName;     // 小程序原始id
        miniProgramObj.path = path;            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = description;               // 小程序消息desc
       // bytes = BitmapQuality.compressImage(context , imageUrl);
        //msg.setThumbImage(mBitmap);  // 小程序消息封面图片，小于128k
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        api.sendReq(req);
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    /**
     * 将输入的流转换为byte数组
     *
     * @return byte数组
     */
    public byte[] inputStreamToByte(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private boolean share(WXMediaMessage.IMediaObject mediaObject, Bitmap thumb, int scene) {
        return share(mediaObject, null, thumb, null, scene);
    }

    private boolean share(WXMediaMessage.IMediaObject mediaObject, String description, int scene) {
        return share(mediaObject, null, null, description, scene);
    }

    private boolean share(WXMediaMessage.IMediaObject mediaObject, String title, Bitmap thumb, String description, int scene) {
        //初始化一个WXMediaMessage对象，填写标题、描述
        WXMediaMessage msg = new WXMediaMessage(mediaObject);
        if (title != null) {
            msg.title = title;
        }
        if (description != null) {
            msg.description = description;
        }
        if (thumb != null) {
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 30, 30, true);
            msg.thumbData = bmpToByteArray(thumb, false);
        }
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = scene;
        return api.sendReq(req);


    }

    //判断是否支持转发到朋友圈
    //微信4.2以上支持，如果需要检查微信版本支持API的情况， 可调用IWXAPI的getWXAppSupportAPI方法,0x21020001及以上支持发送朋友圈
    public boolean isSupportWX() {
        int wxSdkVersion = api.getWXAppSupportAPI();
        return wxSdkVersion >= TIMELINE_SUPPORTED_VERSION;
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > 32768 && options != 10) {
            output.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);
        }
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

