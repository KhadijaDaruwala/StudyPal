package com.studypal.khadija.studypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, SocialLogin.class);
        startActivity(intent);
        finish();

        SharedPref sharedPref;
        sharedPref = SharedPref.getInstance();
        if (sharedPref.getISLogged_IN(MainActivity.this)) {
            Intent NextScreen = new Intent(getApplicationContext(),
                    NavBaseActivity.class);
            startActivity(NextScreen);
            finish();
        }
        else{
            intent = new Intent(MainActivity.this, SocialLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }
}
