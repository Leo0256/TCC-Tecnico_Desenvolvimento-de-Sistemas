package com.LizCore.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.widget.ImageView;

import com.LizCore.R;

import options.IntentActions;

public class LoadingActivity extends AppCompatActivity {

    public IntentActions intent;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_loading);
        intent = new IntentActions();

        imageView = findViewById(R.id.logo_big);

        startTimer();

    }

    //Timer de 3 segundos
    public void startTimer() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onFinish() {
                intent.newIntent(LoadingActivity.this, LoginActivity.class, imageView);
                finish();
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
