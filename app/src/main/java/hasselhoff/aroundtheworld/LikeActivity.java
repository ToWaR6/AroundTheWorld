package hasselhoff.aroundtheworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

public class LikeActivity extends SubActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }
}
