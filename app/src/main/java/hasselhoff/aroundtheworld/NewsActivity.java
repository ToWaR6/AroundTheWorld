package hasselhoff.aroundtheworld;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class NewsActivity extends SubActivity {

    NewsFragment newsFragment;
    WeatherFragment weatherFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        newsFragment = new NewsFragment();
        weatherFragment = new WeatherFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
    }
    public void openWeatherFragment(View view){
        findViewById(R.id.buttonWeather).setEnabled(false);
        findViewById(R.id.buttonNews).setEnabled(true);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,weatherFragment);
        transaction.commit();
    }
    public void openNewsFragment(View view){
        findViewById(R.id.buttonNews).setEnabled(false);
        findViewById(R.id.buttonWeather).setEnabled(true);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
    }


}
