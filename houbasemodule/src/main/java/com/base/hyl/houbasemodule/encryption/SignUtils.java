package com.base.hyl.houbasemodule.encryption;

import android.text.TextUtils;

import com.base.hyl.houbasemodule.Bean.CompareUtils;
import com.base.hyl.houbasemodule.Bean.Paramaters;
import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 41113 on 2018/7/30.
 */

public class SignUtils {

    public static String getSign(String mobile, String verifiCode,String token){
        if(TextUtils.isEmpty(mobile)||TextUtils.isEmpty(verifiCode)||TextUtils.isEmpty(token)){
            return "";
        }
        // TODO: 2018/7/30  修改 固定加密标签
        ArrayList<Paramaters> list=new ArrayList<>();
        Paramaters p1=new Paramaters("mobile",mobile);
        Paramaters p2=new Paramaters("verifiCode",verifiCode);


        list.add(p1);
        list.add(p2);


        String mySign = getSign(list);
        Logger.e(mySign);
        return mySign;
    }


    public static String getSign(ArrayList<Paramaters> paramaterses){
        ArrayList<Paramaters> list = orderByASCII(paramaterses);
        String params = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0){
                params = list.get(i).getKey()+"="+list.get(i).getValue();
            }else{
                params = params+"&"+ list.get(i).getKey()+"="+list.get(i).getValue();
            }
        }
        String sha256 = sha256Encrypt(params);
        Logger.e("sha256 :"+sha256);
        String md5 = encryptMD5(sha256);
        Logger.e("md5 :"+md5);
        return md5;
    }

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

    public static String signBySHA256AndMD5(String str) {
        String sha256 = sha256Encrypt(str);
        return DigestUtils.md5Hex(sha256);
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
}
