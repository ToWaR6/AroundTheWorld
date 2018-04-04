package hasselhoff.aroundtheworld;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hasselhoff.aroundtheworld.database.DatabaseHandler;
import hasselhoff.aroundtheworld.database.PersonContract;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchWeather;

/**
 * Created by Marvin on 03/04/2018.
 */

public class WeatherFragment extends Fragment{

    Handler handler;

    TextView dateTextView;
    TextView weatherTextView;
    TextView humidityTextView;
    TextView temperatureTextView;
    public WeatherFragment(){
        handler = new Handler();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView =  inflater.inflate(R.layout.fragment_weather,container,false);
        dateTextView = (TextView)rootView.findViewById(R.id.dateWeather);
        weatherTextView = (TextView)rootView.findViewById(R.id.weather);
        humidityTextView = (TextView)rootView.findViewById(R.id.humidity);
        temperatureTextView = (TextView)rootView.findViewById(R.id.temperature);
        return rootView;
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        DatabaseHandler myDatabaseHandler = new DatabaseHandler(getActivity());
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
        String currentCity= "Paris";
        if(cursor.moveToFirst()==true){
            currentCity = cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COLUMN_CITY));
        };
        cursor.close();
        updateWeatherData(currentCity);
    }
    public void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetchWeather.getJSON(getActivity(),city);
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.weather_not_found), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try{
           SimpleDateFormat df = new SimpleDateFormat("EEEE dd MMMM  HH:mm ");
           String date = df.format(new Date(json.getLong("dt")*1000));
           dateTextView.setText(date);
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise")*1000,
                    json.getJSONObject("sys").getLong("sunset")*1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherIcon(int actualId, long sunrise,long sunset){
        int id = actualId/100; //Car api supporte plus de météo differente que nous
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset)
                icon = "Jour";
            else
                icon = "Nuit";
        }else{
            switch (id){
                case 2 :
                    icon = "Orage";
                    break;
                case 3 :
                    icon = "Bruine";
                    break;
                case 7 :
                    icon = "Brouillard";
                    break;
                case 8 :
                    icon = "Nuageux";
                    break;
                case 6 :
                    icon = "Neige";
                    break;
                case 5 :
                    icon = "Pluie";
                    break;
            }
        }
        weatherTextView.setText(icon);
    }

}

