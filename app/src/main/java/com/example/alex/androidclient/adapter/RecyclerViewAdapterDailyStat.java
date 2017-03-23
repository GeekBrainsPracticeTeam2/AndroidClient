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
    /*private List<DailyStatForRecycler> dailyStatForRecyclerList;
    private DailyStatForRecycler dailyStatForRecycler;*/
    private List<DailyStatistics> dailyStatisticsList;
    private DailyStatistics dailyStatistics;
    private List<PersonStats> personStatsList;
    private PersonStats personStats;
    private Context context;
    // what person is selected for now?
    private int selectedPerson = 0;
    // what site is selected for now?
    private int selectedSite = 0;

    public RecyclerViewAdapterDailyStat(List<DailyStatistics> dailyStats, Context context,
                                        int selectedPerson, int selectedSite) {
        this.dailyStatisticsList = dailyStats;
        this.context = context;
        this.selectedPerson = selectedPerson;
        this.selectedSite = selectedSite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item_general_stat_layout, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dailyStatistics = dailyStatisticsList.get(position);
        if(selectedSite == dailyStatistics.getSiteID()) {
            Date date = dailyStatistics.getDate();
            Log.d(LOG_TAG, date.toString());
            personStatsList = dailyStatistics.getStatsList();
            int likesCount = 0;

            for (int i = 0; i < personStatsList.size(); i++) {
                personStats = personStatsList.get(i);
                if (personStats.getPersonID() == selectedPerson) {
                    Log.d(LOG_TAG, "Likes count for person ID: " + selectedPerson + " is " +
                            personStats.getLikesCount());
                    likesCount = personStats.getLikesCount();
                }
            }

            /*Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date[position]);*/
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");


            try {
                holder.tvDate.setText(date.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.tvLikesCount.setText(String.valueOf(likesCount));
        }
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

    public int getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(int selectedPerson) {
        this.selectedPerson = selectedPerson;
        notifyDataSetChanged();
    }

    public int getSelectedSite() {
        return selectedSite;
    }

    public void setSelectedSite(int selectedSite) {
        this.selectedSite = selectedSite;
        notifyDataSetChanged();
    }
}
