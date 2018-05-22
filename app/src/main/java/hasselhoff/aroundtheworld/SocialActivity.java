package hasselhoff.aroundtheworld;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;

import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNetwork;

public class SocialActivity extends SubActivity {
    Handler handler;
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.social_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        handler = new Handler();
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchNetwork.getNetwork();
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.networks_not_found), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    handler.post(new Runnable(){
                        public void run(){
                            renderNetwork(json);
                        }
                    });
                }
            }
        }.start();
    }
    public void createSub(MenuItem view){
        DialogFragment newFragment = new newSubFragment();
        newFragment.show(getFragmentManager(),"New sub");
    }

    public void renderNetwork(JSONObject json){
        Log.i("fd",json.toString());
    }
}
