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
                        "(id text primary key, name text, day text, start_hour int, start_minute int, end_hour int, end_minute int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS course_info");
        onCreate(db);
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

    public String getData(String id){
        String data;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info where id = ?", new String[]{id});
        res.moveToFirst();
        data = res.getString(res.getColumnIndex(COURSES_COLUMN_NAME));
        return data;
    }

//    public int numberOfRows(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, COURSES_TABLE_NAME);
//        return numRows;
//    }

//    public boolean updateContact (String id, String name)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("phone", phone);
//        contentValues.put("email", email);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
//        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

    public Integer deleteContact (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("course_info",
                "id = ? ",
                new String[] { id });
    }

    public ArrayList<String> getAllCoursesID()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course_info", null );
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
        Cursor res =  db.rawQuery( "select * from course_info", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COURSES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}