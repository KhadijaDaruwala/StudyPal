package com.studypal.khadija.studypal.Calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.studypal.khadija.studypal.R;

public class EventEditorActivity extends ActionBarActivity {

    private EventEditorFragment eventEditorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        if (savedInstanceState == null) {
            eventEditorFragment = new EventEditorFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, eventEditorFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            eventEditorFragment.end();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        eventEditorFragment.end();
    }
}