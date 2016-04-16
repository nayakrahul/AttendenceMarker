package com.github.amitkmr.attendencemarker;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import android.widget.Toast;
import java.text.SimpleDateFormat;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.Timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;

import android.widget.Toast;
import java.util.Timer;


/**
 * Created by RAHUL on 14-04-2016.
 */

public class BackgroundService extends Service implements LocationListener {
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private DBHelper mydb ;


    private static BackgroundService inst;

    ArrayList<String> coursesAttended = new ArrayList<String>();
    ArrayList<String> coursesNotAttended = new ArrayList<String>();
    int prev_day;


    // Variable defined for the day schdule
    ArrayList<String> daySchedule = new ArrayList<String>();

    // the fetch of cuurent coordinates from the GPS data class
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String latitude="";
    String longitude="";



    @Override
    public void onCreate() {
        // The service is being created
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        runForeground();

        //getStarted();


        mydb = new DBHelper(this);
        //getStarted();
        // GPS DATA \//////////////////////////////////////
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        return mStartMode;
    }

    public void runForeground(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification myNotification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_event_note_24dp)
                .setTicker(getText(R.string.ticker_text)).setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message)).build();

        startForeground(1, myNotification);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude  = location.getLatitude()+"";
        longitude = location.getLongitude()+"";
        //Toast.makeText(this, "latitude:"+latitude+" longitude:"+longitude, Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);


        if(prev_day != day)
            resetSchedule();
        prev_day = day;

        ArrayList<String> daySchedule = new ArrayList<String>();
        daySchedule = getSchedule(day);

        long ldate = System.currentTimeMillis();
        String current_time = new SimpleDateFormat("HH:mm").format(new Date(ldate));
        String current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date(ldate));
        String course_latitude, course_longitude;

        ArrayList<String> coursesAlreadyChecked = new ArrayList<String>();
        coursesAlreadyChecked = mydb.getCoursesByDate(current_date);
        for(String schedule : daySchedule){
            String start_time = schedule.split("-")[0];
            String end_time = schedule.split("-")[1];
            String course_id = schedule.split("-")[2];

            if(!coursesAlreadyChecked.contains(course_id) && !coursesAttended.contains(course_id) && !coursesNotAttended.contains(course_id)) {
                if (current_time.compareTo(start_time) >= 0 && current_time.compareTo(end_time) <= 0) {
                    //Toast.makeText(this, current_time, Toast.LENGTH_SHORT).show();
                    course_latitude = mydb.getCourseLocationLatitude(course_id);
                    course_longitude = mydb.getCourseLocationLongitude(course_id);
                    if(!course_latitude.matches("Latitude Not Set") && !course_longitude.matches("Longitude Not Set")) {
                        double latitude_distance = Math.abs(Double.parseDouble(latitude) - Double.parseDouble(course_latitude));
                        double longitude_distance = Math.abs(Double.parseDouble(longitude) - Double.parseDouble(course_longitude));

                        if (latitude_distance <= 0.00025 && longitude_distance <= 0.00025) {

                            if(mydb.makeAttendance(course_id, current_date, 1)) {
                                Notify(course_id, current_date, "Attended");
                                coursesAttended.add(course_id);
                            }
                        }
                    }
                    else {
                        Notify(course_id, current_date, "Coordinates not set !! Please sync them");
                    }
                }
                else if(current_time.compareTo(end_time) > 0){

                    if(mydb.makeAttendance(course_id, current_date, 0)) {
                        Notify(course_id, current_date, "Not Attended");
                        coursesNotAttended.add(course_id);
                    }
                }
            }

        }


    }



    private void Notify(String notificationTitle, String notificationMessage, String subText){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(this, AttendanceDetails.class);
        notificationIntent.putExtra("id", notificationTitle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification myNotification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_check_circle_24dp)
                .setTicker(getText(R.string.ticker_text)).setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                 .setSubText(subText).build();

        Notification myNotication;
        myNotication = builder.getNotification();
        manager.notify(11, myNotication);


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public ArrayList<String> getSchedule(int day){
        ArrayList<String> daySchedule = new ArrayList<String>();
        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                daySchedule = mydb.getTimeByDay("Sunday");
                break;

            case Calendar.MONDAY:
                // Current day is Monday
                daySchedule = mydb.getTimeByDay("Monday");
                break;

            case Calendar.TUESDAY:
                // Current day in Tuesday
                daySchedule = mydb.getTimeByDay("Tuesday");
                break;

            case Calendar.WEDNESDAY:
                // Current day is Wednesday
                daySchedule = mydb.getTimeByDay("Wednesday");
                break;

            case Calendar.THURSDAY:
                // Current day is Thursday
                daySchedule = mydb.getTimeByDay("Thursday");
                break;

            case Calendar.FRIDAY:
                //  Current day is Friday
                daySchedule = mydb.getTimeByDay("Friday");
                break;

            case Calendar.SATURDAY:
                // Current day is Saturday
                daySchedule = mydb.getTimeByDay("Saturday");
                break;
        }

        Collections.sort(daySchedule);
        return daySchedule;


    }

    public void resetSchedule(){
        coursesAttended.clear();
        coursesNotAttended.clear();

    }


    public void getStarted(){
        Log.d("MYLOG", "Alarm On");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
    }




}