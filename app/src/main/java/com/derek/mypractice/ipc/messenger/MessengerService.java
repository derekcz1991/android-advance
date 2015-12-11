package com.derek.mypractice.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.derek.mypractice.common.MyConstants;


/**
 * Created by derek on 15/12/8.
 * MessengerHandler用来处理客户端发送的消息，并从消息中取出客户端发来的文本信息，
 * 而mMessenger是一个Messenger对象，它和MessengerHandler相关联，并在onBind方法中返回它里面的Binder对象，
 * 可以看出，这里Messenger的作用是将客户端发送的消息传递给MessengerHandler处理
 */
public class MessengerService extends Service {
    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.i(TAG, "receive msg from Client:" + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle data = new Bundle();
                    data.putString("reply", "Yes, I got your greet.");
                    replyMessage.setData(data);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
