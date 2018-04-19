package hasselhoff.aroundtheworld;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import hasselhoff.aroundtheworld.database.Preferences;

public class CityActivity extends SubActivity implements OnConnectionFailedListener{
    SharedPreferences sharedPreferences;
    private String newCity = "";
    private String currentCity;
    private  PlaceAutocompleteFragment autocompleteFragment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        sharedPreferences = getBaseContext().getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
        currentCity = sharedPreferences.getString(Preferences.CITY,"");

        //TextView
        ((TextView) findViewById(R.id.text_you_are)).append(currentCity);

        //Autocomplete
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).setCountry("FR").build();

        new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setFilter(autocompleteFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                newCity = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                newCity = "";
            }
        });
    }
    public void onConnectionFailed (@NonNull ConnectionResult result){
       Log.d("PROBLEM","Error : please contact us");
    }
    public void close(View view){
        CityActivity.this.finish();
    }
    public void validate(View view){
        autocompleteFragment.setText("");
        if(!newCity.equals("")){
            currentCity = newCity;
            sharedPreferences.edit().putString(Preferences.CITY,currentCity).apply();
            ((TextView) findViewById(R.id.text_you_are)).setText(R.string.you_are);
            ((TextView) findViewById(R.id.text_you_are)).append(currentCity);
        }
    }
    public void onDestroy(){
        super.onDestroy();
    }
}
