package com.trunghoang.todoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    public static DatePickerFragment newInstance() {
        return new DatePickerFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener;
        final Fragment parentFragment = getParentFragment();
        if (parentFragment == null) {
            listener = (DatePickerDialog.OnDateSetListener) getContext();
        } else {
            listener = (DatePickerDialog.OnDateSetListener) parentFragment;
        }
        return new DatePickerDialog(getContext(), listener, year, month, day);
    }
}
