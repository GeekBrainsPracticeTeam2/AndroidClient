package com.example.alex.androidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.example.alex.androidclient.adapter.RecyclerViewAdapterDailyStat;
import com.example.alex.androidclient.models.DailyStatistics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.alex.androidclient.DatePickerFragment.BUTTON_SELECTED;
import static com.example.alex.androidclient.DatePickerFragment.CHOSEN;
import static com.example.alex.androidclient.DatePickerFragment.DATE_SELECTED;

/**
 * Created by alex on 03.03.17.
 */

public class TabFragmentDailyStat extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener{
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Spinner spinnerSites, spinnerPersons;
    private Button bView, bFirstDateSelected, bLastDateSelected;
    private TextView tvFirstDateSelected, tvLastDateSelected;
    private RecyclerView recyclerView;

    private RecyclerViewAdapterDailyStat adapter;

    private Calendar firstDateSelected, lastDateSelected;

    private long date;

    private boolean firstDateChosen, lastDateChosen = false;

    private int selectedSite, selectedPerson;

    private static final int flagFirstDateSelected = R.id.textview_first_date_selected;
    private static final int flagLastDateSelected = R.id.textview_last_date_selected;
    private static final int CHANGE_DATE = 2;

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_FIRST_DATE = "firstDate";
    public static final String APP_PREFERENCES_LAST_DATE = "lastDate";
    private SharedPreferences mSettings;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    private MyApp app;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = ((MyApp)getActivity().getApplicationContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_stat_tab_fragment_layout, container, false);
        initView(view);
        setSpinnerSites();
        setSpinnerPersons();
        buttonBehavoir();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(APP_PREFERENCES_FIRST_DATE, firstDateSelected.getTimeInMillis());
        editor.putLong(APP_PREFERENCES_LAST_DATE, lastDateSelected.getTimeInMillis());
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSettings.contains(APP_PREFERENCES_FIRST_DATE) &&
                mSettings.contains(APP_PREFERENCES_LAST_DATE)) {

            firstDateSelected.setTimeInMillis(mSettings.getLong(APP_PREFERENCES_FIRST_DATE, 0));
            lastDateSelected.setTimeInMillis(mSettings.getLong(APP_PREFERENCES_LAST_DATE, 0));
            String firstDate = sdf.format(firstDateSelected.getTime());
            String lastDate = sdf.format(lastDateSelected.getTime());
            tvFirstDateSelected.setText(firstDate);
            tvLastDateSelected.setText(lastDate);
        }
    }

    private void initView(View view){
        spinnerSites = (Spinner)view.findViewById(R.id.spinner_sites);
        spinnerPersons = (Spinner)view.findViewById(R.id.spinner_person);

        bView =(Button)view.findViewById(R.id.button_view);
        bFirstDateSelected = (Button)view.findViewById(R.id.first_date_selected);
        bLastDateSelected = (Button)view.findViewById(R.id.last_date_selected);

        tvFirstDateSelected = (TextView)view.findViewById(R.id.textview_first_date_selected);
        tvLastDateSelected = (TextView)view.findViewById(R.id.textview_last_date_selected);

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
                    Date startDate = new Date(firstDateSelected.getTimeInMillis());
                    app.setStartDate(startDate);

                    tvFirstDateSelected.setText(firstDate);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case flagLastDateSelected:
                try {
                    String lastDate = sdf.format(lastDateSelected.getTime());
                    Date finishDate = new Date(lastDateSelected.getTimeInMillis());
                    app.setFinishDate(finishDate);

                    tvLastDateSelected.setText(lastDate);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setSpinnerSites(){
        String[] siteUrl = app.getSiteUrl();

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, siteUrl);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSites.setAdapter(adapter);
        spinnerSites.setOnItemSelectedListener(this);
    }

    private void setSpinnerPersons(){
        String[] namePerson= app.getNamePerson();

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, namePerson);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerPersons.setAdapter(adapter);
        spinnerPersons.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "Start onItemSelected");

        Spinner spinner = (Spinner)parent;
        switch (spinner.getId()){
            case R.id.spinner_sites:
                Log.d(LOG_TAG, "Start onItemSelected case " + R.id.spinner_sites);
                Log.d(LOG_TAG, "item selected = " + position);
                Log.d(LOG_TAG, "item selected = " + spinnerSites.getItemAtPosition(position));

                if(adapter != null && position != selectedSite) {
                    adapter.setDailyStatisticsList(app.prepareDailyStatForRecycler(position, selectedPerson));
                    selectedSite = position;
                }

                break;
            case R.id.spinner_person:
                Log.d(LOG_TAG, "Start onItemSelected case " + R.id.spinner_person);
                Log.d(LOG_TAG, "item selected = " + position);
                Log.d(LOG_TAG, "item selected = " + spinnerPersons.getItemAtPosition(position));

                if(adapter != null && position != selectedPerson) {
                    adapter.setDailyStatisticsList(app.prepareDailyStatForRecycler(selectedSite, position));
                    selectedPerson = position;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initRecyclerView(){
        Log.d(LOG_TAG, "Start initRecyclerView");

        app.setStartDate(new Date(firstDateSelected.getTimeInMillis()));
        app.setFinishDate(new Date(lastDateSelected.getTimeInMillis()));
        List<DailyStatistics> dailyStatListForRecycler = app.
                prepareDailyStatForRecycler(selectedSite, selectedPerson);

        Log.d(LOG_TAG, "dailyStatListForRecycler = " + dailyStatListForRecycler);
        Log.d(LOG_TAG, "firstDateSelected = " + firstDateSelected);
        Log.d(LOG_TAG, "lastDateSelected = " + lastDateSelected);
        Log.d(LOG_TAG, "selectedSite = " + selectedSite);
        Log.d(LOG_TAG, "selectedPerson = " + selectedPerson);

        setRecyclerView(dailyStatListForRecycler);
        Log.d(LOG_TAG, "End initRecyclerView");
    }

    private void setRecyclerView(List<DailyStatistics> dailyStatListForRecycler){
        Log.d(LOG_TAG, "Start setRecyclerView");

        Context context = getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if(app.getDailyStatisticses() != null) {
            adapter = new RecyclerViewAdapterDailyStat(dailyStatListForRecycler, context,
                    selectedSite, selectedPerson);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }

        Log.d(LOG_TAG, "selectedSite = " + selectedSite);
        Log.d(LOG_TAG, "selectedPerson = " + selectedPerson);
        Log.d(LOG_TAG, "End setRecyclerView");
    }

    private void buttonBehavoir(){
        bView.setOnClickListener(this);
        tvFirstDateSelected.setOnClickListener(this);
        tvLastDateSelected.setOnClickListener(this);

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

    private void checkTVDateSelected(){
        if (firstDateChosen && lastDateChosen){
            checkSelectedDateIsGreaterToday();
            checkFirstDateSelectedIsGreaterLastDateSelected();
            setTVChosen(0);
            initRecyclerView();
        }
    }

    private void checkFirstDateSelectedIsGreaterLastDateSelected() {
        if (firstDateSelected.after(lastDateSelected)){
            firstDateSelected.setTimeInMillis(lastDateSelected.getTimeInMillis());
            setTVChosen(flagFirstDateSelected);
            Toast.makeText(getActivity(), R.string.alarm_first_date_is_greater_last_date,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSelectedDateIsGreaterToday() {
        Calendar toDay = Calendar.getInstance();

        if (firstDateSelected.after(toDay)){
            firstDateSelected.setTimeInMillis(toDay.getTimeInMillis());
            setTVChosen(flagFirstDateSelected);
            Toast.makeText(getActivity(), R.string.alarm_date_is_greater_today,
                    Toast.LENGTH_SHORT).show();
        }
        if (lastDateSelected.after(toDay)){
            lastDateSelected.setTimeInMillis(toDay.getTimeInMillis());
            setTVChosen(flagLastDateSelected);
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
//                handleDate(firstDateSelected, flag, firstDateChosen, chosen);
                firstDateSelected = Calendar.getInstance();
                firstDateSelected.setTimeInMillis(date);
                firstDateChosen = chosen;
                setTVChosen(flag);
                checkTVDateSelected();
                break;
            case flagLastDateSelected:
//                handleDate(lastDateSelected, flag, lastDateChosen, chosen);
                lastDateSelected = Calendar.getInstance();
                lastDateSelected.setTimeInMillis(date);
                lastDateChosen = chosen;
                setTVChosen(flag);
                checkTVDateSelected();
        }
    }
}
