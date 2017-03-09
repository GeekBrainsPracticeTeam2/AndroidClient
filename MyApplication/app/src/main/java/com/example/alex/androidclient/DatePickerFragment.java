package com.example.alex.androidclient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by alex on 05.03.17.
 */

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener{
    public static final String DATE_SELECTED = "date";
    public static final String BUTTON_SELECTED = "flag";
    int flag;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        flag = getArguments().getInt("flag");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        long date = view.getCalendarView().getDate();
        Intent i = new Intent();
        i.putExtra (DATE_SELECTED, date);
        i.putExtra(BUTTON_SELECTED, flag);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }
}
