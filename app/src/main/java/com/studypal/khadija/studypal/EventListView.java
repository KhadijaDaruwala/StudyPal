package com.studypal.khadija.studypal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventListView extends LinearLayout {
    public TextView cellText;

    public EventListView(Context context) {
        super(context);
        ViewGroup group = (ViewGroup) View.inflate(context, R.layout.event_list_item, this);
        group.requestFocus();
        cellText = (TextView) group.findViewById(R.id.cell_text);
    }
}
