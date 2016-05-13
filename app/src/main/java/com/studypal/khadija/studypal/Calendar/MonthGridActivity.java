package com.studypal.khadija.studypal.Calendar;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.studypal.khadija.studypal.NavBaseActivity;
import com.studypal.khadija.studypal.R;

public class MonthGridActivity extends ActionBarActivity {

    private MonthGridFragment monthGridFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_previous:
                monthGridFragment.loadLastMonth();
                return true;
            case R.id.action_next:
                monthGridFragment.loadNextMonth();
                return true;
            case R.id.all_events:
                monthGridFragment.showAllEvents();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_grid);

        startActivity(new Intent(MonthGridActivity.this, CalendarActivity.class));

       /* monthGridFragment = new MonthGridFragment();
        getSupportFragmentManager().beginTransaction()
             //   .add(R.id.activity_calendar_grid_container, monthGridFragment)
                .commit();*/
    }
}