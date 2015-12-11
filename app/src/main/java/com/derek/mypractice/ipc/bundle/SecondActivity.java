package com.derek.mypractice.ipc.bundle;

import android.os.Bundle;

import com.derek.mypractice.R;
import com.derek.mypractice.common.MyConstants;
import com.derek.mypractice.common.MyUtils;
import com.derek.mypractice.common.activities.BaseActivity;
import com.derek.mypractice.common.logger.Log;
import com.derek.mypractice.common.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by derek on 15/12/8.
 */
public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("文件共享: " + TAG);
    }

    @Override
    protected void setUpView() {
        setContentView(R.layout.empty_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recoverFromFile();
    }

    private void recoverFromFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                if (cachedFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cachedFile));
                        user = (User) objectInputStream.readObject();
                        Log.d(TAG, "recover user: " + user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        MyUtils.close(objectInputStream);
                    }
                }
            }
        }).start();
    }
}
