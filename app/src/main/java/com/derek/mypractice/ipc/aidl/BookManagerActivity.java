package com.derek.mypractice.ipc.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.TextView;

import com.derek.mypractice.R;
import com.derek.mypractice.common.MyUtils;
import com.derek.mypractice.common.activities.BaseActivity;
import com.derek.mypractice.common.logger.Log;

import java.util.List;


/**
 * Created by derek on 15/12/8.
 * Reference: http://developer.android.com/guide/components/aidl.html
 */
public class BookManagerActivity extends BaseActivity {

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive new book: " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    /**
     * 客户端的 onServiceConnected 和 onServiceDisconnected 方法都运行在UI线程中，
     * 所以也不可以在它们里面直接调用服务端的耗时方法。
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            mRemoteBookManager = bookManager;
            try {
                // 给binder设置死亡代理
                mRemoteBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
                List<Book> list = bookManager.getBookList();
                //Log.i(TAG, "query book list, list type: " + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list: " + list.toString());
                Book newBook = new Book(3, "material design");
                bookManager.addBook(newBook);
                Log.i(TAG, "add book: " + newBook.toString());
                List<Book> newList = bookManager.getBookList();
                Log.i(TAG, "query book list: " + newList.toString());
                // 客户端注册IOnNewBookArrivedListener到远程服务端
                bookManager.registerListener(mOnNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Log.e(TAG, "binder died");
        }
    };

    /**
     * 当Binder死亡的时候，系统就会回调binderDied方法，然后我们就可以移除之前绑定的binder代理并重新绑定远程服务
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            if(mRemoteBookManager == null)
                return;
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;

            //TODO 重新绑定远程service

        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        // 此方法运行在客户端的Binder线程迟中，访问UI,使用handler
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("AIDL");
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        super.codeUrl = MyUtils.getCodeDirUrl(this.getClass().getPackage().getName());
    }

    @Override
    protected void setUpView() {
        setContentView(R.layout.empty_activity);
        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        StringBuffer sb = new StringBuffer();
        sb.append("When you need to perform IPC, using a Messenger for your interface is simpler than implementing it with AIDL, " +
                "because Messenger queues all calls to the service, whereas, a pure AIDL interface sends simultaneous requests to the service, " +
                "which must then handle multi-threading.\n\n");
        sb.append("For most applications, the service doesn't need to perform multi-threading, " +
                "so using a Messenger allows the service to handle one call at a time. If it's important that your service be multi-threaded, " +
                "then you should use AIDL to define your interface.");

        tvDesc.setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            Log.i(TAG, "unregister listener: " + mOnNewBookArrivedListener);
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
