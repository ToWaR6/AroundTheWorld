package hasselhoff.aroundtheworld;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hasselhoff.aroundtheworld.database.KeyPreferences;

public class MainActivity extends SubActivity {

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
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(KeyPreferences.PREFS,MODE_PRIVATE);
        String currentCity = sharedPreferences.getString(KeyPreferences.CITY,"");
        if(currentCity.equals(""))
            sharedPreferences.edit().putString(KeyPreferences.CITY,getString(R.string.firstCity)).apply();
    }

    public void openNewsAct(View view) {
        newActivity(NewsActivity.class);
    }

    public void openSocialAct(View view) {
        newActivity(SocialActivity.class);
    }

    public void openShopAct(View view) {
        newActivity(ShopActivity.class);
    }

    public void openAdsAct(View view) {
        newActivity(AdsActivity.class);
    }

    public void openMyCity (MenuItem menuItem){
        newActivity(CityActivity.class);
    }

    public void openMyParam (MenuItem menuItem){newActivity(MySettingsActivity.class);}

    public void openLike(MenuItem menuItem){newActivity(LikeActivity.class);}


}
