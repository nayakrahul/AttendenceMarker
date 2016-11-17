package com.github.amitkmr.attendencemarker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.AttributeSet;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RAHUL on 01-04-2016.
 */
public class AttendanceDetails extends AppCompatActivity{
    private DBHelper mydb ;
    private TextView desc;
    private String data;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        mydb = new DBHelper(this);

        data = mydb.getCoursesColumnName(id);

        ArrayList<String> dates = new ArrayList<String>();
        dates = mydb.getCoursesColumnDate(id);

        ArrayList<Integer> attendance = new ArrayList<Integer>();
        attendance = mydb.getCoursesColumnAttendance(id);

        // set the course Name
        desc = (TextView) findViewById(R.id.courseName);
        desc.setText(data);

        int N = dates.size();

        final LinearLayout lm = (LinearLayout) findViewById(R.id.attendance_detail);

        // create the layout params that will be used to define how your
        // create attendance list
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

        //Create four
        int totalScheduledClasses = N;
        int attendedClasses = 0;
        for(int i = N-1; i >= 0; i--)
        {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            TextView date = new TextView(this);
            float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (230 * scale + 0.5f);
            date.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            pixels, RadioGroup.LayoutParams.WRAP_CONTENT));
            date.setText(dates.get(i));
            date.setBackgroundResource(R.drawable.course_border);
            date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            int pp = (int) (10 * scale + 0.5f);
            date.setPadding(pp, pp, pp, pp);
            date.setGravity(Gravity.CENTER);
            ll.addView(date);

            // Create TextView
            TextView status = new TextView(this);
            pixels = (int) (130 * scale + 0.5f);
            status.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            pixels, RadioGroup.LayoutParams.WRAP_CONTENT));
            if(attendance.get(i) == 1){
                attendedClasses++;
                status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp, 0, 0, 0);
            }
            else{
                status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_24dp, 0, 0, 0);
            }
            status.setBackgroundResource(R.drawable.course_border);
            status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            int center = (int) (55 * scale + 0.5f);
            status.setPadding(center, pp, pp, pp);
            status.setGravity(Gravity.CENTER | Gravity.RIGHT);
            ll.addView(status);

            lm.addView(ll);
        }


        //make donut chart of attendance
        DonutChart donutChart = (DonutChart) findViewById(R.id.donutChart);
        donutChart.pieChartVariableA = totalScheduledClasses;
        donutChart.pieChartVariableB = attendedClasses;


        setTitle("   " + id);
        // Back button to reach to home activity

        // return to to the home screen
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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


}
