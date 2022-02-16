package com.example.myplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;


public class Splash extends AppCompatActivity {

    public static final int TIME_TO_START = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                goToLogin();
            }
        }, TIME_TO_START);

    }

    private void goToLogin() {
        Intent splash_login = new Intent(this, Login.class);
        startActivity(splash_login);
        finish();
    }
}