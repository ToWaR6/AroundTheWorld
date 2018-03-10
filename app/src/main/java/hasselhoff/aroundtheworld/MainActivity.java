package hasselhoff.aroundtheworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Create an activity with the default option
     * @param activityClass Class of the activity to create
     */
    public void  newActivity(Class<?> activityClass){
        Intent intent = new Intent(this,activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivityForResult(intent, 0);
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

}
