package hasselhoff.aroundtheworld.remote_fetch;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import hasselhoff.aroundtheworld.R;



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

    public static JSONObject postJSON(Context context, String city,Object[] _ids) {
        try {
            String urlString = AD_API + "/ads/city/%s";
            city = URLEncoder.encode(city, "utf-8");
            urlString = String.format(urlString, city);
            URL url = new URL(urlString); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            writer.write("{_id : [");
            for (int index = 0; index < _ids.length; index++) {
                writer.write((String)_ids[index]);
                if (index != _ids.length - 1)
                    writer.write(",");
            }
            writer.write("]}");
            writer.flush();
            writer.close();
            out.close();
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
            //_id = URLEncoder.encode(_id,"utf-8");
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
            //_id = URLEncoder.encode(_id,"utf-8");
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
