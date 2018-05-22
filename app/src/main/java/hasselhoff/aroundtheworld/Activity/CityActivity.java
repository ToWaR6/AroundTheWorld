package hasselhoff.aroundtheworld.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;

public class CityActivity extends SubActivity implements GoogleApiClient.OnConnectionFailedListener {
    SharedPreferences sharedPreferences;
    private static int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getBaseContext().getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                sharedPreferences.edit().putString(Preferences.CITY, place.getName().toString()).apply();
                sharedPreferences.edit().putString(Preferences.CITYLAT, place.getLatLng().latitude + "").apply();
                sharedPreferences.edit().putString(Preferences.CITYLNG, place.getLatLng().longitude + "").apply();
            }
        }
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Erreur Connection placePicker", Toast.LENGTH_LONG).show();
    }
}
