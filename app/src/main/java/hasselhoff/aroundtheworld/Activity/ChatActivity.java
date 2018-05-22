package hasselhoff.aroundtheworld.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import org.json.JSONException;
import org.json.JSONObject;

import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNetwork;

public class ChatActivity extends SubActivity {
    int idNetwork;
    Handler handler;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_chat, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        handler = new Handler();
        idNetwork = getIntent().getIntExtra("ID_NETWORK",-1);
        if(idNetwork!=1){
            updateChat();
        }
    }

    public void updateChat(){
        final JSONObject postData= new JSONObject();
        try {
            postData.put("id_network",idNetwork);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchNetwork.getMessages(postData);
                if(json!=null){
                    renderMessages(json);
                }
            }
        }.start();
    }

    public void renderMessages(JSONObject json){
        Log.i("json",json.toString());
    }

}
