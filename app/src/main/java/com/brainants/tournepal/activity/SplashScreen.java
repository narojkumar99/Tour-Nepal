package com.brainants.tournepal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brainants.tournepal.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread background = new Thread() {

            public void run() {
                try {
                    sleep(1500);
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up,R.anim.nothing);
                    finish();
                } catch (Exception e) {
                    finish();
                }
            }
        };
        background.start();
    }
}
