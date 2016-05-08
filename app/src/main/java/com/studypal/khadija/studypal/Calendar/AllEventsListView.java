package com.studypal.khadija.studypal.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studypal.khadija.studypal.R;

public class AllEventsListView extends LinearLayout {
    public TextView titleText;
    public TextView descriptionText;
    public TextView dateText;

    public AllEventsListView(Context context) {
        super(context);
        ViewGroup group = (ViewGroup) View.inflate(context, R.layout.all_events_list_item, this);
        group.requestFocus();
        titleText = (TextView) group.findViewById(R.id.item_title_text);
        descriptionText = (TextView) group.findViewById(R.id.item_description_text);
        dateText = (TextView) group.findViewById(R.id.item_date_text);
    }
}
