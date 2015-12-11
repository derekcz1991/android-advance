package com.derek.mypractice.ipc.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.TextView;

import com.derek.mypractice.R;
import com.derek.mypractice.common.MyConstants;
import com.derek.mypractice.common.MyUtils;
import com.derek.mypractice.common.activities.BaseActivity;
import com.derek.mypractice.common.logger.Log;


/**
 * Created by derek on 15/12/8.
 * reference: http://developer.android.com/guide/components/bound-services.html#Messenger
 * 首先需要绑定远程的MessengerService，
 * 绑定成功后，根据服务器返回的binder对象创建Messenger对象并使用此对象想服务器端发送消息
 */
public class MessengerActivity extends BaseActivity {

    private static final String TAG = "MessengerActivity";

    private Messenger mService;

    // 为了接收服务端的回复，客户端也需要准备一个接受消息的Messenger和Handler
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, "receive msg from Server: " + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello, this is client");
            msg.setData(data);
            msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(msg);
                Log.i(TAG, "send msg to Server: " + "hello, this is client");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Messenger");
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        super.codeUrl = MyUtils.getCodeDirUrl(this.getClass().getPackage().getName());
    }

    @Override
    protected void setUpView() {
        setContentView(R.layout.empty_activity);
        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        StringBuffer sb = new StringBuffer();
        sb.append("If you need your service to communicate with remote processes, " +
                "then you can use a Messenger to provide the interface for your service." +
                " This technique allows you to perform interprocess communication (IPC) without the need to use AIDL.\n\n");
        tvDesc.setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

}
