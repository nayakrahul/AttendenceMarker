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
 * Created by RAHUL on 16-04-2016.
 */
public class DeleteWarning extends DialogFragment {
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
        View rootView = inflater.inflate(R.layout.delete_warning, container, false);
        Button button1 = (Button) rootView.findViewById(R.id.yes);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                someEventListener.someEvent("yes");
                dismiss();
            }
        });

        Button button2 = (Button) rootView.findViewById(R.id.no);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                someEventListener.someEvent("no");
                dismiss();
            }
        });

        return rootView;
    }

}
