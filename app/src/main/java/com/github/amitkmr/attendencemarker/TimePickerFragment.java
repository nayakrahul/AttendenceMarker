package com.github.amitkmr.attendencemarker;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
/**
 * Created by RAHUL on 04-04-2016.
 */
public class TimePickerFragment extends DialogFragment {
    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    final String LOG_TAG = "myLogs";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.time_dialog, container, false);
            final TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);

            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final int hour = timePicker.getHour();
                    final int min = timePicker.getMinute();
                    someEventListener.someEvent(String.valueOf(hour)+":"+String.valueOf(min));
                    dismiss();
                }
            });

            return rootView;
        }
}

