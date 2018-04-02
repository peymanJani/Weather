package com.example.peyman.jani.weather.JavaClass;

/**
 * Created by pc_peyman on 3/29/2018.
 */
import android.app.Application;

import com.example.peyman.jani.weather.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class FontDastnevis extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Khandevane.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}