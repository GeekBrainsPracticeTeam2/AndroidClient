package com.example.alex.androidclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentDailyStat extends Fragment implements AdapterView.OnItemSelectedListener{
    Spinner spinnerSites;
    Button buttonView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_stat_tab_fragment_layout, container, false);
        initView(view);
        setSpinner();
        buttonBehavoir();
        return view;
    }

    private void initView(View view){
        spinnerSites = (Spinner)view.findViewById(R.id.sites_spinner);
        buttonView =(Button)view.findViewById(R.id.button_view);
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sites,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSites.setAdapter(adapter);
        spinnerSites.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void buttonBehavoir(){

    }
}
