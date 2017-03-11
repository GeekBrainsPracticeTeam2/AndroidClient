package com.example.alex.androidclient;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.alex.androidclient.DatePickerFragment.BUTTON_SELECTED;
import static com.example.alex.androidclient.DatePickerFragment.CHOSEN;
import static com.example.alex.androidclient.DatePickerFragment.DATE_SELECTED;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentDailyStat extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener{
    private Spinner spinnerSites;
    private Button buttonView, buttonFirstDateSelected, buttonLastDateSelected;
    private TextView textViewFirstDateSelected, textViewLastDateSelected;
    private RecyclerView recyclerView;

    private Calendar firstDateSelected, lastDateSelected;

    private long date;

    private boolean firstDateChosen = false;
    private boolean lastDateChosen = false;

    private static final int flagFirstDateSelected = R.id.first_date_selected;
    private static final int flagLastDateSelected = R.id.last_date_selected;
    private static final int CHANGE_DATE = 2;
    private static final String TAG = "MyApp";

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

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
        buttonView =(Button)view.findViewById(R.id.button_view);
        buttonFirstDateSelected = (Button)view.findViewById(R.id.first_date_selected);
        buttonLastDateSelected = (Button)view.findViewById(R.id.last_date_selected);
        textViewFirstDateSelected = (TextView)view.findViewById(R.id.textview_first_date_selected);
        textViewLastDateSelected = (TextView)view.findViewById(R.id.textviews_last_date_selected);
        setTextViewDefault();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
    }

    private void setTextViewDefault() {
        textViewFirstDateSelected.setText(R.string.default_date);
        textViewLastDateSelected.setText(R.string.default_date);
    }

    private void setTextViewSelected(){
        if (firstDateSelected != null) {
            String firstDate = sdf.format(firstDateSelected.getTime());
            textViewFirstDateSelected.setText(firstDate);
        }
        if (lastDateSelected != null){
            String lastDate = sdf.format(lastDateSelected.getTime());
            textViewLastDateSelected.setText(lastDate);
        }
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
            setTextViewSelected();
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
        if (flagFirstDateSelected == flag){
            firstDateSelected = Calendar.getInstance();
            firstDateSelected.setTimeInMillis(date);
            firstDateChosen = chosen;
            setTextViewSelected();
            checkTextViewDateSelected();
            } else {
            lastDateSelected = Calendar.getInstance();
            lastDateSelected.setTimeInMillis(date);
            lastDateChosen = chosen;
            setTextViewSelected();
            checkTextViewDateSelected();
        }
    }
}
