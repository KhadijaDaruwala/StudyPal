package com.studypal.khadija.studypal;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Khadija on 10-01-2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }
}
