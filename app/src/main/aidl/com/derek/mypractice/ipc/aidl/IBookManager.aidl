// IBookManager.aidl
package com.derek.mypractice.ipc.aidl;

// Declare any non-default types here with import statements
import com.derek.mypractice.ipc.aidl.Book;
import com.derek.mypractice.ipc.aidl.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
