package hasselhoff.aroundtheworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class CityActivity extends SubActivity implements OnConnectionFailedListener{
    private GoogleApiClient myGoogleApiClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        myGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("city","La ville est : " + place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(null, "An error occurred: " + status);
            }
        });
    }
    public void onConnectionFailed (ConnectionResult result){
       Log.d("PROBLEM","ça marche pas là");
    }
    public void close(View view){
        CityActivity.this.finish();
    }
}
