package com.studypal.khadija.studypal.StudyTime;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Log;

import com.studypal.khadija.studypal.Syllabus.Chapter;

import java.util.concurrent.TimeUnit;

public class DatabaseStudyLogs extends SQLiteOpenHelper{
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "studylogDB.db";

        private static final String TABLE = "studylogs";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_SUBJECT_NAME = "subjectname";
        public static final String COLUMN_CHAPTER_NAME = "chapter";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE_TIME = "datetime";


        public DatabaseStudyLogs(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase dB) {
            String CREATE_TABLE_CLASS = "CREATE TABLE " + TABLE + "(" +
                    COLUMN_ID + " " + "INTEGER ," +
                    COLUMN_SUBJECT_NAME + " " + " TEXT," +
                    COLUMN_CHAPTER_NAME + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_DATE_TIME + " Integer)";
            Log.d("onCreate", CREATE_TABLE_CLASS);
            dB.execSQL(CREATE_TABLE_CLASS);

        }

        /**
         * Insert logs
         * @param studylog
         */
        public void addStudyLog(StudyLog studylog) {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID, studylog.getID());
            //values.put(COLUMN_SUBJECT_NAME, studylog.getSubjectName());
            values.put(COLUMN_CHAPTER_NAME, studylog.chaptername);
            //values.put(COLUMN_DESCRIPTION, studylog.getDescription());
            values.put(COLUMN_DATE_TIME, System.currentTimeMillis());
            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(TABLE, null, values);
            Log.d("addStudyLog", studylog.chaptername);
            db.close();
        }

        /**
         *
         * @param chapterName
         * @return
         */
        public Cursor getStudyLogCursor(String chapterName) {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_CHAPTER_NAME + " = '" + chapterName + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            Log.d("getStudyLogCursor", selectQuery);
            Log.d("getStudyLogCursor", cursor.getCount()+"");
            return cursor;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }