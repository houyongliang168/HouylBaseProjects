package com.base.hyl.houbasemodule.tools;

import com.baidu.android.common.security.MD5Util;
import com.orhanobut.logger.Logger;
import com.taikanglife.isalessystem.common.httpmanager.SSLUtils;
import com.taikanglife.isalessystem.common.httpmanager.Tls12SocketFactory;
import com.taikanglife.isalessystem.common.httpmanager.apiservice.ApiService;
import com.taikanglife.isalessystem.common.utils.MyLog;
import com.taikanglife.isalessystem.common.utils.TimeUtil;
import com.taikanglife.isalessystem.module.policy.BitmapAsyncTask;
import com.taikanglife.isalessystem.module.policy.bean.IdentityOCRBean;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.taikanglife.isalessystem.BuildConfig.OCR_BUSINESSCODE;
import static com.taikanglife.isalessystem.BuildConfig.OCR_SECRET_KEY;
import static com.taikanglife.isalessystem.BuildConfig.OCR_URL;

/**
 * Created by liuyang249 on 2018/11/7.
 */

public class OCRUtils {
    Retrofit retrofit;
    String faceResult;

    /**
     * 身份证正面识别
     *
     * @param filePath
     */
    public static void checkIdentityCardFront(final String filePath, final Subscriber<IdentityOCRBean> subscriber) {
        final BitmapAsyncTask bitmapAsyncTask = new BitmapAsyncTask(new BitmapAsyncTask.ONListener() {
            @Override
            public void getBase64Listener(String base64Image) {
                Map<String, Object> map = new HashMap<>();
                map.put("modelCode", "ID001");
                map.put("param", "F0");
                String randomCode = TimeUtil.getTimeOther(System.currentTimeMillis()).replace(" ", "-");
                map.put("randomCode", randomCode);
                MyLog.wtf("time", randomCode);
                map.put("verifyImage", base64Image);
                map.put("businessCode", OCR_BUSINESSCODE);
                //businessCode+secretKey+modelCode+randomCode
                String s = OCR_BUSINESSCODE + OCR_SECRET_KEY + "ID001" + randomCode;
                map.put("encryption", MD5Util.toMd5(s.getBytes(), false));
                //map.put("userInfo", false);
                Logger.e("map :" + map.toString());

                //配置SSL
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("TLS");
                    try {
                        sslContext.init(null, null, null);
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());

                createHttpClicent(subscriber,map);


            }

        });
        bitmapAsyncTask.execute(filePath);
    }
    public static void checkIdentityCardBack(String filepath, final Subscriber<IdentityOCRBean> subscriber){
        BitmapAsyncTask   bitmapAsyncTask = new BitmapAsyncTask(new BitmapAsyncTask.ONListener() {
            @Override
            public void getBase64Listener(String base64) {
                Map<String, Object> map = new HashMap<>();
                map.put("modelCode", "ID001");
                map.put("param", "B0");
                String randomCode = TimeUtil.getTimeOther(System.currentTimeMillis()).replace(" ", "-");
                map.put("randomCode", randomCode);
                MyLog.wtf("time", randomCode);
                map.put("verifyImage", base64);
                map.put("businessCode", OCR_BUSINESSCODE);
                //businessCode+secretKey+modelCode+randomCode
                String s = OCR_BUSINESSCODE + OCR_SECRET_KEY + "ID001" + randomCode;
                map.put("encryption", MD5Util.toMd5(s.getBytes(), false));
                //map.put("userInfo", false);

                //配置SSL
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("TLS");
                    try {
                        sslContext.init(null, null, null);
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
                createHttpClicent(subscriber,map);

            }
        });
        bitmapAsyncTask.execute(filepath);
    }
    /*银行卡 识别*/
    public static void bankOCRCheck(final String filePath, final Subscriber<IdentityOCRBean> subscriber) {
       final BitmapAsyncTask   bitmapAsyncTask = new BitmapAsyncTask(new BitmapAsyncTask.ONListener() {
            @Override
            public void getBase64Listener(String base64) {
                Map<String, Object> map = new HashMap<>();
                map.put("modelCode", "BK001");
                map.put("param", "F0");
                String randomCode = TimeUtil.getTimeOther(System.currentTimeMillis()).replace(" ", "-");
                map.put("randomCode", randomCode);
                MyLog.wtf("time", randomCode);
                map.put("verifyImage", base64);
                map.put("businessCode", OCR_BUSINESSCODE);
                //businessCode+secretKey+modelCode+randomCode
                String s = OCR_BUSINESSCODE + OCR_SECRET_KEY + "BK001" + randomCode;
                map.put("encryption", MD5Util.toMd5(s.getBytes(), false));
                //map.put("userInfo", false);


                createHttpClicent( subscriber, map);
            }
        });
        bitmapAsyncTask.execute(filePath);
    }
    private static void createHttpClicent(Subscriber subscriber,Map map){
        //配置SSL
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            try {
                sslContext.init(null, null, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(socketFactory, new SSLUtils.UnSafeTrustManager())
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(OCR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        retrofit.create(ApiService.class)
                .getIdentityOCRBean(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}