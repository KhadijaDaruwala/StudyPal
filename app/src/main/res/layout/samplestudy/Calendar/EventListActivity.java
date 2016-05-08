package layout.samplestudy.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import app.pal.study.samplestudy.R;

public class EventListActivity extends ActionBarActivity {
    private static final int REQUEST_CODE = 5;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = (Date) extras.get(Constants.DATE_KEY);
        }
        if (date == null) {
            date = new Date();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.button_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewEditor();
            }
        });
        refresh();
    }

    private void openNewEditor() {
        Intent intent = new Intent(EventListActivity.this, EventEditorActivity.class);
        intent.putExtra(Constants.DATE_KEY, date);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        findViewById(R.id.button_create).setOnClickListener(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Date dateExtra = (Date) extras.get(Constants.DATE_KEY);
                if (dateExtra != null) {
                    date = dateExtra;
                }
            }
            refresh();
        }
    }

    private void refresh() {
        CalendarEventDataSource dataSource = new CalendarEventDataSource(this);
        dataSource.openReadOnlyDB();
        final List<CalendarEvent> calendarEvents = dataSource.getAllEventsForDay(date);
        dataSource.close();

        EventListAdapter adapter = new EventListAdapter(calendarEvents);

        ListView listView = (ListView) findViewById(R.id.event_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewEvent(position, calendarEvents);
            }
        });
        setTitle(CalUtil.dateToDisplayString(date));
    }

    private void viewEvent(int position, List<CalendarEvent> calendarEvents) {
        Intent intent = new Intent(this, EventViewerActivity.class);
        intent.putExtra(Constants.EVENT_KEY, calendarEvents.get(position));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                end();
                return true;
            case R.id.action_previous:
                date = CalUtil.subtractDay(date, 1);
                refresh();
                return true;
            case R.id.action_next:
                date = CalUtil.addDay(date, 1);
                refresh();
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
        data.putExtra(Constants.DATE_KEY, date);
        setResult(RESULT_OK, data);
        finish();
    }
}