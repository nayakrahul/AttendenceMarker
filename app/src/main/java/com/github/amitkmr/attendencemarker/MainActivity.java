package com.github.amitkmr.attendencemarker;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBHelper mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);

        ArrayList<String> courses = new ArrayList<String>();
        courses = mydb.getAllCoursesID();

        int N = courses.size();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main);

        for (int i = 0; i < N; i++) {
                final String text = courses.get(i);
                Object objText = text;

                // Create LinearLayout
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.course_border);
                GradientDrawable drawable = (GradientDrawable) textView.getBackground();
                drawable.setColor(Color.rgb(255, 255, 255));
                float scale = this.getResources().getDisplayMetrics().density;
                int pixels = (int) (220 * scale + 0.5f);
                textView.setLayoutParams(new LinearLayout.LayoutParams(pixels,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textView.getLayoutParams();
                lp.height = 250;
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                textView.setText(text);   //course-info
                textView.setPadding(20, 30, 20, 30);

                ll.addView(textView);

                TextView perCentage = new TextView(this);
                perCentage.setBackgroundResource(R.drawable.course_border);
                GradientDrawable drawable1 = (GradientDrawable) perCentage.getBackground();
                drawable1.setColor(Color.rgb(255, 255, 255));
                int pixels1 = (int) (105 * scale + 0.5f);
                perCentage.setLayoutParams(new LinearLayout.LayoutParams(pixels1,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) perCentage.getLayoutParams();
                lp1.height = 250;
                perCentage.setLayoutParams(lp1);
                perCentage.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                perCentage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                perCentage.setPadding(20, 30, 20, 30);
                int percentage = calculatePercenatge(text);
                perCentage.setText(percentage+"%");
                ll.addView(perCentage);

                linearLayout.addView(ll, linearLayout.getChildCount() - 1);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)textView.getLayoutParams();
                params.setMargins(0, 20, 0, 20);
                textView.setLayoutParams(params);

                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)perCentage.getLayoutParams();
                params1.setMargins(0, 20, 0, 20);
                perCentage.setLayoutParams(params1);

                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent nextScreen = new Intent(getApplicationContext(), AttendanceDetails.class);
                        nextScreen.putExtra("id", text);
                        startActivity(nextScreen);
                    }
                });

                perCentage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent nextScreen = new Intent(getApplicationContext(), AttendanceDetails.class);
                        nextScreen.putExtra("id", text);
                        startActivity(nextScreen);
                    }
                });

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        setTitle("Dashboard");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        } else if (id == R.id.nav_courses) {
            Intent nextScreen = new Intent(getApplicationContext(), Course.class);
            startActivity(nextScreen);
        } else if (id == R.id.nav_aboutus) {
            Intent nextScreen = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(nextScreen);

        }
//        else if (id == R.id.nav_share) {
//            // do something
//        } else if (id == R.id.nav_send) {
//            // do something
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public int calculatePercenatge(String id){
        int presentCount = 0, absentCount = 0;
        ArrayList<Integer> attendanceList = new ArrayList<Integer>();
        attendanceList = mydb.getCoursesColumnAttendance(id);
        for(Integer i : attendanceList){
            if(i == 1)
                presentCount++;
            else
                absentCount++;
        }
        if(absentCount+presentCount == 0){
            return 0;
        }
        int percentage = presentCount*100/(presentCount+absentCount);
        return percentage;
    }


}


