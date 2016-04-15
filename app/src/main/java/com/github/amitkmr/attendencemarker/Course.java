package com.github.amitkmr.attendencemarker;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RAHUL on 01-04-2016.
 */
public class Course extends AppCompatActivity implements DeleteWarning.onSomeEventListener{

    TextView courselist;
    private DBHelper mydb ;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mydb = new DBHelper(this);

        ArrayList<String> courses = new ArrayList<String>();
        courses = mydb.getAllCoursesID();

        int N = courses.size();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.courses);

        for (int i = 0; i < N; i++) {
                final String text = courses.get(i);
                Object objText = text;

                final TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.course_border);
                GradientDrawable drawable = (GradientDrawable) textView.getBackground();
                drawable.setColor(Color.rgb(255, 255, 255));
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textView.getLayoutParams();
                lp.height = 180;
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

                textView.setText(text);   //course-info
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete_24dp, 0);
                textView.setPadding(20, 20, 20, 20);
                linearLayout.addView(textView, linearLayout.getChildCount() - 1);

            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= textView.getRight() - textView.getTotalPaddingRight()) {
                            // your action for drawable click event
                            FragmentManager fm = getFragmentManager();
                            DeleteWarning dialogFragment = new DeleteWarning ();
                            dialogFragment.show(fm, "Sample Fragment");
                            id = text;
                            return true;
                        }
                        else{
                            Intent nextScreen = new Intent(getApplicationContext(), Attendance.class);
                            nextScreen.putExtra("id", text);
                            startActivity(nextScreen);
                        }
                    }
                    return true;
                }
            });

//                textView.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        Intent nextScreen = new Intent(getApplicationContext(), Attendance.class);
//                        nextScreen.putExtra("id", text);
//                        startActivity(nextScreen);
//                    }
//                });




        }


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Course List");
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

    public void onClickB (View v) {
        Intent nextScreen = new Intent(getApplicationContext(), Fill_Course.class);
        startActivity(nextScreen);
    }

    public void someEvent(String s) {
        if(s.matches("yes")){
            if (mydb.deleteCourse(id)) {
                Toast.makeText(Course.this, "Course Deleted", Toast.LENGTH_SHORT).show();
                Intent nextScreen = new Intent(getApplicationContext(), Course.class);
                startActivity(nextScreen);
            } else
                Toast.makeText(Course.this, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            return;
        }
    }
}
