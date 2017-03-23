package com.example.alex.androidclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.androidclient.R;
import com.example.alex.androidclient.models.DailyStatForRecycler;
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
    private List<DailyStatForRecycler> dailyStatForRecyclerList;
    private DailyStatForRecycler dailyStatForRecycler;
    private List<DailyStatistics> dailyStatisticsList;
    private DailyStatistics dailyStatistics;
    private List<PersonStats> personStatsList;
    private PersonStats personStats;
    private Context context;

    public RecyclerViewAdapterDailyStat(List<DailyStatistics> dailyStats, Context context) {
        this.dailyStats = dailyStats;
        this.context = context;
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
        int siteID = dailyStatistics.getSiteID();
        Date date = dailyStatistics.getDate();
        personStatsList = dailyStatistics.getStatsList();

        for (int i = 0; i < personStatsList.size(); i++){
        personStats = personStatsList.get(i);
            int personId = personStats.getPersonID();
            int likeCount = personStats.getLikesCount();
            dailyStatForRecyclerList.add(new DailyStatForRecycler(siteID, date, personId,
                    likeCount));
        }

        Map<Date, Integer> dateIntegerMap



        {   Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date[position]);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tvDate = sdf.format(calendar);
        }

        holder.tvDate.setText(sdf.format(tvDate));
        holder.tvLikesCount.setText("" + likesCount[position]);
    }

    private

    @Override
    public int getItemCount() {
        return date == null ? 0 : date.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvLikesCount;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews (View itemView){
            tvDate = (TextView) itemView.findViewById(R.id.text_view_date);
            tvLikesCount = (TextView)itemView.findViewById(R.id.text_view_likes_count);
        }
    }
}
