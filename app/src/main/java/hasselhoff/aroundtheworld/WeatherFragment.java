package hasselhoff.aroundtheworld;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchWeather;

public class WeatherFragment extends Fragment{

    Handler handler;
    String currentCity;
    TextView dateTextView;
    ImageView weatherImageView;
    TextView humidityTextView;
    TextView temperatureTextView;
    TextView currentCityTextView;
    TextView maxTempTextView;
    TextView minTempTextView;
    ScrollView background;
    public WeatherFragment(){
        handler = new Handler();
        setRetainInstance(true); //Evite crash when rotating screen
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView =  inflater.inflate(R.layout.fragment_weather,container,false);
        dateTextView = rootView.findViewById(R.id.dateWeather);
        weatherImageView = rootView.findViewById(R.id.weather);
        humidityTextView = rootView.findViewById(R.id.humidity);
        temperatureTextView = rootView.findViewById(R.id.temperature);
        minTempTextView = rootView.findViewById(R.id.minimumTemp);
        maxTempTextView = rootView.findViewById(R.id.maximumTemp);
        background = rootView.findViewById(R.id.background);
        currentCityTextView = rootView.findViewById(R.id.currentCity);
        return rootView;
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Preferences.PREFS, Context.MODE_PRIVATE);
        currentCity = sharedPreferences.getString(Preferences.CITY,"");
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
            SimpleDateFormat df = new SimpleDateFormat("EEEE dd MMMM  HH:mm ",Locale.FRANCE);
            String date = df.format(new Date(json.getLong("dt")*1000));
            dateTextView.setText(date);
            currentCityTextView.setText(currentCity);
            JSONObject mainJson = json.getJSONObject("main");
            double constanteKelvin = 273.15;
            double minTemp = mainJson.getDouble("temp_min")- constanteKelvin;
            minTempTextView.setText("Min : " + String.format("%.2f", minTemp ) + " ℃");

            double maxTemp = mainJson.getDouble("temp_max") - constanteKelvin;
            maxTempTextView.setText("Max : " + String.format("%.2f",maxTemp) + " °C");

            humidityTextView.append(mainJson.getString("humidity") + "%");

            double temp = mainJson.getDouble("temp") - constanteKelvin;
            temperatureTextView.setText(String.format("%.2f",temp) + "°C");

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise")*1000,
                    json.getJSONObject("sys").getLong("sunset")*1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherIcon(int actualId, long sunrise,long sunset){
        int id = actualId/100; //Car api supporte plus de météos differentes que nous
        Drawable icon=null;

        long currentTime = new Date().getTime();
        boolean isDayLight = currentTime>=sunrise && currentTime<sunset;
        if(isDayLight)
            background.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.blueSky));
        else
            background.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.nightSky));

        if(actualId==800){
            if(isDayLight){
                icon = ContextCompat.getDrawable(getActivity(),R.drawable.soleil);
            }
            else{
                icon = ContextCompat.getDrawable(getActivity(),R.drawable.nuit);
            }
        }
        else{
            switch (id){
                case 2 :
                    icon = ContextCompat.getDrawable(getActivity(),R.drawable.orage);
                    break;
                case 3 :
                    icon = ContextCompat.getDrawable(getActivity(),R.drawable.bruine);
                    break;
               case 7 :
                   icon = ContextCompat.getDrawable(getActivity(),R.drawable.brouillard);
                   break;
                case 6 :
                    icon = ContextCompat.getDrawable(getActivity(),R.drawable.neige);
                    break;
                case 8 :
                    if(isDayLight)
                        icon = ContextCompat.getDrawable(getActivity(),R.drawable.nuageux);
                    else
                        icon = ContextCompat.getDrawable(getActivity(),R.drawable.nuageux_nuit);
                    break;
                case 5 :
                    icon = ContextCompat.getDrawable(getActivity(),R.drawable.pluie);
                    break;
            }
        }
        weatherImageView.setImageDrawable(icon);
    }

}

