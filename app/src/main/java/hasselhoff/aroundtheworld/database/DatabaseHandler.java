package hasselhoff.aroundtheworld.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE  TABLE " + PersonContract.FeedEntry.TABLE_NAME + " ("  +
                    PersonContract.FeedEntry.COLUMN_NAME + " TEXT, " +
                    PersonContract.FeedEntry.COLUMN_FIRSTNAME + " TEXT, " +
                    PersonContract.FeedEntry.COLUMN_BIRTHDAY + " TEXT, " +
                    PersonContract.FeedEntry.COLUMN_GENDER + " TEXT, " +
                    PersonContract.FeedEntry.COLUMN_CITY + " TEXT," +
                    PersonContract.FeedEntry.COLUMN_SIZE + " REAL, " +
                    PersonContract.FeedEntry.COLUMN_WEIGHT + " REAL) ; ";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PersonContract.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DatabaseHandler.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("CREATE","NEW CREATE");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        ContentValues values = new ContentValues();
        values.put(PersonContract.FeedEntry.COLUMN_NAME,"Doe");
        values.put(PersonContract.FeedEntry.COLUMN_FIRSTNAME,"John");
        values.put(PersonContract.FeedEntry.COLUMN_CITY,"Montpellier");
        sqLiteDatabase.insert(PersonContract.FeedEntry.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
