package hasselhoff.aroundtheworld.remote_fetch;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import hasselhoff.aroundtheworld.R;

public class RemoteFetchNews extends AsyncTask<String, Void, JSONObject> {
    private static final String OPEN_NEWS_MAP_API =
            "https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt";
    private Context context;

    public RemoteFetchNews(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try{
            URL url = new URL(String.format(OPEN_NEWS_MAP_API, params[0])); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", this.context.getString(R.string.newsAPIKey)); //Ajout la clef de l'api contenu dans les ressources
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!= null) {
                json.append(tmp).append("\n");
            }
            connection.disconnect();
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getString("status").equals("ok")) {
                return data;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}

