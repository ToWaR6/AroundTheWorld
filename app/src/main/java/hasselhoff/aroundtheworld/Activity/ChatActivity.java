package hasselhoff.aroundtheworld.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.Adapter.AdapterMessage;
import hasselhoff.aroundtheworld.Adapter.AdapterNetwork;
import hasselhoff.aroundtheworld.Model.Message;
import hasselhoff.aroundtheworld.Model.Network;
import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNetwork;

public class ChatActivity extends SubActivity {
    private int idNetwork;
    private EditText editText;
    private Handler handler;
    private RecyclerView recyclerView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_chat, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        recyclerView= findViewById(R.id.list);
        editText = findViewById(R.id.messageToSend);
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
                    handler.post(new Runnable(){
                        public void run(){
                            renderMessages(json);
                        }
                    });
                }
            }
        }.start();
    }

    public void renderMessages(JSONObject json){
        ArrayList<Message> messages = new ArrayList();
        try {
            messages = getNetworkFromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(ChatActivity.this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(ChatActivity.this, R.drawable.divider));
        recyclerView.addItemDecoration(myDivider );
        recyclerView.setAdapter(new AdapterMessage(this,messages));
    }

    public void sendMessage(View v){
        SharedPreferences sharedPreferences = getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
        int id = sharedPreferences.getInt(Preferences.ID,-1);
        String content = editText.getText().toString();
        editText.setText("");
        if(!content.isEmpty() && id != -1){
            final JSONObject postData= new JSONObject();
            try {
                postData.put("id_network",idNetwork);
                postData.put("id_user",id);
                postData.put("content",content);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new Thread(){
                public void run(){
                    RemoteFetchNetwork.postMessage(postData);
                    final JSONObject json = RemoteFetchNetwork.getMessages(postData);
                    if(json!=null){
                        handler.post(new Runnable(){
                            public void run(){
                                renderMessages(json);
                            }
                        });
                    }
                }
            }.start();
        }
    }

    private ArrayList<Message> getNetworkFromJson(JSONObject json) throws JSONException {
        ArrayList<Message> list = new ArrayList();
        JSONArray arrayNetwork = json.getJSONArray("messages");
        JSONObject message;
        for (int i = 0; i < arrayNetwork.length(); i++) {
            message = ((JSONObject) arrayNetwork.get(i));
            list.add(new Message(message.getString("app_message_content"),message.getString("app_message_timestamp")));
        }
        return list;
    }
}
