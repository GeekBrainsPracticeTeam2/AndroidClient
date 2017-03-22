package com.example.alex.androidclient.presenters;

import com.example.alex.androidclient.R;

import java.util.Calendar;

/**
 * Created by alex on 22.03.17.
 */

public class PresenterDailyStat {
    private static final int flagFirstDateSelected = R.id.first_date_selected;
    private static final int flagLastDateSelected = R.id.last_date_selected;

    private Calendar firstDateSelected, lastDateSelected;

    private boolean firstDateChosen, lastDateChosen = false;

    private long date;


}
