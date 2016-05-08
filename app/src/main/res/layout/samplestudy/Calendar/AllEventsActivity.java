package layout.samplestudy.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import app.pal.study.samplestudy.R;

public class AllEventsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        CalendarEventDataSource dataSource = new CalendarEventDataSource(this);
        dataSource.openReadOnlyDB();
        final List<CalendarEvent> calendarEvents = dataSource.getAllEvents();
        dataSource.close();

        AllEventsListAdapter adapter = new AllEventsListAdapter(calendarEvents);

        ListView listView = (ListView) findViewById(R.id.all_event_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            end();
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
        data.putExtra(Constants.DATE_KEY, (Date) getIntent().getExtras().get(Constants.DATE_KEY));
        setResult(RESULT_OK, data);
        finish();
    }
}
