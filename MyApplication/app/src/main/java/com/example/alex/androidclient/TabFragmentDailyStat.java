package com.example.alex.androidclient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import static com.example.alex.androidclient.DatePickerFragment.DATE_SELECTED;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentDailyStat extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener{
    private Spinner spinnerSites;
    private Button buttonView, buttonFirstDateSelected, buttonLastDateSelected;
    private LinearLayout linearLayoutTextViewDateSelected;
    private RecyclerView recyclerView;

    private Calendar firstDateSelected;
    private Calendar lastDateSelected;

    private long date;

    private static final int REQUEST_DATE = 1;

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
        buttonFirstDateSelected = (Button)view.findViewById(R.id.first_date_selected);
        buttonLastDateSelected = (Button)view.findViewById(R.id.last_date_selected);
        linearLayoutTextViewDateSelected = (LinearLayout)view.findViewById(R.id.
                linear_layout_textview_date_selected);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
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
        buttonView.setOnClickListener(this);
        buttonFirstDateSelected.setOnClickListener(this);
        buttonLastDateSelected.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_date_selected:
                setDate(firstDateSelected);
                visibileLinearLayoutTextViewDateSelected();
                break;
            case R.id.last_date_selected:
                setDate(lastDateSelected);
                visibileLinearLayoutTextViewDateSelected();
                break;
            case R.id.button_view:
                //здесь метод отправки запроса к БД

                break;

        }

    }

    private void visibileLinearLayoutTextViewDateSelected(){
        if (firstDateSelected != null && lastDateSelected != null){
            linearLayoutTextViewDateSelected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_DATE){
            date = data.getLongExtra(DATE_SELECTED, -1);
        }
    }

    private void setDate(Calendar calendar){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(this, REQUEST_DATE);
        newFragment.show(getFragmentManager(), "DatePicker");

        if (date != 0){
            calendar.setTimeInMillis(date);
        }


    }

}
