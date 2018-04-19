package hasselhoff.aroundtheworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchAd;
import hasselhoff.aroundtheworld.remote_fetch.DownloadImageTask;

public class AdsActivity extends SubActivity {
    private Handler handler;
    private String currentCity;
    private String adUrl;
    private ImageView adImage;
    private TextView adTitle;
    private Button likeButton;
    private Button dislikeButton;
    private Button withoutOpinionButton;
    private ArrayList<String> adId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        adId = new ArrayList<>();
        setContentView(R.layout.activity_ads);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        likeButton = findViewById(R.id.likeButton);
        dislikeButton = findViewById(R.id.dislikeButton);
        withoutOpinionButton = findViewById(R.id.withoutOpinionButton);
        adImage = findViewById(R.id.adImage);
        adTitle = findViewById(R.id.adTitle);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
        currentCity = sharedPreferences.getString(Preferences.CITY,"");
        getNewAd(currentCity);
    }

    protected void getNewAd(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchAd.getJSON(AdsActivity.this,city);
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdsActivity.this, AdsActivity.this.getString(R.string.ad_not_found), Toast.LENGTH_LONG).show();
                            renderOriginAd();
                        }
                    });
                }else{
                    handler.post(new Runnable(){
                        public void run(){
                            renderAd(json);
                        }
                    });
                }
            }
        }.start();
    }
    public void getOnlyNewAd(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchAd.postJSON(AdsActivity.this,city,adId);
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdsActivity.this, AdsActivity.this.getString(R.string.ad_not_found), Toast.LENGTH_LONG).show();
                            renderOriginAd();
                        }
                    });
                }else{
                    handler.post(new Runnable(){
                        public void run(){
                            renderAd(json);
                        }
                    });
                }
            }
        }.start();
    }

    public void likeAd(final String _id){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchAd.likeAd(AdsActivity.this,_id);
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdsActivity.this, AdsActivity.this.getString(R.string.ad_not_found), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(AdsActivity.this, AdsActivity.this.getString(R.string.feedback), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }.start();
    }
    public void dislikeAd(final String _id){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchAd.dislikeAd(AdsActivity.this,_id);
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdsActivity.this, AdsActivity.this.getString(R.string.ad_not_found), Toast.LENGTH_LONG).show();
                            renderOriginAd();
                        }
                    });
                }else{
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(AdsActivity.this, AdsActivity.this.getString(R.string.feedback), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }.start();
    }
    public void renderAd(JSONObject json){
        try{
            adUrl = json.getString("advertisingURL");
            adId.add(json.getString("_id")); //For like and dislike button
            new DownloadImageTask(adImage).execute(json.getString("imageURL"));
            adTitle.setText(json.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void renderOriginAd(){
        adImage.setImageDrawable(ContextCompat.getDrawable(AdsActivity.this,R.drawable.pub));
        adTitle.setText(R.string.ad);
        dislikeButton.setEnabled(false);
        likeButton.setEnabled(false);
        withoutOpinionButton.setEnabled(false);
        adUrl = null;
    }

    public void withoutOpinion(View view){
        getOnlyNewAd(currentCity);
    }
    public void like(View view){
        likeAd(adId.get(adId.size() -1 ));
        getOnlyNewAd(currentCity);
    }
    public void dislike(View view){
        dislikeAd(adId.get(adId.size() -1 ));
        getOnlyNewAd(currentCity);
    }

    public void openBrowser(View view){
        if(adUrl != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(adUrl));
            startActivity(intent);
        }
    }

}
