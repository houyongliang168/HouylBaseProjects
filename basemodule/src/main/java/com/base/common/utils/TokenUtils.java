package com.base.common.utils;

import android.text.TextUtils;


import com.base.Bean.CompareUtils;
import com.base.Bean.Paramaters;
import com.base.common.log.MyLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;


public class TokenUtils {

    /**
     * 根据参数排序
     * @param paramaterses
     * @return
     */
    public static ArrayList<Paramaters> orderByASCII(ArrayList<Paramaters> paramaterses)
    {
        if (paramaterses != null && paramaterses.size() != 0){
            Collections.sort(paramaterses,new CompareUtils());
            return paramaterses;
        }
        return null;
    }

    /**
     * SHA256加密
     *
     * @param strSrc
     *            明文
     * @return 加密之后的密文
     */
    public static String sha256Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 加密
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public final static String  encryptMD5(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getToken(ArrayList<Paramaters> paramaterses){
        // 新建一个list,值为空的<key,value>不参与token运算
        ArrayList<Paramaters> tokenList = new ArrayList<>();

        MyLog.wtf("H5_token_tokenList：", "" + tokenList.size());
        // ASCII排序
        ArrayList<Paramaters> list = orderByASCII(tokenList);
        // 生成token
        String params = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                params = list.get(i).getKey() + "=" + list.get(i).getValue();
            } else {
                params = params + "&" + list.get(i).getKey() + "=" + list.get(i).getValue();
            }

        }
        String sha256 = sha256Encrypt(params);
        String md5 = encryptMD5(sha256);
        return md5;
    }

    public static String getTokenStr(ArrayList<Paramaters> paramaterses){
        // ASCII排序
        ArrayList<Paramaters> list = orderByASCII(paramaterses);
        // 生成token
        String params = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                // 如果是第一个参数
                if("".equals(list.get(i).getValue()) || null == list.get(i).getValue()){
                    // 值为空的<key,value>不参与token运算
                    MyLog.wtf("H5_url_token_getKey=", list.get(i).getKey());
                    MyLog.wtf("H5_url_token_getValue=", list.get(i).getValue());
                    params = "";
                }else {
                    params = list.get(i).getKey() + "=" + list.get(i).getValue();
                    MyLog.wtf("H5_url_token_params=", params);
                }
            } else {
                if("".equals(list.get(i).getValue()) || null == list.get(i).getValue()){
                    // 值为空的<key,value>不参与token运算
                    MyLog.wtf("H5_url_token_getKey=", list.get(i).getKey());
                    MyLog.wtf("H5_url_token_getValue=", list.get(i).getValue());
                    params = params + "";
                }else {
                    if(TextUtils.isEmpty(params)){
                        params = list.get(i).getKey() + "=" + list.get(i).getValue();
                    }else {
                        params = params + "&" + list.get(i).getKey() + "=" + list.get(i).getValue();
                        MyLog.wtf("H5_url_token_params=", params);
                    }
                }
            }
        }
        String sha256 = sha256Encrypt(params);
        String md5 = encryptMD5(sha256);
        return md5;
    }

    public static String getOrderUrl(String url, ArrayList<Paramaters> list){
        // 新建一个list,使secret参数不出现在Url里面
        ArrayList<Paramaters> urlList = new ArrayList<>();

        String orderUrl = url;
        // 拼接url的时候去掉secret的map
        /*for(int i = list.size()-1; i >= 0; i--){
            if("secret".equals(list.get(i).getKey())){
                list.remove(i);
            }
        }*/
        // ASCII排序
        ArrayList<Paramaters> orderList = orderByASCII(urlList);
        // 此处生成带参数的后半段url
        String paramUrl = "";
        for (int i = 0; i < orderList.size(); i++){
            if(i == 0){
                paramUrl = paramUrl + orderList.get(i).getKey()
                        + "=" + orderList.get(i).getValue();
            }else {
                paramUrl = paramUrl + "&" + orderList.get(i).getKey()
                        + "=" + orderList.get(i).getValue();
            }
        }
        // 开始用原始url拼接带参数的url
        if(orderUrl.contains("?")){
            if(orderUrl.endsWith("?")){
                // 链接里没有拼接参数，但是是以"？"结尾
                orderUrl = orderUrl + paramUrl;
            }else{
                // 链接里已经拼接了参数，直接在后面继续拼接
                orderUrl = orderUrl + "&" + paramUrl;
            }
        }else{
            // 链接里没有拼接参数，也没有"？"
            orderUrl = orderUrl + "?" + paramUrl;
        }
        return orderUrl;
    }

}
