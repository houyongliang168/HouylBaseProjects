package com.base.hyl.houbasemodule.utils;
import android.widget.Toast;

import com.taikanglife.isalessystem.App;

/**
 * Created by zhaoyu on 2017/4/18.类说明：自定义吐司
 */

public class MyToast {
	
	static Toast toast;
	
	public static void showShort(String msg){
		if (toast == null) {
            toast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
	}
    public static void showLong(String msg){
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
