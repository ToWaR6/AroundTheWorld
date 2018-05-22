package hasselhoff.aroundtheworld;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hasselhoff.aroundtheworld.Model.News;
import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNews;


public class NewsFragment extends Fragment {

    ListView viewListNews;
    List<News> listNews;
    private static  JSONObject json = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        if(json == null){
            //this.listNews = generateNews(10);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Preferences.PREFS, Context.MODE_PRIVATE);
            String currentCity = sharedPreferences.getString(Preferences.CITY,"");
            try {
                json = new RemoteFetchNews(getActivity()).execute(currentCity).get();
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        try{
            if (json == null) {
                Toast.makeText(getActivity(), "Erreur lors du chargement de l'api de news", Toast.LENGTH_SHORT).show();
            } else {
                this.listNews = getNewsFromJson(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NewsAdapter adapter = new NewsAdapter(getActivity(), this.listNews);
        this.viewListNews = rootView.findViewById(R.id.listNews);
        this.viewListNews.setAdapter(adapter);
        return rootView;
    }

    private List<News> getNewsFromJson(JSONObject json) throws JSONException {
        List<News> list = new ArrayList<News>();
        JSONArray arrayNews = json.getJSONArray("articles");
        JSONObject article;
        for (int i = 0; i < arrayNews.length(); i++) {
            article = ((JSONObject) arrayNews.get(i));
            list.add(new News(article.getString("title"), article.getString("description"), article.getString("urlToImage"), article.getString("url")));
        }
        return list;
    }

}
