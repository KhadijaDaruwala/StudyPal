package layout.samplestudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import app.pal.study.samplestudy.Calendar.CalendarFragment;
import app.pal.study.samplestudy.Scribble.ScribbleFragment;
import app.pal.study.samplestudy.StudyTime.StudyTimeFragment;
import app.pal.study.samplestudy.Syllabus.Syllabus;

public class NavBaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the Drawer layout for the navigation drawer

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.

        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.item_home) {
            //Handle the Home click action
            fm.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
        }
        else if (id == R.id.item_syllabus) {
            //Handle the SyllabusActivity click action
           // fm.beginTransaction().replace(R.id.content_frame,new SyllabusFragmentOld()).commit();
            startActivity(new Intent(this, Syllabus.class));


        } else if (id == R.id.item_study_time) {
            //Handle the Study Time click action
            fm.beginTransaction().replace(R.id.content_frame,new StudyTimeFragment()).commit();

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
        else if (id == R.id.item_share) {
            //Handle the RateUs click action
            fm.beginTransaction().replace(R.id.content_frame,new ShareAppFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
