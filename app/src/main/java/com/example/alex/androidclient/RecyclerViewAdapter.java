package com.example.alex.androidclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alex on 05.03.17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[] namePerson;
    private int[] indexOccurrence;
    private Context context;

    public RecyclerViewAdapter(String[] namePerson, int[] indexOccurrence, Context context) {
        this.namePerson = namePerson;
        this.indexOccurrence = indexOccurrence;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item_layout, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return namePerson == null ? 0 : namePerson.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamePerson;
        TextView textViewIndexOccurrence;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews (View itemView){
            textViewNamePerson = (TextView) itemView.findViewById(R.id.text_view_name_person);
            textViewIndexOccurrence = (TextView)itemView.findViewById(R.id.
                    text_view_index_occurrence);
        }
    }
}
