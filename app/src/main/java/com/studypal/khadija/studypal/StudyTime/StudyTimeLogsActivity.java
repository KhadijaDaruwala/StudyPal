package com.studypal.khadija.studypal.StudyTime;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.studypal.khadija.studypal.Calendar.Constants;
import com.studypal.khadija.studypal.MainFragment;
import com.studypal.khadija.studypal.NavBaseActivity;
import com.studypal.khadija.studypal.R;

import java.text.DateFormat;
import java.util.Date;

public class StudyTimeLogsActivity extends AppCompatActivity {
    private TextView studylogChapter;
    private ListView mLogListView;
    private String mChapterName;
    private String mDateTime;
    private TextView studylogTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time_logs);
        setTitle("Study Logs");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });*/
        TextView mLogTitle =(TextView)findViewById(R.id.logTitle);



        mChapterName = getIntent().getStringExtra("chapter_name");
        mDateTime=getIntent().getStringExtra("date_time");
        DatabaseStudyLogs dbHandler = new DatabaseStudyLogs(getApplicationContext(), null, null, 1);
        Cursor cursor = dbHandler.getStudyLogCursor(mChapterName);
        Cursor cursor1=dbHandler.getStudyLogCursor(mDateTime);
        studylogChapter = (TextView)findViewById(R.id.studylogChapter);
        studylogTime=(TextView)findViewById(R.id.studylogTime);
        // TODO set description here
        mLogListView = (ListView)findViewById(R.id.logList);
        if (cursor != null && cursor.getCount() > 0)
        mLogListView.setAdapter(new LogAdapter(getApplicationContext(),cursor,0));
        if(cursor1!=null&&cursor1.getCount()>0)
            mLogListView.setAdapter(new LogAdapter1(getApplicationContext(),cursor1,0));
      //  mLogListView.setAdapter(new LogAdapter1(getApplicationContext(),cursor,0));


    }



    /**
     * Class to render logs
     */
    class LogAdapter extends CursorAdapter {

        private TextView mLogChapter;


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



        //    mLogDate.setText(cursor.getInt(cursor.getColumnIndex(DatabaseStudyLogs.COLUMN_DATE_TIME))+"");
            mLogChapter =(TextView)view.findViewById(R.id.studylogChapter);
            mLogChapter.setText(cursor.getString(cursor.getColumnIndex(DatabaseStudyLogs.COLUMN_CHAPTER_NAME)));
            /*String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            mtextDate.setText(currentDateTimeString);*/

        }
    }
    class LogAdapter1 extends CursorAdapter {
        private TextView mLogDateTime;
        private TextView mLogChapter;


        public LogAdapter1(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public LogAdapter1(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_log_list_item, parent, false);

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
          //  mLogDate = (TextView)view.findViewById(R.id.studylogDate);
            //mLogDate.setText(cursor.getInt(cursor.getColumnIndex(DatabaseStudyLogs.COLUMN_DATE_TIME))+"");
            mLogDateTime =(TextView)view.findViewById(R.id.studylogTime);
            mLogDateTime.setText(cursor.getString(cursor.getColumnIndex(DatabaseStudyLogs.COLUMN_DATE_TIME)));
            mLogChapter =(TextView)view.findViewById(R.id.studylogChapter);
            mLogChapter.setText(cursor.getString(cursor.getColumnIndex(DatabaseStudyLogs.COLUMN_CHAPTER_NAME)));
        }
    }
    @Override
    public void onBackPressed() {
        /*Intent data = new Intent();
        data.putExtra(Constants.DATE_KEY, date);
        setResult(RESULT_OK, data);*/
        startActivity(new Intent(StudyTimeLogsActivity.this, NavBaseActivity.class));

        finish();
        return;
    }

}