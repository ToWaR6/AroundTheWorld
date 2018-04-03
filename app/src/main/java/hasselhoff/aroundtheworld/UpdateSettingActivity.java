package hasselhoff.aroundtheworld;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import hasselhoff.aroundtheworld.database.DatabaseHandler;
import hasselhoff.aroundtheworld.database.PersonContract;

/**
 * Created by Marvin on 27/03/2018.
 */

public class UpdateSettingActivity extends SubActivity {
    private String firstname;
    private String name ;
    private String birthday ;
    private String gender ;
    private String weight;
    private String size ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.firstname = getIntent().getStringExtra(MySettingsActivity.FIRSTNAME);
        this.name = getIntent().getStringExtra(MySettingsActivity.NAME);
        this.birthday = getIntent().getStringExtra(MySettingsActivity.BIRTHDAY);
        this.gender = getIntent().getStringExtra(MySettingsActivity.GENDER);
        this.weight = getIntent().getStringExtra(MySettingsActivity.WEIGHT);
        this.size = getIntent().getStringExtra(MySettingsActivity.SIZE);

        ((EditText) findViewById(R.id.editFirstname)).setText(this.firstname);//Ajout texte par défaut dans le champ prénom
        ((EditText) findViewById(R.id.editName)).setText(this.name);
        ((EditText) findViewById(R.id.editBirthday)).setText(this.birthday);
        ((EditText) findViewById(R.id.editGender)).setText(this.gender);
        ((EditText) findViewById(R.id.editWeight)).setText(this.weight);
        ((EditText) findViewById(R.id.editSize)).setText(this.size);
    }
    public void updateSetting(View view){
        DatabaseHandler myDatabaseHandler = new DatabaseHandler(UpdateSettingActivity.this);
        SQLiteDatabase db = myDatabaseHandler.getReadableDatabase();
        ContentValues values = new ContentValues();
        String firstname = ((EditText) findViewById(R.id.editFirstname)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.editName)).getText().toString().trim();
        String birthday = ((EditText) findViewById(R.id.editBirthday)).getText().toString().trim();
        String gender = ((EditText) findViewById(R.id.editGender)).getText().toString().trim();
        String weight = ((EditText) findViewById(R.id.editWeight)).getText().toString().trim();
        String size = ((EditText) findViewById(R.id.editSize)).getText().toString().trim();
        String dateFormat = getResources().getString(R.string.dateFormat);
        String anniversaire  = getResources().getString(R.string.birthday);
        birthday = validFormat(dateFormat,birthday.trim());//Format the date or return null
        if(birthday.equals("")){
            Toast.makeText(this.getBaseContext(),anniversaire+dateFormat,Toast.LENGTH_SHORT).show();
            birthday = this.birthday;
        }
        values.put(PersonContract.FeedEntry.COLUMN_FIRSTNAME, firstname);
        values.put(PersonContract.FeedEntry.COLUMN_NAME, name);
        values.put(PersonContract.FeedEntry.COLUMN_BIRTHDAY, birthday);
        values.put(PersonContract.FeedEntry.COLUMN_GENDER, gender);
        values.put(PersonContract.FeedEntry.COLUMN_WEIGHT, weight);
        values.put(PersonContract.FeedEntry.COLUMN_SIZE, size);


        db.update(PersonContract.FeedEntry.TABLE_NAME, values,null,null);
        UpdateSettingActivity.this.finish();
    }

    public static String validFormat(String format, String value) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                value= "";
            }
        } catch (java.text.ParseException e) {
            value= "";
            e.printStackTrace();
        }
        return value;
    }
}
