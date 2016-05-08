package com.studypal.khadija.studypal.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Date;
import java.util.List;

public class CalAllEventsListAdapter extends BaseAdapter {
    private List<CalendarEvent> allEvents;

    public CalAllEventsListAdapter(List<CalendarEvent> allEvents) {
        this.allEvents = allEvents;
    }

    @Override
    public int getCount() {
        return allEvents.size();
    }

    @Override
    public CalendarEvent getItem(int position) {
        return allEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalAllEventsListView row = (CalAllEventsListView) convertView;
        Context context = parent.getContext();
        if (row == null) {
            row = new CalAllEventsListView(context);
        }
        CalendarEvent event = getItem(position);

        row.titleText.setText(event.getTitle());

        Date date = event.getDate();
        String dateString = CalUtil.dateToString(date);
        if (event.getAllDay()) {
            dateString += ", " + CalUtil.timeToString(date);
        }
        row.dateText.setText(dateString);

        row.descriptionText.setText(event.getDescription());

        return row;
    }
}
