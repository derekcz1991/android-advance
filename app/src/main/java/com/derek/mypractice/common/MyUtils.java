package com.derek.mypractice.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by derek on 15/12/8.
 */
public class MyUtils {

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCodeUrl(String clsName) {
        return clsName.replace(".", "/") + ".java";
    }


}
