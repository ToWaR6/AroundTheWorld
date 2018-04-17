package hasselhoff.aroundtheworld.remote_fetch;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;




public class RemoteFetchAd {
    private static final String AD_API =
            "https://ad-round-the-world.herokuapp.com/api";

    public static JSONObject getJSON(Context context, String city){
        try{
            String urlString = AD_API + "/ads/city/%s";
            city = URLEncoder.encode(city,"utf-8");
            urlString = String.format(urlString, city);
            URL url = new URL(urlString); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!= null)
                json.append(tmp).append("\n");
            reader.close();
            return new JSONObject(json.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject postJSON(Context context, String city,ArrayList<String> _ids) {
        try {
            String urlString = AD_API + "/ads/city/%s";
            city = URLEncoder.encode(city, "utf-8");
            urlString = String.format(urlString, city);
            URL url = new URL(urlString); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");

            JSONObject postData = new JSONObject();
            JSONArray idArray = new JSONArray(_ids);

            postData.put("_id",idArray);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes("" + postData.toString());
            wr.flush();
            wr.close();

            Log.i("test",postData.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            /*if(data.getInt("cod") != 200)// != Réussite
                return null;*/
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

    public static JSONObject likeAd(Context context, String _id){
        try{
            String urlString = AD_API + "/ads/like/%s";
            urlString = String.format(urlString, _id);
            URL url = new URL(urlString); //Ajout de l'id dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!= null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            /*if(data.getInt("cod") != 200)// != Réussite
                return null;*/
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
    public static JSONObject dislikeAd(Context context, String _id){
        try{
            String urlString = AD_API + "/ads/dislike/%s";
            urlString = String.format(urlString, _id);
            URL url = new URL(urlString); //Ajout de l'id dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!= null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            /*if(data.getInt("cod") != 200)// != Réussite
                return null;*/
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
