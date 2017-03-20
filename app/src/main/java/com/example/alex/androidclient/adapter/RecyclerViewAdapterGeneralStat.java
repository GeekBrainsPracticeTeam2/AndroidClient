package com.example.alex.androidclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.androidclient.R;
import com.example.alex.androidclient.models.PersonStats;

import java.util.ArrayList;

/**
 * Created by alex on 18.03.17.
 */

public class RecyclerViewAdapterGeneralStat extends
        RecyclerView.Adapter<RecyclerViewAdapterGeneralStat.ViewHolder> {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private String[] namePerson;
    private int[] likesCount;
    private Context context;

    public RecyclerViewAdapterGeneralStat(String[] namePerson, int[] likesCount, Context context) {
        Log.d(LOG_TAG, "Start contstructor");

        this.namePerson = namePerson;
        this.likesCount = likesCount;
        this.context = context;

        Log.d(LOG_TAG, "Length namePerson = " + namePerson.length);
        Log.d(LOG_TAG, "Length likesCount = " + likesCount.length);
        Log.d(LOG_TAG, "context = " + context);
        Log.d(LOG_TAG, "End contstructor");
    }

    @Override
    public RecyclerViewAdapterGeneralStat.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.
                        list_view_item_layout, parent, false);
        return new RecyclerViewAdapterGeneralStat.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterGeneralStat.ViewHolder holder, int position) {
        holder.textViewNamePerson.setText(namePerson[position]);
        holder.textViewlikesCount.setText(likesCount[position]);

    }

    @Override
    public int getItemCount() {
        return namePerson == null ? 0 : namePerson.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamePerson;
        TextView textViewlikesCount;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews (View itemView){
            textViewNamePerson = (TextView) itemView.findViewById(R.id.text_view_name_person);
            textViewlikesCount = (TextView)itemView.findViewById(R.id.text_view_likes_count);
        }
    }
}
