package hasselhoff.aroundtheworld;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import hasselhoff.aroundtheworld.database.DatabaseHandler;
import hasselhoff.aroundtheworld.database.PersonContract;

/**
 * Created by Marvin on 27/03/2018.
 */

public class UpdateSettingActivity extends SubActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        String firstname = getIntent().getStringExtra(MySettingsActivity.FIRSTNAME);
        String name = getIntent().getStringExtra(MySettingsActivity.NAME);
        String birthday = getIntent().getStringExtra(MySettingsActivity.BIRTHDAY);
        String gender = getIntent().getStringExtra(MySettingsActivity.GENDER);
        String weight = getIntent().getStringExtra(MySettingsActivity.WEIGHT);
        String size = getIntent().getStringExtra(MySettingsActivity.SIZE);

        ((EditText) findViewById(R.id.editFirstname)).setText(firstname);//Ajout texte par défaut dans le champ prénom
        ((EditText) findViewById(R.id.editName)).setText(name);
        ((EditText) findViewById(R.id.editBirthday)).setText(birthday);
        ((EditText) findViewById(R.id.editGender)).setText(gender);
        ((EditText) findViewById(R.id.editWeight)).setText(weight);
        ((EditText) findViewById(R.id.editSize)).setText(size);
    }
    public void updateSetting(View view){
        DatabaseHandler myDatabaseHandler = new DatabaseHandler(UpdateSettingActivity.this);
        SQLiteDatabase db = myDatabaseHandler.getReadableDatabase();
        ContentValues values = new ContentValues();
        String firstname = ((EditText) findViewById(R.id.editFirstname)).getText().toString();
        String name = ((EditText) findViewById(R.id.editName)).getText().toString();
        String birthday = ((EditText) findViewById(R.id.editBirthday)).getText().toString();
        String gender = ((EditText) findViewById(R.id.editGender)).getText().toString();
        String weight = ((EditText) findViewById(R.id.editWeight)).getText().toString();
        String size = ((EditText) findViewById(R.id.editSize)).getText().toString();

        values.put(PersonContract.FeedEntry.COLUMN_FIRSTNAME, firstname);
        values.put(PersonContract.FeedEntry.COLUMN_NAME, name);
        values.put(PersonContract.FeedEntry.COLUMN_BIRTHDAY, birthday);
        values.put(PersonContract.FeedEntry.COLUMN_GENDER, gender);
        values.put(PersonContract.FeedEntry.COLUMN_WEIGHT, weight);
        values.put(PersonContract.FeedEntry.COLUMN_SIZE, size);


        db.update(PersonContract.FeedEntry.TABLE_NAME, values,null,null);
        UpdateSettingActivity.this.finish();
    };
}
