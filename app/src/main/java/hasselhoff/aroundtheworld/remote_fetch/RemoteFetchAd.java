package hasselhoff.aroundtheworld.remote_fetch;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import hasselhoff.aroundtheworld.R;

/**
 * Created by Marvin on 15/04/2018.
 */

public class RemoteFetchAd {
    private static final String AD_API =
            "https://ad-round-the-world.herokuapp.com/api";

    public static JSONObject getJSON(Context context, String city){
        try{
            String urlString = AD_API + "/ads/city/%s";
            URL url = new URL(String.format(urlString, city)); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!= null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("cod") != 200)// != Réussite
                return null;
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
