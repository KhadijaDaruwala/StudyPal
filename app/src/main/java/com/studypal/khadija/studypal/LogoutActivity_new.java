package com.studypal.khadija.studypal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.studypal.khadija.studypal.Login.SocialLogin;

public class LogoutActivity_new extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // SocialLogin.signOut();

       // GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
               .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        mGoogleApiClient.connect();
        signOut();

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

   /* @Override
    public void onConnected(Bundle bundle) {

        if(mGoogleApiClient.isConnected()){

            Log.d("Google_Api_Client: It was connected on (onConnected) function, working as it should.","Success");
        }
        else{
            Log.d("Google_Api_Client: It was NOT connected on (onConnected) function, It is definetly bugged.","Fail");
        }

    }*/
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
