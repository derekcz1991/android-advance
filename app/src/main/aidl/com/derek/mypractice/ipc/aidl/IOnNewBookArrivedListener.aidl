// IOnNewBookArrivedListener.aidl
package com.derek.mypractice.ipc.aidl;

// Declare any non-default types here with import statements
import com.derek.mypractice.ipc.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
