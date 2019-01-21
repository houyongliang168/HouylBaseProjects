package com.base.common.log;
import android.widget.Toast;

import com.base.common.utils.ApplicationTool;


/**
 * Created by zhaoyu on 2017/4/18.类说明：自定义吐司
 */

public class MyToast {
	
	static Toast toast;
	
	public static void showShort(String msg){
		if (toast == null) {
            toast = Toast.makeText(ApplicationTool.getInstance().getApplication(), null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
	}
    public static void showLong(String msg){
        if (toast == null) {
            toast = Toast.makeText(ApplicationTool.getInstance().getApplication(), null, Toast.LENGTH_LONG);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
