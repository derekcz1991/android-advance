package com.derek.mypractice.common;

import android.os.Environment;

/**
 * Created by derek on 15/12/8.
 */
public class MyConstants {
    public static final String PATH = Environment
            .getExternalStorageDirectory().getPath() + "/practice/";
    public static final String CACHE_FILE_PATH = PATH + "usercache";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;

    public static final String GITHUB_ROOT_URL = "https://github.com/derekcz1991/android-advance/";
    public static final String CODE_ROOT_URL = GITHUB_ROOT_URL + "tree/master/app/src/main/java/";
}
