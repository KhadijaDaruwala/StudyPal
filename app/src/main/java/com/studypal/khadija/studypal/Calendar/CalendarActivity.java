package com.studypal.khadija.studypal.Calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;

import com.studypal.khadija.studypal.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.studypal.khadija.studypal.Calendar.Constants.DATE_KEY;

/**
 * Created by Khadija on 13-05-2016.
 */
public class CalendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar_grid);
      //  setTitle("Study Logs");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private static final int REQUEST_CODE = 1;
    private Calendar calendar;


    @Override
    public void onResume() {
        super.onResume();

        if (calendar == null) {
            calendar = CalUtil.getCalendar(new Date());
        }
        refresh();
    }

    private void refresh() {
        final GridView calendarView = (GridView) findViewById(R.id.calendar_grid_view);

        setTitle();
        initListAdapter(calendarView);

        calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GridItem item = (GridItem) parent.getAdapter().getItem(position);
                if (item != null) {
                    startListActivity(item);
                }
            }
        });
    }

    private void startListActivity(GridItem item) {
        calendar.set(Calendar.DATE, item.getDay());
        Intent intent = new Intent(this, EventListActivity.class);
        intent.putExtra(DATE_KEY, calendar.getTime());
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void loadNextMonth() {
        calendar.setTime(CalUtil.addMonth(calendar.getTime(), 1));
        refresh();
    }

    public void loadLastMonth() {
        calendar.setTime(CalUtil.subtractMonth(calendar.getTime(), 1));
        refresh();
    }

    public void showAllEvents() {
        Intent intent = new Intent(this, AllEventsActivity.class);
        intent.putExtra(DATE_KEY, calendar.getTime());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            calendar.setTime((Date) data.getExtras().get(Constants.DATE_KEY));
        }
    }

    private void setTitle() {
        setTitle(CalUtil.dateToFormattedString(calendar.getTime(), "MMMM, yyyy"));
        ;
    }

    private void initListAdapter(final GridView calendarView) {
        calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    calendarView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    calendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                CalendarEventDataSource dataSource = new CalendarEventDataSource(getBaseContext());
                dataSource.openReadOnlyDB();
                List<GridItem> gridList = CalUtil.getGridList(calendar.getTime(),
                        dataSource.getDaysWithEventsForMonth(calendar.getTime()));
                dataSource.close();

                calendarView.setAdapter(new MonthDisplayAdapter(
                                gridList, calendarView.getMeasuredHeight())
                );
            }
        });
    }
}
