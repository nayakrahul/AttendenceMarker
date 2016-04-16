package com.github.amitkmr.attendencemarker;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RAHUL on 04-04-2016.
 */
public class Attendance extends AppCompatActivity implements GPSData.onSyncCoordinate, DeleteWarning.onSomeEventListener {
    private DBHelper mydb ;
    private TextView desc, stats;
    private String id;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        mydb = new DBHelper(this);

        data = mydb.getCoursesColumnName(id);
        // set the course Name
        desc = (TextView) findViewById(R.id.courseName);
        desc.setText(data);

        //set attendance stats
        stats = (TextView) findViewById(R.id.attendance_score);
        stats.setText(getStats(id));

        ArrayList<String> days = new ArrayList<String>();
        days = mydb.getCoursesColumnDay(id);

        ArrayList<Integer> start_hour = new ArrayList<Integer>();
        start_hour = mydb.getCoursesColumnStartHr(id);

        ArrayList<Integer> start_min = new ArrayList<Integer>();
        start_min = mydb.getCoursesColumnStartMin(id);

        ArrayList<Integer> end_hour = new ArrayList<Integer>();
        end_hour = mydb.getCoursesColumnEndHr(id);

        ArrayList<Integer> end_min = new ArrayList<Integer>();
        end_min = mydb.getCoursesColumnEndMin(id);

        int N = days.size();

        final LinearLayout lm = (LinearLayout) findViewById(R.id.course_detail);

        // create the layout params that will be used to define how your
        //create the schedule
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < N; i++){
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            TextView schedule_day = new TextView(this);
            float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (180 * scale + 0.5f);
            schedule_day.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            pixels, RadioGroup.LayoutParams.WRAP_CONTENT));
            schedule_day.setText(days.get(i));
            schedule_day.setBackgroundResource(R.drawable.course_border);
            schedule_day.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            int pp = (int) (10 * scale + 0.5f);
            schedule_day.setPadding(pp, pp, pp, pp);
            schedule_day.setGravity(Gravity.CENTER);
            ll.addView(schedule_day);

            // Create TextView
            TextView schedule_time = new TextView(this);
            pixels = (int) (180 * scale + 0.5f);
            schedule_time.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            pixels, RadioGroup.LayoutParams.WRAP_CONTENT));
            String time = String.format("%02d", start_hour.get(i)) + ":" + String.format("%02d", start_min.get(i))
                    +" - "+String.format("%02d", end_hour.get(i)) + ":" + String.format("%02d", end_min.get(i));
            schedule_time.setText(time);
            schedule_time.setBackgroundResource(R.drawable.course_border);
            schedule_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            schedule_time.setPadding(pp, pp, pp, pp);
            schedule_time.setGravity(Gravity.CENTER);
            ll.addView(schedule_time);

            lm.addView(ll);

        }

        //add delete button
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        final Button btn = new Button(this);
        btn.setLayoutParams(
                new LinearLayout.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT));
        btn.setText("DELETE COURSE");
        // set the layoutParams on the button
        btn.setLayoutParams(params);
        float scale = this.getResources().getDisplayMetrics().density;
        int pixels1 = (int) (20 * scale + 0.5f);
        int pixels2 = (int) (30 * scale + 0.5f);
        btn.setPadding(pixels1, pixels2, pixels1, pixels2);

        // Set click listener for button
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // put code on click operation
                FragmentManager fm = getFragmentManager();
                DeleteWarning dialogFragment = new DeleteWarning ();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)btn.getLayoutParams();
        params1.setMargins(320, 50, 320, 100);
        btn.setLayoutParams(params1);

        //Add button to LinearLayout
        ll.addView(btn);
        //Add button to LinearLayout defined in XML
        lm.addView(ll);

        // check the latitude and longitude from the table
        CoordinatesSetOrNot(id);

        //set titile in the app bar
        setTitle("   " + id);
        // return to to the home screen
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void CoordinatesSetOrNot(String id){
        String data1, data2;
        data1 = mydb.getCourseLocationLatitude(id);
        data2 = mydb.getCourseLocationLongitude(id);

        if(data1.matches("Latitude Not Set") || data2.matches("Longitude Not Set")){
            Button btn=(Button)findViewById(R.id.coordinate_check);
            btn.setText("COORDINATES NOT SYNCED ! SYNC NOW");
        }

    }

    public void onClickSyncCoordinate(View v)   {
        FragmentManager fm = getFragmentManager();
        GPSData dialogFragment = new GPSData ();
        dialogFragment.show(fm, "Sample Fragment");
    }

    public void syncCoordinate(String s1, String s2) {
        if(s1 != null && s2 != null) {
            if (mydb.updateCoordinates(id, data, s1, s2)) {
                Toast.makeText(this, "Coordinates Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
            Button btn = (Button) findViewById(R.id.coordinate_check);
            btn.setText("EDIT COORDINATES OF VENUE");
        }
        else{
            Toast.makeText(this, "Coordinates Not Found", Toast.LENGTH_SHORT).show();
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



    public void onClickAttendanceDetails(View v) {

        Intent nextScreen = new Intent(getApplicationContext(), AttendanceDetails.class);
        nextScreen.putExtra("id", id);
        startActivity(nextScreen);

    }

    public String getStats(String id){
        int presentCount = 0, absentCount = 0;
        ArrayList<Integer> attendanceList = new ArrayList<Integer>();
        attendanceList = mydb.getCoursesColumnAttendance(id);
        for(Integer i : attendanceList){
            if(i == 1)
                presentCount++;
            else
                absentCount++;
        }

       String ret_val = presentCount+"/"+(absentCount+presentCount);
        return ret_val;
    }

    public void someEvent(String s) {
        if(s.matches("yes")){
            if (mydb.deleteCourse(id)) {
                    Toast.makeText(Attendance.this, "Course Deleted", Toast.LENGTH_SHORT).show();
                    Intent nextScreen = new Intent(getApplicationContext(), Course.class);
                    startActivity(nextScreen);
                } else
                    Toast.makeText(Attendance.this, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            return;
        }
    }
}
