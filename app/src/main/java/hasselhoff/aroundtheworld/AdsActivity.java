package hasselhoff.aroundtheworld;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class AdsActivity extends SubActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

}
