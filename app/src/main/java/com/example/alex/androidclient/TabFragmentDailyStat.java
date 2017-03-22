package com.example.alex.androidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.example.alex.androidclient.presenters.PresenterDailyStat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.alex.androidclient.DatePickerFragment.BUTTON_SELECTED;
import static com.example.alex.androidclient.DatePickerFragment.CHOSEN;
import static com.example.alex.androidclient.DatePickerFragment.DATE_SELECTED;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentDailyStat extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener{
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Spinner spinnerSites;
    private Button bView, bFirstDateSelected, bLastDateSelected;
    private TextView tvFirstDateSelected, tvLastDateSelected;
    private RecyclerView recyclerView;

    private Calendar firstDateSelected, lastDateSelected;

    private long date;

    private boolean firstDateChosen, lastDateChosen = false;

    private static final int flagFirstDateSelected = R.id.first_date_selected;
    private static final int flagLastDateSelected = R.id.last_date_selected;
    private static final int CHANGE_DATE = 2;


    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    private MyApp app;

    private PresenterDailyStat presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = ((MyApp)getActivity().getApplicationContext());
        presenter = new PresenterDailyStat();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_stat_tab_fragment_layout, container, false);
        initView(view);
        setSpinner();
        buttonBehavoir();

        return view;
    }

    private void initView(View view){
        spinnerSites = (Spinner)view.findViewById(R.id.sites_spinner);

        bView =(Button)view.findViewById(R.id.button_view);
        bFirstDateSelected = (Button)view.findViewById(R.id.first_date_selected);
        bLastDateSelected = (Button)view.findViewById(R.id.last_date_selected);

        tvFirstDateSelected = (TextView)view.findViewById(R.id.textview_first_date_selected);
        tvLastDateSelected = (TextView)view.findViewById(R.id.textviews_last_date_selected);

        setTVDefault(tvFirstDateSelected);
        setTVDefault(tvLastDateSelected);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
    }

    private void setTVDefault(TextView tv) {
        tv.setText(R.string.default_date);
    }

    private void setTVChosen(int flag){
        switch (flag) {
            case flagFirstDateSelected:
                try {
                    String firstDate = sdf.format(firstDateSelected.getTime());
                    tvFirstDateSelected.setText(firstDate);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case flagLastDateSelected:
                try {
                    String lastDate = sdf.format(lastDateSelected.getTime());
                    tvLastDateSelected.setText(lastDate);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setSpinner(){
        String[] siteUrl = app.getSiteUrl();

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, siteUrl);
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
        bView.setOnClickListener(this);
        bFirstDateSelected.setOnClickListener(this);
        bLastDateSelected.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case flagFirstDateSelected:
                startDatePickerDialog(flagFirstDateSelected);
                break;
            case flagLastDateSelected:
                startDatePickerDialog(flagLastDateSelected);
                break;
            case R.id.button_view:
                //здесь метод отправки запроса к БД

                break;
        }
    }

    private void checkTextViewDateSelected(){
        if (firstDateChosen && lastDateChosen){
            checkSelectedDateIsGreaterToday();
            checkFirstDateSelectedIsGreaterLastDateSelected();
            setTVChosen(0);
        }
    }

    private void checkFirstDateSelectedIsGreaterLastDateSelected() {
        if (firstDateSelected.after(lastDateSelected)){
            firstDateSelected.setTimeInMillis(lastDateSelected.getTimeInMillis());
            Toast.makeText(getActivity(), R.string.alarm_first_date_is_greater_last_date,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSelectedDateIsGreaterToday() {
        Calendar toDay = Calendar.getInstance();

        if (firstDateSelected.after(toDay)){
            firstDateSelected.setTimeInMillis(toDay.getTimeInMillis());
            Toast.makeText(getActivity(), R.string.alarm_date_is_greater_today,
                    Toast.LENGTH_SHORT).show();
        }
        if (lastDateSelected.after(toDay)){
            lastDateSelected.setTimeInMillis(toDay.getTimeInMillis());
            Toast.makeText(getActivity(), R.string.alarm_date_is_greater_today,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case CHANGE_DATE:
                date = data.getLongExtra(DATE_SELECTED, 0);
                int flag = data.getIntExtra(BUTTON_SELECTED, 0);
                boolean chosen = data.getBooleanExtra(CHOSEN, false);
                setDate(flag, chosen);
                break;
        }
    }

    private void startDatePickerDialog(int flag){
        DialogFragment changeDate = new DatePickerFragment();
        Bundle args = new Bundle(1);
        args.putInt(BUTTON_SELECTED, flag);
        changeDate.setArguments(args);
        changeDate.setTargetFragment(TabFragmentDailyStat.this, CHANGE_DATE);
        changeDate.show(getFragmentManager(), "DatePicker");
    }

    private void setDate(int flag, boolean chosen){
        switch (flag) {
            case flagFirstDateSelected:
                firstDateSelected = Calendar.getInstance();
                firstDateSelected.setTimeInMillis(date);
                firstDateChosen = chosen;
                setTVChosen(flag);
                checkTextViewDateSelected();
                break;
            case flagLastDateSelected:
                lastDateSelected = Calendar.getInstance();
                lastDateSelected.setTimeInMillis(date);
                lastDateChosen = chosen;
                setTVChosen(flag);
                checkTextViewDateSelected();
        }
    }
}
