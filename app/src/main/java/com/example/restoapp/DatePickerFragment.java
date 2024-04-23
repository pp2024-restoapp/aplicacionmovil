package com.example.restoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    public DateSelectionListener dateListener;


    public interface DateSelectionListener {
        void onDateSelected(int year, int month, int day);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (dateListener != null) {
                    dateListener.onDateSelected(year, month, dayOfMonth);
                }
            }
        }, year, month, day);

    }

    public void setDateListener(DateSelectionListener listener) {
        dateListener = listener;
    }
}
