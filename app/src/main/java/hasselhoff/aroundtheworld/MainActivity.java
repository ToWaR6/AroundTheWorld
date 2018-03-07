package hasselhoff.aroundtheworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
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

    public void openAdsAct(View view) {
        Intent intent = new Intent(this, AdsActivity.class);
        startActivity(intent);
    }

    public void openShopAct(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    public void openNewsAct(View view) {
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }

    public void openSocialAct(View view) {
        Intent intent = new Intent(this, SocialActivity.class);
        startActivity(intent);
    }

}
