package com.base.common.loadmanager.owncallback;


import com.base.common.loadmanager.callback.Callback;
import com.base.hyl.houbasemodule.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class ErrorCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.module_error_page;
    }

}
