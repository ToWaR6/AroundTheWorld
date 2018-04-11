package hasselhoff.aroundtheworld;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    ListView viewListNews;
    List<News> listNews;
    String urlImage = "https://is3-ssl.mzstatic.com/image/thumb/Purple128/v4/17/05/84/17058405-29c8-1618-cc2a-86aead67ca5d/AppIconGNL-1x_U007emarketing-0-85-220-0-9.png/1200x630bb.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        this.listNews = generateNews(10);
        NewsAdapter adapter = new NewsAdapter(getActivity(), this.listNews);
        this.viewListNews = (ListView) rootView.findViewById(R.id.listNews);
        this.viewListNews.setAdapter(adapter);

        return rootView;
    }

    private void printNews() {
        List<News> list = generateNews(5);

        NewsAdapter adapter = new NewsAdapter(getActivity(), list);
        viewListNews.setAdapter(adapter);
    }

    private List<News> generateNews(int nbNews) {
        List<News> list = new ArrayList<News>();
        for (int i = 1; i <= nbNews; i++) {
            list.add(new News("Titre "+i, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus molestie lorem porta, vestibulum libero vitae, egestas mauris." , this.urlImage));
        }
        return list;
    }

}
