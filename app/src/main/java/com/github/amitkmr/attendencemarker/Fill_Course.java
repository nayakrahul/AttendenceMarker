package com.github.amitkmr.attendencemarker;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by RAHUL on 03-04-2016.
 */
public class Fill_Course extends AppCompatActivity implements TimePickerFragment.onSomeEventListener {
    TextView id;
    TextView name;
    private DBHelper mydb ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_course);

        id = (TextView) findViewById(R.id.editId);
        name = (TextView) findViewById(R.id.editName);
        mydb = new DBHelper(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    CheckBox checkBox_mon, checkBox_tue, checkBox_wed, checkBox_thu, checkBox_fri, checkBox_sat, checkBox_sun;
    public void onClick(View v)
    {
            checkBox_mon = (CheckBox) findViewById(R.id.checkBoxMon);
            checkBox_tue = (CheckBox) findViewById(R.id.checkBoxTue);
            checkBox_wed = (CheckBox) findViewById(R.id.checkBoxWed);
            checkBox_thu = (CheckBox) findViewById(R.id.checkBoxThu);
            checkBox_fri = (CheckBox) findViewById(R.id.checkBoxFri);
            checkBox_sat = (CheckBox) findViewById(R.id.checkBoxSat);
            checkBox_sun = (CheckBox) findViewById(R.id.checkBoxSun);
            String time1, time2;
            TextView timeText1, timeText2;
            if(checkBox_mon.isChecked()){
                timeText1 = (TextView) findViewById(R.id.mon_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.mon_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Monday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
            if(checkBox_tue.isChecked()){
                timeText1 = (TextView) findViewById(R.id.tue_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.tue_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Tuesday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
            if(checkBox_wed.isChecked()){
                timeText1 = (TextView) findViewById(R.id.wed_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.wed_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Wednesday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
            if(checkBox_thu.isChecked()){
                timeText1 = (TextView) findViewById(R.id.thu_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.thu_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Thursday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
            if(checkBox_fri.isChecked()){
                timeText1 = (TextView) findViewById(R.id.fri_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.fri_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Friday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
            if(checkBox_sat.isChecked()){
                timeText1 = (TextView) findViewById(R.id.sat_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.sat_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Saturday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
            if(checkBox_sun.isChecked()){
                timeText1 = (TextView) findViewById(R.id.sun_start_text);
                time1 = timeText1.getText().toString();
                timeText2 = (TextView) findViewById(R.id.sun_end_text);
                time2 = timeText2.getText().toString();
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                mydb.insertCourse(id.getText().toString(), name.getText().toString(), "Sunday",
                        Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]),
                        Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            }
        Intent nextScreen = new Intent(getApplicationContext(), Course.class);
        startActivity(nextScreen);
    }

    //on playing with checkboxes
    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        boolean check;
        if (checkBox.isChecked())
            check = true;
        else
            check = false;
        String checkText = checkBox.getText().toString();
        Button b1,b2;
        switch (checkText) {
                case "Mon":
                    b1 = (Button) findViewById(R.id.mon_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.mon_end_button);
                    b2.setEnabled(check);
                case "Tue":
                    b1 = (Button) findViewById(R.id.tue_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.tue_end_button);
                    b2.setEnabled(check);
                case "Wed":
                    b1 = (Button) findViewById(R.id.wed_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.wed_end_button);
                    b2.setEnabled(check);
                case "Thu":
                    b1 = (Button) findViewById(R.id.thu_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.thu_end_button);
                    b2.setEnabled(check);
                case "Fri":
                    b1 = (Button) findViewById(R.id.fri_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.fri_end_button);
                    b2.setEnabled(check);
                case "Sat":
                    b1 = (Button) findViewById(R.id.sat_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.sat_end_button);
                    b2.setEnabled(check);
                case "Sun":
                    b1 = (Button) findViewById(R.id.sun_start_button);
                    b1.setEnabled(check);
                    b2 = (Button) findViewById(R.id.sun_end_button);
                    b2.setEnabled(check);
        }
    }

    int flag;
    String buttonText;
    public void onClick_start_time(View v)
    {
        flag = 0;
        Button b = (Button)v;
        buttonText = b.getText().toString();
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    public void onClick_end_time(View v)
    {
        flag = 1;
        Button b = (Button)v;
        buttonText = b.getText().toString();
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    private TextView start_time;
    private TextView end_time;
    @Override
    public void someEvent(String s) {
        switch (buttonText) {
            case "Monday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.mon_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.mon_end_text);
                    end_time.setText(s);
                }
                break;
            case "Tuesday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.tue_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.tue_end_text);
                    end_time.setText(s);
                }
                break;
            case "Wednesday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.wed_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.wed_end_text);
                    end_time.setText(s);
                }
                break;
            case "Thursday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.thu_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.thu_end_text);
                    end_time.setText(s);
                }
                break;
            case "Friday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.fri_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.fri_end_text);
                    end_time.setText(s);
                }
                break;
            case "Saturday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.sat_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.sat_end_text);
                    end_time.setText(s);
                }
                break;
            case "Sunday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.sun_start_text);
                    start_time.setText(s);
                } else {
                    end_time = (TextView) findViewById(R.id.sun_end_text);
                    end_time.setText(s);
                }
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
