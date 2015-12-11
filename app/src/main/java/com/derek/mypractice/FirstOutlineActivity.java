package com.derek.mypractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by derek on 15/12/9.
 */
public class FirstOutlineActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Outline> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outline);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        titleList = new ArrayList<>();
        titleList.add(new Outline("IPC机制", SecondOutLineActivity.class));

        System.out.println("FirstOutlineActivity");
        mRecyclerView.setAdapter(new OutlineListAdapter(titleList, this));
    }


}
