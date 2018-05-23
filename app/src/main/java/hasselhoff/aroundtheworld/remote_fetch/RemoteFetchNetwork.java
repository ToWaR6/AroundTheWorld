package hasselhoff.aroundtheworld.remote_fetch;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Exemple de https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587
public class RemoteFetchNetwork {
    private static final String AROUND_THE_NETWORK_API =
            "https://around-the-network.herokuapp.com/%s";

    public static JSONObject createUser(JSONObject postData){
        try{
            URL url = new URL(String.format(AROUND_THE_NETWORK_API, "create_user.php")); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write("" + postData.toString());
            writer.flush();
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("success") == 0)// != Réussite
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

    public static JSONObject updateUser(JSONObject postData){
        try{
            URL url = new URL(String.format(AROUND_THE_NETWORK_API, "update_user.php")); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write("" + postData.toString());
            writer.flush();
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("success") == 0)// != Réussite
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

    public static JSONObject postMessage(JSONObject postData){
        try{
            URL url = new URL(String.format(AROUND_THE_NETWORK_API, "post_message.php")); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write("" + postData.toString());
            writer.flush();
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("success") == 0)// != Réussite
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

    public static JSONObject createNetwork(JSONObject postData){
        try{
            URL url = new URL(String.format(AROUND_THE_NETWORK_API, "create_network.php")); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write("" + postData.toString());
            writer.flush();
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("success") == 0)// != Réussite
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

    public static JSONObject getNetwork(){
        try{
            URL url = new URL(String.format(AROUND_THE_NETWORK_API, "get_networks.php")); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("success") == 0)// != Réussite
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

    public static JSONObject getMessages(JSONObject postData){
        try{
            URL url = new URL(String.format(AROUND_THE_NETWORK_API, "get_messages.php")); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes("" + postData.toString());
            wr.flush();
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");

            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("success") == 0)// != Réussite
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
