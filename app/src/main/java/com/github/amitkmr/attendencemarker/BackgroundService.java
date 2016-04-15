package com.github.amitkmr.attendencemarker;
import android.app.AlarmManager;
import android.app.Notification;
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

/**
 * Created by RAHUL on 14-04-2016.
 */
public class BackgroundService extends Service implements LocationListener {
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private DBHelper mydb ;


    private static BackgroundService inst;
    // Variable defined for the day schdule
    ArrayList<String> daySchedule = new ArrayList<String>();

    // the fetch of cuurent coordinates from the GPS data class
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String latitude="";
    String longitude="";


    public static BackgroundService instance() {
        return inst;
    }

    @Override
    public void onCreate() {
        // The service is being created
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        runForeground();
        //getStarted();
        // GPS DATA \//////////////////////////////////////
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);





        mydb = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                daySchedule = mydb.getTimeByDay("Sunday");
                Collections.sort(daySchedule);
                break;

            case Calendar.MONDAY:
                // Current day is Monday
                daySchedule = mydb.getTimeByDay("Monday");
                Collections.sort(daySchedule);
                break;

            case Calendar.TUESDAY:
                // Current day in Tuesday
                daySchedule = mydb.getTimeByDay("Tuesday");
                Collections.sort(daySchedule);
                break;

            case Calendar.WEDNESDAY:
                // Current day is Wednesday
                daySchedule = mydb.getTimeByDay("Wednesday");
                Collections.sort(daySchedule);
                break;

            case Calendar.THURSDAY:
                // Current day is Thursday
                daySchedule = mydb.getTimeByDay("Thursday");
                Collections.sort(daySchedule);
                break;

            case Calendar.FRIDAY:
                //  Current day is Friday
                daySchedule = mydb.getTimeByDay("Friday");
                Collections.sort(daySchedule);
                break;

            case Calendar.SATURDAY:
                // Current day is Saturday
                daySchedule = mydb.getTimeByDay("Saturday");
                Collections.sort(daySchedule);
                break;

        }

        Log.d("Background Service", "GPS data");
        return mStartMode;
    }

    public void runForeground(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(this, BackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification myNotification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_event_note_24dp)
                .setTicker(getText(R.string.ticker_text)).setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message)).build();

        startForeground(1, myNotification);
    }

    public void getStarted(){
        Log.d("MYLOG", "Alarm On");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
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

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);

        Log.i("Background Activity","CurrentHour: "+currentHour+" currentMinute: "+currentMinute);

//        String schedule = daySchedule.get(0);
        int length = daySchedule.size();
        if (length !=0)
        {
//            Toast.makeText(this, "schedule:"+length, Toast.LENGTH_SHORT).show();
//
//            int index = 0;
//            while(index < length)
//            {
//                String schedule = daySchedule.get(index);
//                String[] startEndTime = schedule.split("-");
//                String startTime = startEndTime[0];
//                String endTime = startEndTime[1];
//
//                String[] startHourMinute = startTime.split(":");
//                int startHour = Integer.parseInt(startHourMinute[0]);
//                int startMinute = Integer.parseInt((startHourMinute[1]));
//
//
//                String[] endHourMinute = startTime.split(":");
//                int endHour = Integer.parseInt(startHourMinute[0]);
//                int endMinute = Integer.parseInt((startHourMinute[1]));
//
//                if (currentHour <= endHour && currentHour >= startHour)
//                {
//
//
//                }
//            }

        }


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
}