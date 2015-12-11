package com.derek.mypractice.ipc.bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.derek.mypractice.R;
import com.derek.mypractice.common.MyConstants;
import com.derek.mypractice.common.MyUtils;
import com.derek.mypractice.common.activities.BaseActivity;
import com.derek.mypractice.common.logger.Log;
import com.derek.mypractice.common.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by derek on 15/12/8.
 */
public class FirstActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("文件共享: " + TAG);
    }

    @Override
    protected void setUpView() {
        setContentView(R.layout.activity_ipc_bundle_first);
        findViewById(R.id.btn_next).setOnClickListener(this);

        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvDesc.setText("文件共享也是一种不错的进程间通信方式，两个进程通过读／写同一个文件来交换数据。" +
                "在此Activity中序列化一个对象到文件系统中，在SecondActivity中恢复这个对象");

        super.codeUrl = MyUtils.getCodeUrl(this.getClass().getName());
        Log.d(TAG, "codeUrl = " + codeUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        persisToFile();
    }

    private void persisToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(1, "hello world", false);
                File dir = new File(MyConstants.PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    Log.d(TAG, "persist user: " + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.close(objectOutputStream);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_next:
                intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }
}
