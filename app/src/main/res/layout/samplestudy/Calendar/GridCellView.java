package layout.samplestudy.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.pal.study.samplestudy.R;

public class GridCellView extends LinearLayout {
    public TextView cellText;
    public ImageView indicator;

    public GridCellView(Context context) {
        super(context);
        ViewGroup group = (ViewGroup) View.inflate(context, R.layout.calendar_grid_cell, this);
        group.requestFocus();
        cellText = (TextView) group.findViewById(R.id.cell_text);
        indicator = (ImageView) group.findViewById(R.id.cell_indicator);
    }
}
