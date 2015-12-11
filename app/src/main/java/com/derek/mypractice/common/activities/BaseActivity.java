/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.derek.mypractice.common.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.derek.mypractice.R;
import com.derek.mypractice.common.logger.Log;
import com.derek.mypractice.common.logger.LogFragment;
import com.derek.mypractice.common.logger.LogWrapper;
import com.derek.mypractice.common.logger.MessageOnlyLogFilter;


/**
 * Base launcher activity, to handle most of the common plumbing for samples.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    protected int menuRes = -1;
    protected String codeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpView();
        initializeLogging();
    }

    /**
     * Child must call setContentView within this method
     */
    protected abstract void setUpView();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuRes == -1) {
            getMenuInflater().inflate(R.menu.main, menu);
        } else {
            getMenuInflater().inflate(menuRes, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuRes == -1) {
            switch (item.getItemId()) {
                case R.id.menu_code:
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.EXTRA_URL, codeUrl);
                    startActivity(intent);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Set up targets to receive log data
     */
    /*public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        Log.i(TAG, "Ready");
    }*/

    /**
     * Set up targets to receive log data
     */
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
}
