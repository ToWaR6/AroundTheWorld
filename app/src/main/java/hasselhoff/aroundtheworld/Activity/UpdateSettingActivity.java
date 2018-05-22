package hasselhoff.aroundtheworld.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNetwork;


public class UpdateSettingActivity extends SubActivity {
    private String birthday ;
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        String firstName = getIntent().getStringExtra(MySettingsActivity.FIRSTNAME);
        String name = getIntent().getStringExtra(MySettingsActivity.NAME);
        this.birthday = getIntent().getStringExtra(MySettingsActivity.BIRTHDAY);
        String gender = getIntent().getStringExtra(MySettingsActivity.GENDER);
        String weight = getIntent().getStringExtra(MySettingsActivity.WEIGHT);
        String size = getIntent().getStringExtra(MySettingsActivity.SIZE);

        ((EditText) findViewById(R.id.editFirstname)).setText(firstName);//Ajout texte par défaut dans le champ prénom
        ((EditText) findViewById(R.id.editName)).setText(name);
        ((EditText) findViewById(R.id.editBirthday)).setText(this.birthday);
        ((EditText) findViewById(R.id.editGender)).setText(gender);
        ((EditText) findViewById(R.id.editWeight)).setText(weight);
        ((EditText) findViewById(R.id.editSize)).setText(size);
    }
    public void updateSetting(View view){
        final SharedPreferences sharedPreferences = getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
        String firstName = ((EditText) findViewById(R.id.editFirstname)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.editName)).getText().toString().trim();
        String birthday = ((EditText) findViewById(R.id.editBirthday)).getText().toString().trim();
        String gender = ((EditText) findViewById(R.id.editGender)).getText().toString().trim();
        String weight = ((EditText) findViewById(R.id.editWeight)).getText().toString().trim();
        String size = ((EditText) findViewById(R.id.editSize)).getText().toString().trim();
        String dateFormat = getResources().getString(R.string.dateFormat);
        String birthdayText  = getResources().getString(R.string.birthday);
        birthday = validFormat(dateFormat,birthday.trim());//Format the date or return null
        if(birthday.equals("")){
            Toast.makeText(this.getBaseContext(),birthdayText+dateFormat,Toast.LENGTH_SHORT).show();
            birthday = this.birthday;
        }

        int id = sharedPreferences.getInt(Preferences.ID,-1);
        final JSONObject  postData = new JSONObject();
        try {
            postData.put("name",name);
            postData.put("first_name",firstName);
            postData.put("birthday",birthday);
            postData.put("weight",weight);
            postData.put("size",size);
            postData.put("gender",gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("data",postData.toString());
        if(id == -1){
            new Thread(){
                public void run(){
                    final JSONObject json =  RemoteFetchNetwork.createUser(postData);
                    if(json != null) {
                        handler.post(new Runnable(){
                            public void run(){
                            try {
                                int id = json.getInt("id");
                                sharedPreferences.edit().putInt(Preferences.ID, id).apply();
                                Log.i("Connect","connect");
                            } catch(JSONException e){
                                    e.printStackTrace();
                            }
                            }
                        });
                    }
                }
            }.start();
        }else{
            try {
                postData.put("id_user",id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new Thread(){
                public void run(){
                   RemoteFetchNetwork.updateUser(postData);
                }
            }.start();
        }

        sharedPreferences.edit().putString(Preferences.NAME,name)
                .putString(Preferences.FIRSTNAME,firstName)
                .putString(Preferences.BIRTHDAY,birthday)
                .putString(Preferences.GENDER,gender)
                .putString(Preferences.WEIGHT,weight)
                .putString(Preferences.SIZE,size)
                .apply();
        UpdateSettingActivity.this.finish();
    }

    public static String validFormat(String format, String value) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
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
