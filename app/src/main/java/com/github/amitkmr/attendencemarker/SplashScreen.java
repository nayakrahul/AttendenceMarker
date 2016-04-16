package com.github.amitkmr.attendencemarker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vamsikrishna on 12-Feb-15.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_Light_NoTitleBar);
        setContentView(R.layout.splash_screen);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable(){
            public void run(){

                finish();
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(intent);
            }
        }, 1000);
    }

}

