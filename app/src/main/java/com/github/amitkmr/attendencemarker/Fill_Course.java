package com.github.amitkmr.attendencemarker;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by RAHUL on 03-04-2016.
 */
public class Fill_Course extends AppCompatActivity {
    TextView id;
    TextView name;
    private DBHelper mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_course);
        id = (TextView) findViewById(R.id.editId);
        name = (TextView) findViewById(R.id.editName);

        mydb = new DBHelper(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v)
    {
            if (mydb.insertCourse(id.getText().toString(), name.getText().toString())){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                }
                else{
                Intent intent = new Intent(getApplicationContext(),Course.class);
                startActivity(intent);
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
}
