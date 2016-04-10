package com.studypal.khadija.studypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;

public class EventViewerActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 6;
    private CalendarEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = (CalendarEvent) extras.get(Constants.EVENT_KEY);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        setTitle(event.getTitle());
        Date date = event.getDate();
        String dateText = CalUtil.dateToDisplayString(date);
        if (!event.getAllDay()) {
            dateText += ", " + CalUtil.timeToString(date);
        }
        ((TextView) findViewById(R.id.event_date_text)).setText(dateText);
        ((TextView) findViewById(R.id.event_description_text)).setText(event.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                end();
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, EventEditorActivity.class);
                intent.putExtra(Constants.EVENT_KEY, event);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            case R.id.action_delete:
                CalendarEventDataSource dataSource = new CalendarEventDataSource(this);
                dataSource.openWritableDB();
                dataSource.deleteEvent(event);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        end();
    }

    private void end() {
        Intent data = new Intent();
        data.putExtra(Constants.EVENT_KEY, event);
        data.putExtra(Constants.DATE_KEY, event.getDate());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            event = (CalendarEvent) data.getExtras().get(Constants.EVENT_KEY);
            refresh();
        }
    }
}
