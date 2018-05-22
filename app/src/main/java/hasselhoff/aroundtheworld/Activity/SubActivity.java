package hasselhoff.aroundtheworld.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public abstract class SubActivity extends AppCompatActivity {

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
    public void  newActivity(Class<?> activityClass){
        Intent intent = new Intent(this,activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivityForResult(intent, 0);
    }

}
