package com.github.amitkmr.attendencemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class DBHelper extends SQLiteOpenHelper {

//    SQLiteDatabase mydatabase = openOrCreateDatabase("Courses",MODE_PRIVATE,null);
    public static final String DATABASE_NAME = "Courses.db";
    public static final String COURSES_TABLE_NAME = "course_info";
    public static final String COURSES_COLUMN_ID = "id";
    public static final String COURSES_COLUMN_NAME = "name";
    public static final String COURSES_COLUMN_DAY = "day";
    public static final String COURSES_COLUMN_START_HR = "start_hour";
    public static final String COURSES_COLUMN_START_MIN = "start_minute";
    public static final String COURSES_COLUMN_END_HR = "end_hour";
    public static final String COURSES_COLUMN_END_MIN = "end_minute";
    public static final String COURSES_COLUMN_LATITUDE = "latitude";
    public static final String COURSES_COLUMN_LONGITUDE = "longitude";
    public static final String COURSES_COLUMN_DATE = "date";
    public static final String COURSES_COLUMN_ATTENDANCE = "attendance";

    private HashMap hp;


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table course_info " +
                        "(id text, name text, day text, start_hour int, start_minute int, end_hour int, end_minute int)"
        );


        db.execSQL(
                "create table course_location_info " +
                        "(id text, name text, latitude text, longitude text)"
        );

        db.execSQL(
                "create table course_attendance " +
                        "(id text, date text, attendance int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS course_info");
        db.execSQL("DROP TABLE IF EXISTS course_location_info");
        db.execSQL("DROP TABLE IF EXISTS course_attendance");
        onCreate(db);
    }

    public boolean makeAttendance (String id, String date, int attendance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("date", date);
        contentValues.put("attendance", attendance);
        db.insert("course_attendance", null, contentValues);
        return true;
    }

    public boolean insertCourse  (String id, String name, String day, int start_hr, int start_min, int end_hr, int end_min)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("day", day);
        contentValues.put("start_hour", start_hr);
        contentValues.put("start_minute", start_min);
        contentValues.put("end_hour", end_hr);
        contentValues.put("end_minute", end_min);
        db.insert("course_info", null, contentValues);
        return true;
    }


    public boolean insertCoordinates (String id, String name, String latitude, String longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        db.insert("course_location_info", null, contentValues);
        return true;
    }

    public String getCoursesColumnName(String id){
        String data;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        data = res.getString(res.getColumnIndex(COURSES_COLUMN_NAME));
        return data;
    }

    public String getCourseLocationLatitude(String id){
        String data;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_location_info where id = ?", new String[]{id});
        res.moveToFirst();
        data = res.getString(res.getColumnIndex(COURSES_COLUMN_LATITUDE));
        return data;
    }

    public String getCourseLocationLongitude(String id){
        String data;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_location_info where id = ?", new String[]{id});
        res.moveToFirst();
        data = res.getString(res.getColumnIndex(COURSES_COLUMN_LONGITUDE));
        return data;
    }

    public ArrayList<String> getCoursesColumnDay(String id){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COURSES_COLUMN_DAY)));
            res.moveToNext();
        }
        return array_list;

    }

    public ArrayList<Integer> getCoursesColumnStartHr(String id){
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(COURSES_COLUMN_START_HR)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getCoursesColumnStartMin(String id){
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(COURSES_COLUMN_START_MIN)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getCoursesColumnEndHr(String id){
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(COURSES_COLUMN_END_HR)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getCoursesColumnEndMin(String id){
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(COURSES_COLUMN_END_MIN)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getTimeByDay (String day){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from course_info where day = ?", new String[]{day});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            int start_hr = res.getInt(res.getColumnIndex(COURSES_COLUMN_START_HR));
            int start_min = res.getInt(res.getColumnIndex(COURSES_COLUMN_START_MIN));
            int end_hr = res.getInt(res.getColumnIndex(COURSES_COLUMN_END_HR));
            int end_min = res.getInt(res.getColumnIndex(COURSES_COLUMN_END_MIN));
            String id = res.getString(res.getColumnIndex(COURSES_COLUMN_ID));
            array_list.add(String.format("%02d", start_hr)+":"+String.format("%02d", start_min)+"-"
                            +String.format("%02d", end_hr)+":"+String.format("%02d", end_min)+"-"
                            +id);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getCoursesColumnDate (String id){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from course_attendance where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(COURSES_COLUMN_DATE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getCoursesColumnAttendance (String id){
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from course_attendance where id = ?", new String[]{id});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(COURSES_COLUMN_ATTENDANCE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getCoursesByDate (String date){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from course_attendance where date = ?", new String[]{date});
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COURSES_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

//    public int numberOfRows(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, COURSES_TABLE_NAME);
//        return numRows;
//    }

    public boolean updateCoordinates (String id, String name, String latitude, String longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        db.update("course_location_info", contentValues, "id = ? ", new String[] {id} );
        return true;
    }

    public boolean deleteCourse (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("course_info",
                "id = ? ",
                new String[] { id });
        db.delete("course_location_info",
                "id = ? ",
                new String[] { id });
        db.delete("course_attendance",
                "id = ? ",
                new String[] { id });
        return true;
    }

    public ArrayList<String> getAllCoursesID()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_location_info", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COURSES_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllCoursesName()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_location_info", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COURSES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}