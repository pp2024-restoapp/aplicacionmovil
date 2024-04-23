package com.example.restoapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    public TimeSelectionListener timeListener;

    public interface TimeSelectionListener {
        void onTimeSelected(int hour, int minute);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (timeListener != null) {
                    timeListener.onTimeSelected(hourOfDay, minute);
                }
            }
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(requireContext()));
    }

    public void setTimeListener(TimeSelectionListener listener) {
        timeListener = listener;
    }
}