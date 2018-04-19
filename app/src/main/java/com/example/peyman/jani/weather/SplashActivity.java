package com.example.peyman.jani.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.peyman.jani.weather.JavaClass.DataBase;
import com.example.peyman.jani.weather.JavaClass.DatabaseHandler;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    Context context=this;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

            image = (ImageView)findViewById(R.id.sun);
            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clock);
            animation1.setAnimationListener(new SplashActivity());
        //Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        AnimationSet set=new AnimationSet(false);
        set.addAnimation(animation1);
        //set.addAnimation(animation2);
        image.startAnimation(set);
        //DatabaseHandler dataBase = new DatabaseHandler(this,"mydatabase.sql",null,1);
        //dataBase.insertStudent("peyman","jani","25");

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
