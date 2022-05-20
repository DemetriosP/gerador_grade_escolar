package com.example.geradordegradeescolar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.geradordegradeescolar.R;

import java.util.Objects;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(this::vaiTelaLogin, 1000);
    }

    private void vaiTelaLogin() {
        Intent intent = new Intent(SplashScreen.this, FormLogin.class);
        startActivity(intent);
        finish();
    }
}