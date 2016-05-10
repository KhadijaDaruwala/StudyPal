package com.studypal.khadija.studypal.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.studypal.khadija.studypal.Calendar.CalendarEventSQLiteHelper.COLUMN_ALLDAY;
import static com.studypal.khadija.studypal.Calendar.CalendarEventSQLiteHelper.COLUMN_DATE;
import static com.studypal.khadija.studypal.Calendar.CalendarEventSQLiteHelper.COLUMN_DETAILS;
import static com.studypal.khadija.studypal.Calendar.CalendarEventSQLiteHelper.COLUMN_ID;
import static com.studypal.khadija.studypal.Calendar.CalendarEventSQLiteHelper.COLUMN_TITLE;
import static com.studypal.khadija.studypal.Calendar.CalendarEventSQLiteHelper.TABLE_EVENTS;


public class CalendarEventDataSource {
    private SQLiteDatabase database;
    private CalendarEventSQLiteHelper dbHelper;
    private String[] allColumns = {
            COLUMN_ID,
            COLUMN_DATE,
            COLUMN_TITLE,
            COLUMN_DETAILS,
            COLUMN_ALLDAY
    };

    public CalendarEventDataSource(Context context) {
        dbHelper = new CalendarEventSQLiteHelper(context);
    }

    public void openWritableDB() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void openReadOnlyDB() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public CalendarEvent createEvent(CalendarEvent event) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE, CalUtil.dateTimeToString(event.getDate()));
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DETAILS, event.getTitle());
        values.put(COLUMN_ALLDAY, event.getAllDay() ? 1 : 0);

        long insertId = database.insert(TABLE_EVENTS, null, values);

        Cursor cursor = database.query(TABLE_EVENTS,
                allColumns, COLUMN_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        CalendarEvent calendarEvent = cursorToEvent(cursor);
        cursor.close();
        return calendarEvent;
    }

    public void updateEvent(CalendarEvent event) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE, CalUtil.dateTimeToString(event.getDate()));
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DETAILS, event.getTitle());
        values.put(COLUMN_ALLDAY, event.getAllDay() ? 1 : 0);

        long id = event.getId();
        database.update(TABLE_EVENTS, values, COLUMN_ID + " = " + id, null);
    }

    public void deleteEvent(CalendarEvent event) {
        long id = event.getId();
        database.delete(TABLE_EVENTS, COLUMN_ID + " = " + id, null);
    }

    public List<CalendarEvent> getAllEvents() {
        return getEventsFromDB(null);
    }

    public List<CalendarEvent> getAllEventsForDay(Date date) {
        Date beginDate = CalUtil.getBeginningOfDay(date);
        Date endDate = CalUtil.getEndOfDay(date);
        String selection = getDateRangeSelection(beginDate, endDate);

        return getEventsFromDB(selection);
    }

    public List<Integer> getDaysWithEventsForMonth(Date date) {
        Date beginDate = CalUtil.getBeginningOfMonth(date);
        Date endDate = CalUtil.getEndOfMonth(date);
        String selection = getDateRangeSelection(beginDate, endDate);

        ArrayList<Integer> days = new ArrayList<>();
        List<CalendarEvent> eventsFromDB = getEventsFromDB(selection);
        for (CalendarEvent event : eventsFromDB) {
            days.add(CalUtil.getDayOfMonth(event.getDate()));
        }

        return days;
    }

    private CalendarEvent cursorToEvent(Cursor cursor) {
        CalendarEvent event = new CalendarEvent(cursor.getLong(0), CalUtil.stringToDate(cursor.getString(1)), cursor.getString(2), cursor.getString(3), 0 != cursor.getInt(4));
        return event;
    }

    private List<CalendarEvent> getEventsFromDB(String selection) {
        Cursor cursor = database.query(TABLE_EVENTS,
                allColumns,
                selection,
                null, null, null, COLUMN_DATE);
        cursor.moveToFirst();

        List<CalendarEvent> events = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            CalendarEvent event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return events;
    }

    private String getDateRangeSelection(Date beginDate, Date endDate) {
        return COLUMN_DATE
                + " between \"" + CalUtil.dateTimeToString(beginDate) + "\" and \""
                + CalUtil.dateTimeToString(endDate) + "\"";
    }
}