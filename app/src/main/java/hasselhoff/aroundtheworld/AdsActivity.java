package hasselhoff.aroundtheworld;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import hasselhoff.aroundtheworld.database.DatabaseHandler;
import hasselhoff.aroundtheworld.database.PersonContract;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchAd;

public class AdsActivity extends SubActivity {
    private Handler handler;
    private String currentCity;
    private ImageView adImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_ads);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        adImage = findViewById(R.id.adImage);

        DatabaseHandler myDatabaseHandler = new DatabaseHandler(AdsActivity.this);
        SQLiteDatabase db = myDatabaseHandler.getReadableDatabase();
        String[] projection = {
                PersonContract.FeedEntry.COLUMN_CITY
        };
        Cursor cursor = db.query(
                PersonContract.FeedEntry.TABLE_NAME,
                projection,
                null, //Where clause
                null, //Where clause
                null, //Don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        if(cursor.moveToFirst()){
            currentCity = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_CITY));
        }
        cursor.close();
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
    public void renderAd(JSONObject json){
        try{
            Log.i("Test",json.getString("imageURL"));
            URL url = new URL(json.getString("imageURL"));
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            adImage.setImageBitmap(bmp);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
