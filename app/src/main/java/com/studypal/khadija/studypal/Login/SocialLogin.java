package com.studypal.khadija.studypal.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.service.carrier.CarrierMessagingService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.studypal.khadija.studypal.NavBaseActivity;
import com.studypal.khadija.studypal.R;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.studypal.khadija.studypal.Login.SocialLogin;

import java.util.Arrays;

public class SocialLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = SocialLogin.class.getSimpleName();
    private static GoogleApiClient mGoogleApiClient;
    private SignInButton mSignInButton;
    private static final int RC_SIGN_IN = 0;
    private CallbackManager mCallbackManager;
    private LoginButton mFbLoginButton;
    private AppSharedPref mSharePref;
    private LoginManager mLoginManager;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;
    private Profile profile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);
        mSharePref = new AppSharedPref(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        //[START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested.
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setScopes(gso.getScopeArray());
        mSignInButton.setOnClickListener(this);  //Register button's OnClickListener to sign in the user when clicked
        // [END customize_button]

        //Facebook Login
        mFbLoginButton = (LoginButton) findViewById(R.id.login_button);
        mLoginManager = LoginManager.getInstance();
        mCallbackManager  = CallbackManager.Factory.create();
        tokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }};
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
            }
        };

        tokenTracker.startTracking();
        profileTracker.startTracking();

        mFbLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLoginManager.logInWithReadPermissions(SocialLogin.this, Arrays.asList("public_profile", "email"));

                mLoginManager.registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>() {

                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                AccessToken accessToken = loginResult.getAccessToken();
                                profile = Profile.getCurrentProfile();

                               /* StringBuilder loginResponse = new StringBuilder();
                                loginResponse.append(loginResult.getAccessToken().getUserId());
                                loginResponse.append(loginResult.getAccessToken().getToken());
                                Toast.makeText(getApplicationContext(), loginResponse.toString() + "", Toast.LENGTH_SHORT).show();
                              //  startActivity(new Intent(getApplicationContext(),ShareActivity.class));
                                finish();*/

                                // TODO
                                String personName = "public_profile";
                                String personEmail = "email";
                                profile = Profile.getCurrentProfile();
                                mSharePref.saveISLogged_IN(true);
                                mSharePref.setUserName(personName);
                                mSharePref.setEmail(personEmail);

                            //    Toast.makeText(profile, mSharePref.getUserName()+""+mSharePref.getEmail(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(SocialLogin.this, "Fb Login Success", Toast.LENGTH_SHORT).show();
                                redirectToHome();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(SocialLogin.this, "Fb Login Cancel", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(SocialLogin.this, "Fb Login Error", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }


    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END onActivityResult]

    /**
     * Handle Sign In Result
     * @param result
     */

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            mSharePref.saveISLogged_IN(true);
            mSharePref.setUserName(personName);
            mSharePref.setEmail(personEmail);

            Toast.makeText(this, mSharePref.getUserName()+""+mSharePref.getEmail(), Toast.LENGTH_SHORT).show();
            redirectToHome();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show();
            //mIdTokenTextView.setText("ID Token: null");
        }
    }
    // [END handleSignInResult]

    //[START signIn]
    private void signIn() {
        //Starting the intent prompts the user to select a Google account to sign in with.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //[END signIn]

    // [START signOut]
    public static void signOut() {
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
    // [END signOut]


    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    //Handle sign-in button taps by creating a sign-in intent with the getSignInIntent method,
    //and starting the intent with startActivityForResult.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     *
     */
    private void redirectToHome() {
        startActivity(new Intent(SocialLogin.this, NavBaseActivity.class));
        finish();
    }
}