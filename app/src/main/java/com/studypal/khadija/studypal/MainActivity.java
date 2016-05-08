package com.studypal.khadija.studypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.studypal.khadija.studypal.Login.AppSharedPref;
import com.studypal.khadija.studypal.Login.SocialLogin;

public class MainActivity extends AppCompatActivity {
    private AppSharedPref mAppSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppSharedPref = new AppSharedPref(this);
        startActivity(new Intent(this, mAppSharedPref.IsLoggedIN() ? NavBaseActivity.class : SocialLogin.class ));
        finish();
    }
}
