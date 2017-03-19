package com.example.alex.androidclient;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private Spinner spinnerSites;
    private Button buttonView;
    private RecyclerView recyclerView;
    final Context context = getContext();

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
        MyApp app = ((MyApp)getActivity().getApplicationContext());

        String[] siteUrl = app.getSiteUrl();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, siteUrl);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSites.setAdapter(adapter);
        spinnerSites.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerItemPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerItemPosition = -1;
    }

    private void buttonBehavoir(){
        buttonView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_view:
                MyApp app = ((MyApp)getActivity().getApplicationContext());
                app.setSiteID(spinnerItemPosition);
                int[] likeCount = app.getLikeCount();
                String[] namePerson = app.getNamePerson();
                setRecyclerView(namePerson, likeCount);
        break;
    }
}

    private void setRecyclerView(String[] namePerson, int[] likeCount){

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapterGeneralStat(namePerson, likeCount, context));
    }
}