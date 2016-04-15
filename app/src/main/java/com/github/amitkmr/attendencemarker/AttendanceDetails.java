package com.github.amitkmr.attendencemarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RAHUL on 01-04-2016.
 */
public class AttendanceDetails extends AppCompatActivity {
    private DBHelper mydb ;
    private TextView desc;
    private String id;
    private String data;
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

        TextView temp1 = new TextView(this);
        TextView temp2 = new TextView(this);
        temp1 = (TextView) findViewById(R.id.classes);
        temp2 = (TextView) findViewById(R.id.attendance);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.attendance_detail);
        for(int i = 0; i < N; i++){
            TextView textView = new TextView(this);

            final float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (250 * scale + 0.5f);
            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                    pixels, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp1.addRule(RelativeLayout.BELOW, temp1.getId());

            textView.setLayoutParams(lp1);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setBackgroundResource(R.drawable.course_border);
            String s = dates.get(i);
            textView.setText(s);
            int pp = (int) (10 * scale + 0.5f);
            textView.setPadding(pp, pp, pp, pp);
            textView.setGravity(Gravity.CENTER);
            textView.setId(View.generateViewId());

            temp1 = textView;
            relativeLayout.addView(textView, relativeLayout.getChildCount() - 1);

            TextView PresentAbsent = new TextView(this);
            if(attendance.get(i)==1)
                PresentAbsent.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_done_24dp, 0, 0, 0);
            else
                PresentAbsent.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_close_24dp, 0, 0, 0);

            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                    pixels, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.BELOW, temp2.getId());
            PresentAbsent.setLayoutParams(lp2);
            PresentAbsent.setBackgroundResource(R.drawable.course_border);
            textView.setPadding(pp, pp, pp, pp);
            textView.setGravity(Gravity.CENTER);
            textView.setId(View.generateViewId());
            temp2 = PresentAbsent;
            relativeLayout.addView(PresentAbsent, relativeLayout.getChildCount() - 1);

        }

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
