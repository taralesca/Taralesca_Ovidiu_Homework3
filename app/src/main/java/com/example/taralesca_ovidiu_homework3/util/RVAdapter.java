package com.example.taralesca_ovidiu_homework3.util;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taralesca_ovidiu_homework3.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TextViewHolder> {
    private String[] dataSet;
    private OnTapListener onTapListener;

    public RVAdapter(String[] dataSet, OnTapListener onTapListener) {
        this.dataSet = dataSet;
        this.onTapListener = onTapListener;
    }

    public void swapDataSet(String[] newData) {
        this.dataSet = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card, parent, false);

        return new TextViewHolder(v, onTapListener);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.textView.setText(dataSet[position]);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    static class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        OnTapListener onTapListener;

        TextViewHolder(TextView v, OnTapListener onTapListener) {
            super(v);
            textView = v;
            this.onTapListener = onTapListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTapListener.onUserClick(getAdapterPosition());
        }
    }

    public interface OnTapListener {
        void onUserClick(int position);
    }
}

