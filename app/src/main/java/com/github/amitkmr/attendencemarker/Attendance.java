package com.github.amitkmr.attendencemarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RAHUL on 04-04-2016.
 */
public class Attendance extends AppCompatActivity {
    private DBHelper mydb ;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        mydb = new DBHelper(this);

        String data;
        data = mydb.getData(id);

        // set the course Name
        desc = (TextView) findViewById(R.id.courseName);
        desc.setText(data);

        // return to to the home screen
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //set titile in the app bar
        setTitle(id);
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

    public void onClickAttendanceDetails(View v) {
        Intent nextScreen = new Intent(getApplicationContext(), AttendanceDetails.class);
        startActivity(nextScreen);
    }
}
