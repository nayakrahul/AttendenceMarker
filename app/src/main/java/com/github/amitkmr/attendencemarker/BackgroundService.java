package com.github.amitkmr.attendencemarker;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by RAHUL on 14-04-2016.
 */
public class BackgroundService extends Service {
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private DBHelper mydb ;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static BackgroundService inst;

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
        mydb = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        ArrayList<String> daySchedule = new ArrayList<String>();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

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
        Intent myIntent = new Intent(BackgroundService.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(BackgroundService.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
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
}