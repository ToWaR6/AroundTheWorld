package hasselhoff.aroundtheworld.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import hasselhoff.aroundtheworld.R;

public class ChatActivity extends SubActivity {
    int idServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        idServer = getIntent().getIntExtra("ID_SERVER",-1);
    }

}
