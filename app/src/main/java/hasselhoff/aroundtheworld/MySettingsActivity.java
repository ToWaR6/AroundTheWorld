package hasselhoff.aroundtheworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import hasselhoff.aroundtheworld.database.KeyPreferences;


public class MySettingsActivity extends SubActivity {

    public final static String FIRSTNAME = "firstName";
    public final static String NAME = "name";
    public final static String BIRTHDAY = "birthday";
    public final static String GENDER = "gender";
    public final static String WEIGHT = "weight";
    public final static String SIZE = "size";
    private Intent updateActivity ;
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
        SharedPreferences sharedPreferences = getSharedPreferences(KeyPreferences.PREFS,MODE_PRIVATE);
        String name = sharedPreferences.getString(KeyPreferences.NAME,"");
       ((TextView) findViewById(R.id.name)).setText(name);
        updateActivity.putExtra(NAME,name);

        String firstName =  sharedPreferences.getString(KeyPreferences.FIRSTNAME,"");
        ((TextView) findViewById(R.id.firstname)).setText(firstName);
        updateActivity.putExtra(FIRSTNAME,firstName);

        String birthday =  sharedPreferences.getString(KeyPreferences.BIRTHDAY,"");
        ((TextView) findViewById(R.id.birthday)).setText(birthday);
        updateActivity.putExtra(BIRTHDAY,birthday);

        String gender = sharedPreferences.getString(KeyPreferences.GENDER,"");
       ((TextView) findViewById(R.id.gender)).setText(gender);
        updateActivity.putExtra(GENDER,gender);

        String weight =  sharedPreferences.getString(KeyPreferences.WEIGHT,"");
        ((TextView) findViewById(R.id.weight)).setText(weight);
        updateActivity.putExtra(WEIGHT,weight);

        String size = sharedPreferences.getString(KeyPreferences.SIZE,"");
       ((TextView) findViewById(R.id.size)).setText(size);
        updateActivity.putExtra(SIZE,size);
    }

    public void openUpdateSetting(View view) {
            updateActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0,0);
            startActivityForResult(updateActivity, 0);
    }

}
