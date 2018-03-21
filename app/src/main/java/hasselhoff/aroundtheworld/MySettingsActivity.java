package hasselhoff.aroundtheworld;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import hasselhoff.aroundtheworld.database.DatabaseHandler;
import hasselhoff.aroundtheworld.database.PersonContract;

public class MySettingsActivity extends SubActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        DatabaseHandler myDatabaseHandler = new DatabaseHandler(MySettingsActivity.this);
        SQLiteDatabase db = myDatabaseHandler.getReadableDatabase();
        String[] projection = {
                PersonContract.FeedEntry.COLUMN_NAME,
                PersonContract.FeedEntry.COLUMN_FIRSTNAME,
                PersonContract.FeedEntry.COLUMN_BIRTHDAY,
                PersonContract.FeedEntry.COLUMN_GENDER,
                PersonContract.FeedEntry.COLUMN_WEIGHT,
                PersonContract.FeedEntry.COLUMN_SIZE,
        };
        Cursor cursor = db.query(
                PersonContract.FeedEntry.TABLE_NAME,
                projection,
                null, //Where clause
                null, //Where clause
                null, //Don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        if(cursor.moveToFirst()==true){
            String name = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_NAME));
            if (name!=null) ((TextView) findViewById(R.id.name)).append(name);

            String firstname = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_FIRSTNAME));
            if (firstname!=null) ((TextView) findViewById(R.id.firstname)).append(firstname);

            String birthday = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_BIRTHDAY));
            if (birthday!=null) ((TextView) findViewById(R.id.birthday)).append(birthday);

            String gender = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_GENDER));
            if (gender!=null) ((TextView) findViewById(R.id.gender)).append(gender);

            String weight = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_WEIGHT));
            if (weight!=null) ((TextView) findViewById(R.id.weight)).append(weight);

            String size = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_SIZE));
            if (size!=null) ((TextView) findViewById(R.id.size)).append(size);
        }
    }

}
