package com.example.expressapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashSreen_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent_inicio = new Intent(SplashSreen_2.this,MainActivity.class);
                startActivity(intent_inicio);
                finish();
            }
        },1000);
    }
}