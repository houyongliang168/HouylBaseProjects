package com.base.common.loadmanager.core;


import com.base.common.loadmanager.callback.Callback;

/**
 * Description:TODO
 * Create Time:2017/9/4 8:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 * 类型转换
 */
public interface Convertor<T> {
   Class<?extends Callback> map(T t);
}
