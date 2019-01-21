package com.base.common.regex;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by REN SHI QIAN on 2018/5/18.
 */

public class EncodeUtil {
    public static String encodeUrlOnlyEncodeChinese(String url){
        List<String> hanziList = new ArrayList<String>();
        /*Pattern p = null;
        Matcher m = null;*/
        String value = null;
        Pattern p = Pattern.compile("([\u4e00-\u9fa5]+)"); //  汉字的在unicode编码的范围区域：\u4e00-\u9fa5
        Matcher m = p.matcher(url);
        while (m.find()) {
            value = m.group(0);
            hanziList.add(value);                          //  先一个个匹配取出放在list集合中
        }
        for (String hanzi : hanziList) {
            try {
                url = url.replace(hanzi, URLEncoder.encode(hanzi, "UTF-8"));        //再替换
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return url;
    }
}
