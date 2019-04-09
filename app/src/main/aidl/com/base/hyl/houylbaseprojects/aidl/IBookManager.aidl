// IBookManager.aidl
package com.base.hyl.houylbaseprojects.aidl;
import com.base.hyl.houylbaseprojects.aidl.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(inout Book book);
}
