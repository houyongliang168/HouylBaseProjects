// IBookManager.aidl
package com.base.hyl.houylbaseprojects.aidl;
import com.base.hyl.houylbaseprojects.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListerner {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   
    void onNewBookArrived(in Book book);// in 输入型参数 out 输出型参数  inout 输入输出型参数  底层的开销不同
}
