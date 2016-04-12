package com.github.amitkmr.attendencemarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        data = mydb.getCoursesColumnName(id);



        // set the course Name

        desc = (TextView) findViewById(R.id.courseName);
        desc.setText(data);

        ArrayList<String> day = new ArrayList<String>();
        day = mydb.getCoursesColumnDay(id);

        ArrayList<Integer> start_hour = new ArrayList<Integer>();
        start_hour = mydb.getCoursesColumnStartHr(id);

        ArrayList<Integer> start_min = new ArrayList<Integer>();
        start_min = mydb.getCoursesColumnStartMin(id);

        ArrayList<Integer> end_hour = new ArrayList<Integer>();
        end_hour = mydb.getCoursesColumnEndHr(id);

        ArrayList<Integer> end_min = new ArrayList<Integer>();
        end_min = mydb.getCoursesColumnEndMin(id);

        int N = day.size();
        int below = R.id.schedule;
        TextView temp = new TextView(this);
        temp = (TextView) findViewById(R.id.schedule);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.course_detail);
        for(int i = 0; i < N; i++){
            TextView textView = new TextView(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.BELOW, temp.getId());
            textView.setLayoutParams(lp);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            String s = day.get(i)+" : " + String.format("%02d", start_hour.get(i)) + ":" + String.format("%02d", start_min.get(i))
                        +" - "+String.format("%02d", end_hour.get(i)) + ":" + String.format("%02d", end_min.get(i));
            textView.setText(s);
            textView.setPadding(20, 10, 0, 0);
            textView.setId(View.generateViewId());
            relativeLayout.addView(textView, relativeLayout.getChildCount() - 1);
            temp = textView;
        }

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
