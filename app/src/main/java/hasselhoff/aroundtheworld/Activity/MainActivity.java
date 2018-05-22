package hasselhoff.aroundtheworld.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;

public class MainActivity extends SubActivity {
    SharedPreferences sharedPreferences;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        sharedPreferences = getBaseContext().getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
        String currentCity = sharedPreferences.getString(Preferences.CITY,"");
        if(currentCity.equals(""))
            sharedPreferences.edit().putString(Preferences.CITY,getString(R.string.firstCity)).apply();


    }

    public void openNewsAct(View view) {
        newActivity(NewsActivity.class);
    }

    public void openSocialAct(View view) {
        int id = sharedPreferences.getInt(Preferences.ID,-1);
        if(id!=-1)
            newActivity(SocialActivity.class);
        else{
            Toast.makeText(this, R.string.connect_please, Toast.LENGTH_SHORT).show();
            newActivity(MySettingsActivity.class);
        }
    }

    public void openShopAct(View view) {
        newActivity(ShopActivity.class);
    }

    public void openAdsAct(View view) { newActivity(AdsActivity.class); }

    public void openMyCity (MenuItem menuItem){
        newActivity(CityActivity.class);
    }

    public void openMyParam (MenuItem menuItem) {
        newActivity(MySettingsActivity.class);
    }

    public void openLike(MenuItem menuItem){newActivity(LikeActivity.class);}


}
