package hasselhoff.aroundtheworld.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.Adapter.AdapterMessage;
import hasselhoff.aroundtheworld.Model.Message;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(ChatActivity.this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(ChatActivity.this, R.drawable.divider));
        recyclerView.addItemDecoration(myDivider );
        editText = findViewById(R.id.messageToSend);
        handler = new Handler();
        idNetwork = getIntent().getIntExtra("ID_NETWORK",-1);
        if(idNetwork!=1){
            updateChat();
        }
    }
    public void refreshAction(MenuItem v){
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
            messages = getMessagesFromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new AdapterMessage(this,messages));
        recyclerView.smoothScrollToPosition(messages.size());
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
    private Message getMessageFromJSON(JSONObject json) throws JSONException {
        return new Message(json.getString("app_message_content"),json.getString("app_message_timestamp"), json.getString("app_user_name"),
                    json.getString("app_user_first_name"));


    }
    private ArrayList<Message> getMessagesFromJson(JSONObject json) throws JSONException {
        ArrayList<Message> list = new ArrayList();
        JSONArray arrayNetwork = json.getJSONArray("messages");
        JSONObject message;
        for (int i = 0; i < arrayNetwork.length(); i++) {
            message = ((JSONObject) arrayNetwork.get(i));
            list.add(getMessageFromJSON(message));
        }
        return list;
    }
}
