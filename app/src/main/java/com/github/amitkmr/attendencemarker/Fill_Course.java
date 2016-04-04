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
            if (mydb.insertCourse(id.getText().toString(), name.getText().toString())){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                }
                else{
                Intent intent = new Intent(getApplicationContext(),Course.class);
                startActivity(intent);
                }
    }

    public void onClick_start_time(View v)
    {
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    public void onClick_end_time(View v)
    {
        FragmentManager fm = getFragmentManager();
        TimePickerFragment dialogFragment = new TimePickerFragment ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    private TextView name3;
    @Override
    public void someEvent(String s) {
        name3= (TextView) findViewById(R.id.time1);
        name3.setText(s);
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
