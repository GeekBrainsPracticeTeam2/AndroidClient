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

    private void setDate(long date, int flag, boolean chosen){
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
