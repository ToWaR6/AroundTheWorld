package hasselhoff.aroundtheworld;

import android.support.v7.app.AppCompatActivity;


public class SubActivity extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
