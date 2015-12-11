package com.derek.mypractice;

/**
 * Created by derek on 15/12/10.
 */
public class Outline {
    private String title;
    private Class<?> destActivity;

    public Outline(String title, Class<?> destActivity) {
        this.title = title;
        this.destActivity = destActivity;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getDestActivity() {
        return destActivity;
    }
}
