package com.base.hyl.houylbaseprojects.http;


import com.base.common.http.HttpUtil;

/**
 * Created by 41113 on 2018/11/7.
 */

public class HttpUtilsService {




    /**
     * 获取 主的的 service

     * @return
     */
    public static ApiService getApiServie() {
        return HttpUtil.baseHttpUtils(ApiService.class, "www.baodu.com");
    }




}
