package com.studypal.khadija.studypal.Syllabus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studypal.khadija.studypal.Syllabus.Chapter;
import com.studypal.khadija.studypal.Syllabus.Subject;

import java.util.Calendar;

public class DatabaseChapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chapterDB.db";

    private static final String TABLE = "chapters";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT = "class";
    public static final String COLUMN_NAME = "chaptername";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";

    public DatabaseChapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dB) {
        String CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE + "(" +
                COLUMN_ID + " " + "INTEGER PRIMARY KEY," +
                COLUMN_SUBJECT + " " + " TEXT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_YEAR + " INTEGER," +
                COLUMN_MONTH + " INTEGER," +
                COLUMN_DAY + " INTEGER" + ")";

        dB.execSQL(CREATE_TABLE_CHAPTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addChapter(Chapter chapter) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, chapter.getParentClass());
        values.put(COLUMN_NAME, chapter.getName());
        values.put(COLUMN_DESCRIPTION, chapter.getDescription());
        values.put(COLUMN_YEAR, chapter.getYear());
        values.put(COLUMN_MONTH, chapter.getMonth());
        values.put(COLUMN_DAY, chapter.getDay());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE, null, values);
        db.close();
    }

    public boolean deleteAssignment(String assignmentname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE + " WHERE " + COLUMN_NAME + " =  \"" + assignmentname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Subject mSubject = new Subject();

        if (cursor.moveToFirst()) {
            mSubject.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(mSubject.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public Cursor getAssignments(String pClass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_SUBJECT + " = '" + pClass + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor;
    }

    public boolean isAssignmentDue() {
        String query = "Select * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

      /* if (cursor.moveToFirst()) {
            do {
                if (checkDate(cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR)), cursor.getInt(cursor.getColumnIndex(COLUMN_MONTH)), cursor.getInt(cursor.getColumnIndex(COLUMN_DAY))))
                    return true;
            } while (cursor.moveToNext());
        }*/
        return false;
    }

    public void changeName(String name, String newname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newname);
        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        db.close();
    }

    public void changeDescription(String description, String newdescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, newdescription);
        db.update(TABLE, values, COLUMN_DESCRIPTION + " = '" + description + "'", null);
        db.close();
    }

    public void changeDay(String name, int day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, day);
        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        db.close();
    }

    public void changeMonth(String name, int month) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONTH, month);
        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        db.close();
    }

    public void changeYear(String name, int Year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_YEAR, Year);
        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        db.close();
    }

    public void changeClass(String name, String newname) {
        String query = "Select * FROM " + TABLE + " WHERE " + COLUMN_SUBJECT + " =  \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, newname);
        if(cursor.moveToFirst()) {
            db.update(TABLE, values, COLUMN_SUBJECT + " = '" + name + "'", null);
        }
        cursor.close();
        db.close();
    }

    private boolean checkDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.YEAR) == year) {
            if (c.get(Calendar.MONTH) + 1 == month) {
                return (day - c.get(Calendar.DAY_OF_MONTH) == 1);
            } else if (month - c.get(Calendar.MONTH) == 2) {
                return (day == 1);
            }
        } else if (c.get(Calendar.MONTH) == 11 && c.get(Calendar.DAY_OF_MONTH) == 31) {
            return (month == 1 && day == 1);
        }
    return false;
    }
}
