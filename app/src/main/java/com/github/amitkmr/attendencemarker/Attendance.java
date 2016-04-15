package com.github.amitkmr.attendencemarker;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RAHUL on 04-04-2016.
 */
public class Attendance extends AppCompatActivity implements GPSData.onSyncCoordinate {
    private DBHelper mydb ;
    private TextView desc;
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

        // Add Delete Course Button
        Button btn = new Button(this);
        btn.setText("DELETE COURSE");
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, temp.getId());
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        btn.setLayoutParams(lp);
        btn.setPadding(10, 0, 10, 0);
        btn.setBackgroundColor(0x9BDDFF);
        relativeLayout.addView(btn, relativeLayout.getChildCount() - 1);

        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       // put code on click operation
                                       if(mydb.deleteCourse(id)) {
                                           Toast.makeText(Attendance.this, "Course Deleted", Toast.LENGTH_SHORT).show();
                                           Intent nextScreen = new Intent(getApplicationContext(), Course.class);
                                           startActivity(nextScreen);
                                       }
                                       else
                                           Toast.makeText(Attendance.this, "Failed", Toast.LENGTH_SHORT).show();

                                   }
                               });

                // get the latitude and longitude from the table
                CoordinatesSetOrNot(id);

        // return to to the home screen
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //set titile in the app bar
        setTitle(id);
    }

    public void CoordinatesSetOrNot(String id){
        String data1, data2;
        data1 = mydb.getCourseLocationLatitude(id);
        TextView showLatitude = (TextView)findViewById(R.id.showLatitude);
        showLatitude.setText(data1);

        data2 = mydb.getCourseLocationLongitude(id);
        TextView showLongitude = (TextView)findViewById(R.id.showLongitude);
        showLongitude.setText(data2);

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
}
