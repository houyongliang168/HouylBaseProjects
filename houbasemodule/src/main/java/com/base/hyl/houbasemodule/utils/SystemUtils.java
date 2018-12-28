package com.base.hyl.houbasemodule.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by zcc on 2017/7/29.
 */

public class SystemUtils {
    public static void openBrowser(Context context, String url) {
        //urlText是一个文本输入框，输入网站地址
        //Uri  是统一资源标识符
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
