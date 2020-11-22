package com.example.videofromgoogledrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeSplas extends AppCompatActivity {
    ImageView logo;
    Animation anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_welcome_splas);
        logo = findViewById(R.id.logo);
        anime = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        logo.setAnimation(anime);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(WelcomeSplas.this, MainActivity.class);
                                          ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(WelcomeSplas.this,
                                                  android.R.anim.slide_in_left,
                                                  android.R.anim.slide_out_right);
                                          startActivity(intent, optionsCompat.toBundle());
                                          finish();
                                      }
                                  },
                5000
        );
    }
}