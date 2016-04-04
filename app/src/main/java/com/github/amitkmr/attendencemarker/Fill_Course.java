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

/**
 * Created by RAHUL on 03-04-2016.
 */
public class Fill_Course extends AppCompatActivity implements TimePickerFragment.onSomeEventListener {
    TextView id;
    TextView name;
    private DBHelper mydb ;
    String time1, time2;

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

    public void onClick(View v)
    {
            String[] parts1 = time1.split(":");
            String[] parts2 = time2.split(":");
            if (mydb.insertCourse(id.getText().toString(), name.getText().toString(),
                    Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]), Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]))){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                }
                else{
                Intent intent = new Intent(getApplicationContext(),Course.class);
                startActivity(intent);
                }
    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            String checkText = checkBox.getText().toString();
            Button b1,b2;
            switch (checkText) {
                case "Mon":
                    b1 = (Button) findViewById(R.id.mon_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.mon_end_button);
                    b2.setEnabled(true);
                case "Tue":
                    b1 = (Button) findViewById(R.id.tue_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.tue_end_button);
                    b2.setEnabled(true);
                case "Wed":
                    b1 = (Button) findViewById(R.id.wed_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.wed_end_button);
                    b2.setEnabled(true);
                case "Thu":
                    b1 = (Button) findViewById(R.id.thu_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.thu_end_button);
                    b2.setEnabled(true);
                case "Fri":
                    b1 = (Button) findViewById(R.id.fri_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.fri_end_button);
                    b2.setEnabled(true);
                case "Sat":
                    b1 = (Button) findViewById(R.id.sat_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.sat_end_button);
                    b2.setEnabled(true);
                case "Sun":
                    b1 = (Button) findViewById(R.id.sun_start_button);
                    b1.setEnabled(true);
                    b2 = (Button) findViewById(R.id.sun_end_button);
                    b2.setEnabled(true);
            }
        }
        else{
                String checkText = checkBox.getText().toString();
                Button b1,b2;
                switch (checkText) {
                    case "Mon":
                        b1 = (Button) findViewById(R.id.mon_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.mon_end_button);
                        b2.setEnabled(false);
                    case "Tue":
                        b1 = (Button) findViewById(R.id.tue_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.tue_end_button);
                        b2.setEnabled(false);
                    case "Wed":
                        b1 = (Button) findViewById(R.id.wed_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.wed_end_button);
                        b2.setEnabled(false);
                    case "Thu":
                        b1 = (Button) findViewById(R.id.thu_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.thu_end_button);
                        b2.setEnabled(false);
                    case "Fri":
                        b1 = (Button) findViewById(R.id.fri_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.fri_end_button);
                        b2.setEnabled(false);
                    case "Sat":
                        b1 = (Button) findViewById(R.id.sat_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.sat_end_button);
                        b2.setEnabled(false);
                    case "Sun":
                        b1 = (Button) findViewById(R.id.sun_start_button);
                        b1.setEnabled(false);
                        b2 = (Button) findViewById(R.id.sun_end_button);
                        b2.setEnabled(false);
                }
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
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.mon_end_text);
                    end_time.setText(s);
                    time2 = s;
                }
                break;
            case "Tuesday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.tue_start_text);
                    start_time.setText(s);
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.tue_end_text);
                    end_time.setText(s);
                    time2 = s;
                }
                break;
            case "Wednesday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.wed_start_text);
                    start_time.setText(s);
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.wed_end_text);
                    end_time.setText(s);
                    time2 = s;
                }
                break;
            case "Thursday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.thu_start_text);
                    start_time.setText(s);
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.thu_end_text);
                    end_time.setText(s);
                    time2 = s;
                }
                break;
            case "Friday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.fri_start_text);
                    start_time.setText(s);
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.fri_end_text);
                    end_time.setText(s);
                    time2 = s;
                }
                break;
            case "Saturday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.sat_start_text);
                    start_time.setText(s);
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.sat_end_text);
                    end_time.setText(s);
                    time2 = s;
                }
                break;
            case "Sunday":
                if (flag == 0) {
                    start_time = (TextView) findViewById(R.id.sun_start_text);
                    start_time.setText(s);
                    time1 = s;
                } else {
                    end_time = (TextView) findViewById(R.id.sun_end_text);
                    end_time.setText(s);
                    time2 = s;
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
