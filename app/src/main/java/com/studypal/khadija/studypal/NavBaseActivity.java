package com.studypal.khadija.studypal;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studypal.khadija.studypal.Calendar.CalendarFragment;
import com.studypal.khadija.studypal.Login.AppSharedPref;
import com.studypal.khadija.studypal.Scribble.ScribbleFragment;


import com.studypal.khadija.studypal.StudyTime.StudyTimeFragment;
import com.studypal.khadija.studypal.Syllabus.SyllabusActivity;
import com.studypal.khadija.studypal.Syllabus.SyllabusFragment;
import com.studypal.khadija.studypal.Syllabus.SyllabusFragmentNew;

public class NavBaseActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private AppSharedPref mAppSharedPref;
    private TextView mUserNameTextView;
    private TextView mUserEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_base);
        mAppSharedPref = new AppSharedPref(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)layoutInflater.inflate(R.layout.nav_header_nav_base, null, false);


        Log.d("OnSocialLogin create :", mAppSharedPref.getUserName());

        //Initialize the Drawer layout for the navigation drawer

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_nav_base);

        mUserEmailTextView = (TextView)headerLayout.findViewById(R.id.userEmail);
        mUserNameTextView = (TextView)headerLayout.findViewById(R.id.userName);
        mUserNameTextView.setText(mAppSharedPref.getUserName());
        mUserEmailTextView.setText(mAppSharedPref.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
        //Call fragment manager

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();
        String title="";

        if (id == R.id.item_home) {
            //Handle the Home click action
            fm.beginTransaction().replace(R.id.content_frame,new MainFragment()).commit();
         //   title="Home";

        }
        else if (id == R.id.item_syllabus) {
            //Handle the SyllabusActivity click action
            // fm.beginTransaction().replace(R.id.content_frame,new SyllabusFragmentNew()).commit();
            startActivity(new Intent(this, SyllabusActivity.class));
          //  title="Home";

        } else if (id == R.id.item_study_time) {
            //Handle the Study Time click action
            fm.beginTransaction().replace(R.id.content_frame,new StudyTimeFragment()).commit();
            //title="Home";


        } else if (id == R.id.item_exam) {
            //Handle the Exams click action
            fm.beginTransaction().replace(R.id.content_frame, new ExamFragment()).commit();

        } else if (id == R.id.item_calendar) {
            //Handle the Calendar click action
            fm.beginTransaction().replace(R.id.content_frame,new CalendarFragment()).commit();

        } else if (id == R.id.nav_scribble) {
            //Handle the Scribble click action
            fm.beginTransaction().replace(R.id.content_frame,new ScribbleFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
