package hasselhoff.aroundtheworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


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


}
