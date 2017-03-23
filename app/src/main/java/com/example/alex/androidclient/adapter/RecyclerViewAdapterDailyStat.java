package com.example.alex.androidclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.androidclient.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by alex on 05.03.17.
 */

public class RecyclerViewAdapterDailyStat extends
        RecyclerView.Adapter<RecyclerViewAdapterDailyStat.ViewHolder> {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private long[] date;
    private int[] likesCount;
    private Context context;

    public RecyclerViewAdapterDailyStat(long[] date, int[] likesCount, Context context) {
        this.date = date;
        this.likesCount = likesCount;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date[position]);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tvDate = sdf.format(calendar);

        holder.tvDate.setText(sdf.format(tvDate));
        holder.tvLikesCount.setText("" + likesCount[position]);
    }

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
