package com.studypal.khadija.studypal.Calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MonthDisplayAdapter extends BaseAdapter {
    private List<GridItem> gridItems;
    private int measuredHeight;

    public MonthDisplayAdapter(List<GridItem> gridItems, int measuredHeight) {
        this.gridItems = gridItems;
        this.measuredHeight = measuredHeight;
    }

    @Override
    public int getCount() {
        return gridItems.size();
    }

    @Override
    public GridItem getItem(int position) {
        return gridItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridCellView cell = (GridCellView) convertView;
        Context context = parent.getContext();
        if (cell == null) {
            cell = new GridCellView(context);
        }
        GridItem date = getItem(position);

        String day = "";
        if (date != null) {
            day += date.getDay();
            if (date.getIsToday()) {
                cell.cellText.setTextColor(parent.getResources().getColor(android.R.color.holo_blue_dark));
                cell.cellText.setTypeface(null, Typeface.BOLD);
            }
            if (date.getHasEvents()) {
                cell.indicator.setVisibility(View.VISIBLE);
            }
        }

        cell.cellText.setText(day);
        cell.setMinimumHeight((int) (0.98 * (measuredHeight / (gridItems.size() / 7))));
        return cell;
    }
}
