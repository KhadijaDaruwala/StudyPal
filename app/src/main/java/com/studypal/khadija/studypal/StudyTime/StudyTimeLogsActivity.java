package com.studypal.khadija.studypal.StudyTime;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.studypal.khadija.studypal.R;

public class StudyTimeLogsActivity extends AppCompatActivity {
    private TextView studylogDesc;
    private ListView mLogListView;
    private String mChapterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time_logs);
        mChapterName = getIntent().getStringExtra("chapter_name");
        DatabaseStudyLogs dbHandler = new DatabaseStudyLogs(getApplicationContext(), null, null, 1);
        Cursor cursor = dbHandler.getStudyLogCursor(mChapterName);
        studylogDesc = (TextView)findViewById(R.id.studylogChapter);
        // TODO set description here
        mLogListView = (ListView)findViewById(R.id.logList);
        if (cursor != null && cursor.getCount() > 0)
            mLogListView.setAdapter(new LogAdapter(getApplicationContext(),cursor,0));
    }

    /**
     * Class to render logs
     */
    class LogAdapter extends CursorAdapter {
        private TextView mLogDate;

        public LogAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public LogAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_log_list_item, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            mLogDate = (TextView)view.findViewById(R.id.studylogTime);
            mLogDate.setText(cursor.getInt(cursor.getColumnIndex(DatabaseStudyLogs.COLUMN_DATE_TIME))+"");
        }
    }

}