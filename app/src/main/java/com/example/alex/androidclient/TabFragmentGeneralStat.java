package com.example.alex.androidclient;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alex.androidclient.adapter.RecyclerViewAdapterGeneralStat;
import com.example.alex.androidclient.managers.CacheManager;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentGeneralStat extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener{
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Spinner spinnerSites;
    private Button buttonView;
    private RecyclerView recyclerView;
    private Context context;

    private int spinnerItemPosition;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_stat_tab_fragment_layout, container, false);
        initView(view);
        setSpinner();
        buttonBehavoir();
        return view;
    }

    private void initView(View view){
        spinnerSites = (Spinner)view.findViewById(R.id.sites_spinner);
        buttonView =(Button)view.findViewById(R.id.button_view);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_general_stat);
    }

    private void setSpinner(){
        Log.d(LOG_TAG, "Start setSpinner");

        MyApp app = ((MyApp)getActivity().getApplicationContext());
        String[] siteUrl = app.getSiteUrl();
        Log.d(LOG_TAG, "Length String[] siteUrl = " + siteUrl.length);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, siteUrl);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSites.setAdapter(adapter);
        spinnerSites.setOnItemSelectedListener(this);
        Log.d(LOG_TAG, "End setSpinner");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "Start onItemSelected");
        spinnerItemPosition = position;
        Log.d(LOG_TAG, "spinnerItemPosition = " + spinnerItemPosition);
        Log.d(LOG_TAG, "End onItemSelected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(LOG_TAG, "Start onNothingSelected");
        spinnerItemPosition = -1;

        Log.d(LOG_TAG, "spinnerItemPosition = " + spinnerItemPosition);
        Log.d(LOG_TAG, "End onNothingSelected");
    }

    private void buttonBehavoir(){
        buttonView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_view:
                Log.d(LOG_TAG, "Start onClick case R.id.button_view");
                MyApp app = ((MyApp)getActivity().getApplicationContext());
                app.setSiteID(spinnerItemPosition);
                int[] likeCount = app.getLikeCount();
                Log.d(LOG_TAG, "Length int[] likeCount = " + likeCount.length);
                String[] namePerson = app.getNamePerson();
                Log.d(LOG_TAG, "Length String[] namePerson = " + namePerson.length);
                setRecyclerView(namePerson, likeCount);
                Log.d(LOG_TAG, "End onClick case R.id.button_view");
        break;
    }
}

    private void setRecyclerView(String[] namePerson, int[] likeCount){
        context = getActivity();
        Log.d(LOG_TAG, "Start setRecyclerView");
        Log.d(LOG_TAG, "Length int[] likeCount = " + likeCount.length);
        Log.d(LOG_TAG, "Length String[] namePerson = " + namePerson.length);
        Log.d(LOG_TAG, "context = " + context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapterGeneralStat(namePerson, likeCount, context));
        Log.d(LOG_TAG, "End setRecyclerView");
    }
}