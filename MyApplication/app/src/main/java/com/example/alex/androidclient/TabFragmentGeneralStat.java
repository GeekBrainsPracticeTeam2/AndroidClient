package com.example.alex.androidclient;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentGeneralStat extends Fragment implements AdapterView.OnItemSelectedListener {
    TextView textView;
    Spinner sites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_stat_tab_fragment_layout, container, false);
        initView(view);
        setSpinner();
        return view;
    }

    private void initView(View view){
        textView = (TextView)view.findViewById(R.id.text);
        sites = (Spinner)view.findViewById(R.id.sites_spinner);
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sites,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        sites.setAdapter(adapter);
        sites.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
