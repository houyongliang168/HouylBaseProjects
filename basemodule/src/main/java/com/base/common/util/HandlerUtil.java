package com.base.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class HandlerUtil {
	/**
	 * 主要Handler类，在线程中可用
	 * what：0.提示文本信息
	 */
	private static Handler baseHandler = null;
	
	public  static Handler getBaseHandler(final Context context){
		if (null == baseHandler) {
	    	Looper curLooper = Looper.myLooper();
	        if(null == curLooper){
	        	curLooper = Looper.getMainLooper();
	        }
			baseHandler = new Handler(curLooper);
		}  
		return baseHandler;
	}
}
