// IBookManager.aidl
package com.derek.mypractice;

// Declare any non-default types here with import statements
import com.derek.mypractice.Book;
import com.derek.mypractice.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
