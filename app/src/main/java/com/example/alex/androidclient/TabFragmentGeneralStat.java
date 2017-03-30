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

public class TabFragmentGeneralStat extends Fragment implements AdapterView.OnItemSelectedListener{
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Spinner spinnerSites;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterGeneralStat adapter;

    private MyApp app;
    private String[] siteUrl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = ((MyApp)getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_stat_tab_fragment_layout, container, false);
        initView(view);
        setSpinner();
        initRecyclerView();
        return view;
    }

    private void initView(View view){
        spinnerSites = (Spinner)view.findViewById(R.id.sites_spinner);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_general_stat);
    }

    public void setSpinner(){
        Log.d(LOG_TAG, "Start setSpinner");


        siteUrl = app.getSiteUrl();
        if(siteUrl != null) {
        Log.d(LOG_TAG, "Length String[] siteUrl = " + siteUrl.length);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, siteUrl);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            spinnerSites.setAdapter(adapter);
            spinnerSites.setOnItemSelectedListener(this);
            Log.d(LOG_TAG, "End setSpinner");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "Start onItemSelected");
        Log.d(LOG_TAG, "position = " + position);

        siteUrl = app.getSiteUrl();
        app.setSiteID(position);
        app.getLikeCount();
        adapter.notifyDataSetChanged();

        Log.d(LOG_TAG, "End onItemSelected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(LOG_TAG, "Start onNothingSelected");
        Log.d(LOG_TAG, "End onNothingSelected");
    }

    private void initRecyclerView(){
        Log.d(LOG_TAG, "Start onClick case R.id.button_view");

        int[] likeCount = app.getLikeCount();
        String[] namePerson = app.getNamePerson();

        try{
            Log.d(LOG_TAG, "Length int[] likeCount = " + likeCount.length);
            Log.d(LOG_TAG, "Length String[] namePerson = " + namePerson.length);
        } catch (Exception e) {
            e.printStackTrace();
        }


        setRecyclerView(namePerson, likeCount);
    }

    public void setRecyclerView(String[] namePerson, int[] likeCount){
        Context context = getActivity();

        if(namePerson != null && likeCount != null) {
            Log.d(LOG_TAG, "Start setRecyclerView");
            Log.d(LOG_TAG, "Length int[] likeCount = " + likeCount.length);
            Log.d(LOG_TAG, "Length String[] namePerson = " + namePerson.length);
            Log.d(LOG_TAG, "context = " + context);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            adapter = new RecyclerViewAdapterGeneralStat(namePerson, likeCount, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);

            Log.d(LOG_TAG, "End setRecyclerView");
        }
    }
}