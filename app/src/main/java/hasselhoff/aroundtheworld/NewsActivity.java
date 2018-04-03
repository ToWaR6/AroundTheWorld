package hasselhoff.aroundtheworld;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class NewsActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    public void openWeatherFragment(View view){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
    }


}
