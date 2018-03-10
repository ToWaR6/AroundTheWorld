package hasselhoff.aroundtheworld;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class NewsActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

}
