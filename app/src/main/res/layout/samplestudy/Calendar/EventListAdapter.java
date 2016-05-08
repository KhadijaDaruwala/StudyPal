package layout.samplestudy.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class EventListAdapter extends BaseAdapter {
    private List<CalendarEvent> allEventsForDay;

    public EventListAdapter(List<CalendarEvent> allEventsForDay) {

        this.allEventsForDay = allEventsForDay;
    }

    @Override
    public int getCount() {
        return allEventsForDay.size();
    }

    @Override
    public CalendarEvent getItem(int position) {
        return allEventsForDay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventListView row = (EventListView) convertView;
        Context context = parent.getContext();
        if (row == null) {
            row = new EventListView(context);
        }
        CalendarEvent event = getItem(position);

        row.cellText.setText(event.getTitle());
        return row;
    }
}
