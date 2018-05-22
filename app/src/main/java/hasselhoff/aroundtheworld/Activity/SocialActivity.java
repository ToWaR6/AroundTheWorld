package hasselhoff.aroundtheworld.Activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.Adapter.AdapterNetwork;
import hasselhoff.aroundtheworld.Fragment.newSubFragment;
import hasselhoff.aroundtheworld.Model.Network;
import hasselhoff.aroundtheworld.R;
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
        ArrayList<Network> networks = new ArrayList();
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(SocialActivity.this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(SocialActivity.this, R.drawable.divider));
        recyclerView.addItemDecoration(myDivider );
        recyclerView.setAdapter(new AdapterNetwork(this,networks));
    }
    public void createSub(MenuItem view){
        DialogFragment newFragment = new newSubFragment();
        newFragment.show(getFragmentManager(),"New sub");
    }

    public void renderNetwork(JSONObject json){
        ArrayList<Network> networks = new ArrayList();
        try {
            networks = getNetworkFromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(SocialActivity.this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(SocialActivity.this, R.drawable.divider));
        recyclerView.addItemDecoration(myDivider );
        recyclerView.setAdapter(new AdapterNetwork(this,networks));
    }

    private ArrayList<Network> getNetworkFromJson(JSONObject json) throws JSONException {
        ArrayList<Network> list = new ArrayList();
        JSONArray arrayNetwork = json.getJSONArray("networks");
        JSONObject network;
        for (int i = 0; i < arrayNetwork.length(); i++) {
            network = ((JSONObject) arrayNetwork.get(i));
            list.add(new Network(network.getInt("app_network_number_subscriber"),network.getString("app_network_private"),network.getString("app_network_name")
                    ,network.getString("app_network_city"),network.getInt("app_network_id"),network.getString("app_network_id_user")));
        }
        Log.i("LISt",list.toString());
        return list;
    }
}
