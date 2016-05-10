package com.studypal.khadija.studypal.Calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.studypal.khadija.studypal.R;

import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;


public class EventEditorFragment extends Fragment {

    private Button saveButton;
    private EditText titleEditor;
    private EditText descriptionEditor;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox allDayCheckbox;
    private CalendarEvent calendarEvent;

    public EventEditorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_editor, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initCalendarEvent();
        initViews();
    }

    private void initCalendarEvent() {
        Date date = null;
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            calendarEvent = (CalendarEvent) extras.get(Constants.EVENT_KEY);
            date = (Date) extras.get(Constants.DATE_KEY);
        }
        if (calendarEvent == null) {
            calendarEvent = new CalendarEvent(null, date != null ? date : new Date(), "", "", true);
        }
    }

    private void initViews() {
        Date date = calendarEvent.getDate();
        String title = calendarEvent.getTitle();
        String description = calendarEvent.getDescription();
        final boolean allDay = calendarEvent.getAllDay();

        if (!"".equals(title)) {
            getActivity().setTitle(title);
        }
        titleEditor = (EditText) getActivity().findViewById(R.id.event_title);
        titleEditor.setText(title);
        descriptionEditor = (EditText) getActivity().findViewById(R.id.event_description);
        descriptionEditor.setText(description);

        datePicker = (DatePicker) getActivity().findViewById(R.id.date_picker);
        datePicker.init(CalUtil.getYear(date), CalUtil.getMonth(date), CalUtil.getDayOfMonth(date), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                updateSave();
            }
        });


        allDayCheckbox = (CheckBox) getActivity().findViewById(R.id.all_day_checkbox);
        allDayCheckbox.setChecked(allDay);

        timePicker = (TimePicker) getActivity().findViewById(R.id.time_picker);
        timePicker.setCurrentHour(CalUtil.getHour(date));
        timePicker.setCurrentMinute(CalUtil.getMinute(date));
        timePicker.setIs24HourView(true);
        if (!allDay) {
            timePicker.setVisibility(VISIBLE);
        }

        saveButton = (Button) getActivity().findViewById(R.id.button_save);

        titleEditor.addTextChangedListener(new SaveEnablingTextWatcher());
        descriptionEditor.addTextChangedListener(new SaveEnablingTextWatcher());
        allDayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dismissKeyboard();
                if (isChecked) {
                    timePicker.setVisibility(View.GONE);
                    allDayCheckbox.setTextColor(getResources().getColor(R.color.black));
                } else {
                    timePicker.setVisibility(VISIBLE);
                    allDayCheckbox.setTextColor(getResources().getColor(R.color.gray));
                    timePicker.requestFocus();
                }
                updateSave();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
                end();
            }
        });
    }


    private void dismissKeyboard() {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(titleEditor.getWindowToken(), 0);
        manager.hideSoftInputFromWindow(descriptionEditor.getWindowToken(), 0);
    }

    private class SaveEnablingTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateSave();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    private void updateSave() {
        saveButton.setEnabled(titleEditor.getText().toString().length() > 0
                && descriptionEditor.getText().toString().length() > 0);
    }

    private void saveEvent() {
        CalendarEventDataSource dataSource = new CalendarEventDataSource(getActivity());
        dataSource.openWritableDB();

        boolean allDay = allDayCheckbox.isChecked();
        Date date = CalUtil.getDate(
                datePicker.getYear(),
                datePicker.getMonth() + 1,
                datePicker.getDayOfMonth(),
                allDay ? 0 : timePicker.getCurrentHour(),
                allDay ? 0 : timePicker.getCurrentMinute());

        calendarEvent = new CalendarEvent(
                calendarEvent.getId(),
                date,
                titleEditor.getText().toString(),
                descriptionEditor.getText().toString(),
                allDay);

        if (calendarEvent.getId() == null) {
            dataSource.createEvent(calendarEvent);
        } else {
            dataSource.updateEvent(calendarEvent);
        }
        dataSource.close();
    }

    public void end() {
        Intent data = new Intent();
        data.putExtra(Constants.DATE_KEY, calendarEvent.getDate());
        data.putExtra(Constants.EVENT_KEY, calendarEvent);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }}