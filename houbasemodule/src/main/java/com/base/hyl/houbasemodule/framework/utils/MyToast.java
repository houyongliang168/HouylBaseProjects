package com.base.hyl.houbasemodule.framework.utils;
import android.widget.Toast;

import com.base.hyl.houbasemodule.ApplicationToolss;


/**
 * Created by zhaoyu on 2017/4/18.类说明：自定义吐司
 */

public class MyToast {
	
	static Toast toast;
	
	public static void showShort(String msg){
		if (toast == null) {
            toast = Toast.makeText(ApplicationToolss.getInstance().getApplication(), null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
	}
    public static void showLong(String msg){
        if (toast == null) {
            toast = Toast.makeText(ApplicationToolss.getInstance().getApplication(), null, Toast.LENGTH_LONG);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
