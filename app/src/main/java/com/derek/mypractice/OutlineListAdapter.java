package com.derek.mypractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by derek on 15/12/9.
 */
public class OutlineListAdapter extends RecyclerView.Adapter<OutlineListAdapter.ViewHolder> {

    private ArrayList<Outline> titleList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView title;
        private IMyViewHolderClicks mListener;
        private int position;

        public ViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);

            mListener = listener;
            title = (TextView) itemView.findViewById(R.id.text_view);
            itemView.findViewById(R.id.root).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(position);
        }

        private interface IMyViewHolderClicks {
            void onItemClick(int position);
        }
    }

    public OutlineListAdapter(ArrayList<Outline> titleList, Context context) {
        this.titleList = titleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_single_line, parent, false);
        return new ViewHolder(v, new ViewHolder.IMyViewHolderClicks() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, titleList.get(position).getDestActivity());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;
        holder.title.setText(titleList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


}
