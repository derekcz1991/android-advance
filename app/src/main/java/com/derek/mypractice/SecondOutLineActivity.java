package com.derek.mypractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.derek.mypractice.ipc.aidl.BookManagerActivity;
import com.derek.mypractice.ipc.bundle.FirstActivity;
import com.derek.mypractice.ipc.messenger.MessengerActivity;

import java.util.ArrayList;

/**
 * Created by derek on 15/12/9.
 */
public class SecondOutLineActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Outline> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outline);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        titleList = new ArrayList<>();
        titleList.add(new Outline("使用文件共享", FirstActivity.class));
        titleList.add(new Outline("使用Messenger", MessengerActivity.class));
        titleList.add(new Outline("使用AIDL", BookManagerActivity.class));
        //titleList.add("使用ContentProvider");
        //titleList.add("使用Socket");

        mRecyclerView.setAdapter(new OutlineListAdapter(titleList, this));

    }

}
