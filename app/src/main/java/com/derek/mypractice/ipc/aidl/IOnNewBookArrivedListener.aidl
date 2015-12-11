// IOnNewBookArrivedListener.aidl
package com.derek.mypractice;

// Declare any non-default types here with import statements
import com.derek.mypractice.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
