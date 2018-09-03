package com.example.hq.fypmobileapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        final Intent intent;

        PreferenceManager.getInstance().init(this);

        if(PreferenceManager.getInstance().getBoolean(PreferenceManager.LOGGED_IN) == true){
            intent = new Intent(this, Dashboard.class);
        }
        else{
            intent = new Intent(this,SigninScreen.class);
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
