/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.hyl.houbasemodule.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 */
public class ToastUtil
{

	//这个类的使用会导致内存泄露，在release时关闭其功能
	//单例模式
	public static boolean isShow = true;
	private static String oldMsg;
	protected static Toast toast   = null;
	private static long oneTime=0;
	private static long twoTime=0;

	public static void showToast(Context context, String s){
			if(context==null){
				return;
			}
		if(isShow) {
			//避免内存泄露
//			context = context.getApplicationContext();
			if (toast == null) {
				toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
				toast.show();
				oneTime = System.currentTimeMillis();
			} else {
				twoTime = System.currentTimeMillis();
				if (s.equals(oldMsg)) {
					if (twoTime - oneTime > Toast.LENGTH_SHORT) {
						toast.show();
					}
				} else {
					oldMsg = s;
					toast.setText(s);
					toast.show();
				}
			}
			oneTime = twoTime;
		}
	}


	public static void showToast(Context context, int resId){
		if(isShow) {
			showToast(context, context.getString(resId));
		}
	}



//
//
//
//	/**
//	 * 短时间显示Toast
//	 *
//	 * @param context
//	 * @param message
//	 */
//	public static void showShort(Context context, CharSequence message)
//	{
//		if (isShow)
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//
//	}
//
//	/**
//	 * 短时间显示Toast
//	 *
//	 * @param context
//	 * @param message
//	 */
//	public static void showShort(Context context, int message)
//	{
//		if (isShow)
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//	}
//
//	/**
//	 * 长时间显示Toast
//	 *
//	 * @param context
//	 * @param message
//	 */
//	public static void showLong(Context context, CharSequence message)
//	{
//		if (isShow)
//			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//	}
//
//	/**
//	 * 长时间显示Toast
//	 *
//	 * @param context
//	 * @param message
//	 */
//	public static void showLong(Context context, int message)
//	{
//		if (isShow)
//			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//	}
//
//	/**
//	 * 自定义显示Toast时间
//	 *
//	 * @param context
//	 * @param message
//	 * @param duration
//	 */
//	public static void show(Context context, CharSequence message, int duration)
//	{
//		if (isShow)
//			Toast.makeText(context, message, duration).show();
//	}
//
//	/**
//	 * 自定义显示Toast时间
//	 *
//	 * @param context
//	 * @param message
//	 * @param duration
//	 */
//	public static void show(Context context, int message, int duration)
//	{
//		if (isShow)
//			Toast.makeText(context, message, duration).show();
//	}

}
