// IBookManager.aidl
package com.base.hyl.houylbaseprojects.aidl;
import com.base.hyl.houylbaseprojects.aidl.Book;
import com.base.hyl.houylbaseprojects.aidl.IOnNewBookArrivedListerner;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);// in 输入型参数 out 输出型参数  inout 输入输出型参数  底层的开销不同
    void registerListener(IOnNewBookArrivedListerner listener);
    void unregisterListener(IOnNewBookArrivedListerner listener);
}
