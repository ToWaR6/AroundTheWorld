package hasselhoff.aroundtheworld;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import hasselhoff.aroundtheworld.database.DatabaseHandler;
import hasselhoff.aroundtheworld.database.PersonContract;

public class MySettingsActivity extends SubActivity {

    public final static String FIRSTNAME = "Firsname";
    public final static String NAME = "name";
    public final static String BIRTHDAY = "birthday";
    public final static String GENDER = "gender";
    public final static String WEIGHT = "weight";
    public final static String SIZE = "size";
    public Intent updateActivity ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        updateActivity = new Intent(this,UpdateSettingActivity.class);
        loadActivity();
    }
    protected void onResume(){
        super.onResume();
        loadActivity();
    }
    private void loadActivity(){
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
            if (name!=null) ((TextView) findViewById(R.id.name)).setText(name);
            updateActivity.putExtra(NAME,name);

            String firstname = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_FIRSTNAME));
            if (firstname!=null) ((TextView) findViewById(R.id.firstname)).setText(firstname);
            updateActivity.putExtra(FIRSTNAME,firstname);

            String birthday = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_BIRTHDAY));
            if (birthday!=null) ((TextView) findViewById(R.id.birthday)).setText(birthday);
            updateActivity.putExtra(BIRTHDAY,birthday);

            String gender = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_GENDER));
            if (gender!=null) ((TextView) findViewById(R.id.gender)).setText(gender);
            updateActivity.putExtra(GENDER,gender);

            String weight = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_WEIGHT));
            if (weight!=null) ((TextView) findViewById(R.id.weight)).setText(weight);
            updateActivity.putExtra(WEIGHT,weight);

            String size = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_SIZE));
            if (size!=null) ((TextView) findViewById(R.id.size)).setText(size);
            updateActivity.putExtra(SIZE,size);
        }
    }

    public void openUpdateSetting(View view) {
            updateActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0,0);
            startActivityForResult(updateActivity, 0);
    }

}
