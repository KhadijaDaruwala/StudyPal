package com.studypal.khadija.studypal.Calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.studypal.khadija.studypal.R;


public class MonthGridActivity extends ActionBarActivity {

    private CalendarFragment calendarFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_previous:
                calendarFragment.loadLastMonth();
                return true;
            case R.id.action_next:
                calendarFragment.loadNextMonth();
                return true;
            case R.id.all_events:
                calendarFragment.showAllEvents();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_grid);

        calendarFragment = new CalendarFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_calendar_grid_container,calendarFragment)
                .commit();
    }
}