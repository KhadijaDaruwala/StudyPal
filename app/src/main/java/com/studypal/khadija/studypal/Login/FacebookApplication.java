package com.studypal.khadija.studypal.Login;

import android.app.Application;
import com.facebook.FacebookSdk;

//Facebook Initialization

public class FacebookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }
}
