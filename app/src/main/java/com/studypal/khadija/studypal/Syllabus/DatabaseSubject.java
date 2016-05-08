package com.studypal.khadija.studypal.Syllabus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studypal.khadija.studypal.Syllabus.Subject;

public class DatabaseSubject extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "subjectDB.db";

    private static final String TABLE = "subjects";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "subjectname";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_COLOR = "color";

    public DatabaseSubject(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dB) {
        String CREATE_TABLE_SUBJECT = "CREATE TABLE " + TABLE + "(" +
                COLUMN_ID + " " + "INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_QUANTITY + " INTEGER," +
                COLUMN_COLOR + " INTEGER" + ")";

        dB.execSQL(CREATE_TABLE_SUBJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
    }

    public void addSubject(Subject mSubject) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, mSubject.getSubjectName());
        values.put(COLUMN_QUANTITY, mSubject.getQuantity());
        values.put(COLUMN_COLOR, mSubject.getColor());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE, null, values);
        db.close();
    }

 /*   public boolean deleteSubject(String subjectname) {

        boolean result = false;
        String query = "Select * FROM " + TABLE + " WHERE " + COLUMN_NAME + " =  \"" + subjectname + "\"";
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
    }*/

    public void addQuantity(String name) {
        String query = "Select * FROM " + TABLE + " WHERE " + COLUMN_NAME + " =  \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        ContentValues values = new ContentValues();

        values.put(COLUMN_QUANTITY, cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)) + 1);

        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        cursor.close();
        db.close();
    }

   public void changeName(String name, String newname) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newname);

        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        db.close();
    }

    public void changeColor(String name, int color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR, color);

        db.update(TABLE, values, COLUMN_NAME + " = '" + name + "'", null);
        db.close();
    }

    public void decrementQuantity(String subjectname) {
        String query = "Select * FROM " + TABLE + " WHERE " + COLUMN_NAME + " =  \"" + subjectname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        ContentValues values = new ContentValues();

        values.put(COLUMN_QUANTITY, cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)) - 1);

        db.update(TABLE, values, COLUMN_NAME + " = '" + subjectname + "'", null);
        cursor.close();
        db.close();
    }

    public Cursor getSubjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_QUANTITY + ", " + COLUMN_COLOR + " FROM " + TABLE;
        Cursor cursor =  db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor;
    }

    public boolean findSubject(String name) {
        String query = "Select * FROM " + TABLE + " WHERE " + COLUMN_NAME + " =  \"" + name + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean val = cursor.moveToFirst();
        cursor.close();
        db.close();
        return val;
    }
}