package com.example.alex.androidclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.androidclient.R;
/*
import com.example.alex.androidclient.models.DailyStatForRecycler;
 */
import com.example.alex.androidclient.models.DailyStatistics;
import com.example.alex.androidclient.models.PersonStats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 05.03.17.
 */

public class RecyclerViewAdapterDailyStat extends
        RecyclerView.Adapter<RecyclerViewAdapterDailyStat.ViewHolder> {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private final int TYPE_ITEM_VISIBILE = 0;
    private final int TYPE_ITEM_INVISIBLE = 1;

    private List<DailyStatistics> dailyStatisticsList;
    private DailyStatistics dailyStatistics;
    private List<PersonStats> personStatsList;
    private PersonStats personStats;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private Context context;
    // what person is selected for now?
    private int selectedPerson = 0;
    // what site is selected for now?
    private int selectedSite = 0;

    private int flagUnvisibleViewInRecycler;
    private ArrayList<Integer> itemVisibility;

    public RecyclerViewAdapterDailyStat(List<DailyStatistics> dailyStats, Context context,
                                        int selectedPerson, int selectedSite) {
        this.dailyStatisticsList = dailyStats;
        this.context = context;
        this.selectedPerson = selectedPerson;
        this.selectedSite = selectedSite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "Start onCreateViewHolder");
        View view = null;
        switch (viewType) {
            case TYPE_ITEM_VISIBILE:
                try {
                    view = LayoutInflater.from(context).inflate(
                            R.layout.list_view_item_daily_stat_layout, parent, false);
                    view.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                break;
            case TYPE_ITEM_INVISIBLE:
                try {
                    view = LayoutInflater.from(context).inflate(
                            R.layout.list_view_item_daily_stat_layout, parent, false);
                    view.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

        Log.d(LOG_TAG, "End onCreateViewHolder");
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(LOG_TAG, "Start getItemViewType");
        Log.d(LOG_TAG, "position = " + position);
        Log.d(LOG_TAG, "Integer position = " + new Integer(position));
        Log.d(LOG_TAG, "itemVisibility = " + itemVisibility.size());

        if (itemVisibility.contains(Integer.valueOf(position))){
            Log.d(LOG_TAG, "End getItemViewType");
            return TYPE_ITEM_VISIBILE;
        }
        Log.d(LOG_TAG, "End getItemViewType");
        return TYPE_ITEM_INVISIBLE;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(LOG_TAG, "Start onBindViewHolder");
        dailyStatistics = dailyStatisticsList.get(position);
        if(selectedSite == dailyStatistics.getSiteID()) {
            Date date = dailyStatistics.getDate();

            personStatsList = dailyStatistics.getStatsList();
            int likesCount = 0;

            for (int i = 0; i < personStatsList.size(); i++) {
                Log.d(LOG_TAG, "Start onBindViewHolder, Start for i = " + i);
                personStats = personStatsList.get(i);
                if (personStats.getPersonID() == selectedPerson) {
                    Log.d(LOG_TAG, "Likes count for person ID: " + selectedPerson + " is " +
                            personStats.getLikesCount());
                    likesCount = personStats.getLikesCount();
                }
            }

            String dateLike = sdf.format(date);
            Log.d(LOG_TAG, "dateLike = " + dateLike);
            Log.d(LOG_TAG, "likesCount = " + String.valueOf(likesCount));
                holder.tvDate.setText(dateLike);
                holder.tvLikesCount.setText(String.valueOf(likesCount));
        }
        Log.d(LOG_TAG, "End onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return dailyStatisticsList == null ? 0 : dailyStatisticsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvLikesCount;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            tvDate = (TextView) itemView.findViewById(R.id.text_view_date);
            tvLikesCount = (TextView) itemView.findViewById(R.id.text_view_likes_count);
        }
    }

    public void setSelectedPerson(int selectedPerson) {
        this.selectedPerson = selectedPerson;
        notifyDataSetChanged();
    }

    public void setSelectedSite(int selectedSite) {
        this.selectedSite = selectedSite;
        notifyDataSetChanged();
    }
}
