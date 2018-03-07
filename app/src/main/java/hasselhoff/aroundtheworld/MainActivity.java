package hasselhoff.aroundtheworld;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
