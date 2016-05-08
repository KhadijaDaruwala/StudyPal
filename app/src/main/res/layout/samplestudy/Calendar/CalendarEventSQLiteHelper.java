package layout.samplestudy.Calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarEventSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAILS = "details";
    public static final String COLUMN_ALLDAY = "allday";

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EVENTS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_DATE + " text not null,"
            + COLUMN_TITLE + " text not null,"
            + COLUMN_DETAILS + " text not null,"
            + COLUMN_ALLDAY + " integer not null"
            + ");";

    public CalendarEventSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }
}
