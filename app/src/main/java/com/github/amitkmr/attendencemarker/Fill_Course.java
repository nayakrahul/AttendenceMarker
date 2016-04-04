package com.github.amitkmr.attendencemarker;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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

    int flag;
    public void onClick_start_time(View v)
    {
        flag = 0;
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    public void onClick_end_time(View v)
    {
        flag = 1;
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    private TextView start_time;
    private TextView end_time;
    @Override
    public void someEvent(String s) {
        if (flag == 0) {
            start_time = (TextView) findViewById(R.id.time1);
            start_time.setText(s);
            time1 = s;
        }
        else {
            end_time = (TextView) findViewById(R.id.time2);
            end_time.setText(s);
            time2 = s;
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
