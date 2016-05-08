package layout.samplestudy.Calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.pal.study.samplestudy.R;

import static android.app.Activity.RESULT_OK;

public class CalendarFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private Calendar calendar;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar_grid, container, false);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        if (calendar == null) {
            calendar = CalUtil.getCalendar(new Date());
        }
        refresh();
    }

    private void refresh() {
        final GridView calendarView = (GridView)rootView.findViewById(R.id.calendar_grid_view);

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
        Intent intent = new Intent(getActivity(), EventListActivity.class);
        intent.putExtra(Constants.DATE_KEY, calendar.getTime());
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
        Intent intent = new Intent(getActivity(), AllEventsActivity.class);
        intent.putExtra(Constants.DATE_KEY, calendar.getTime());
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
        getActivity().setTitle(CalUtil.dateToFormattedString(calendar.getTime(), "MMMM, yyyy"));
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

                CalendarEventDataSource dataSource = new CalendarEventDataSource(getActivity());
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
