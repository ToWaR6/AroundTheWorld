package hasselhoff.aroundtheworld;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hasselhoff.aroundtheworld.Model.News;
import hasselhoff.aroundtheworld.remote_fetch.DownloadImageTask;

/**
 * Created by Loic on 11/04/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, List<News> listNews) {
        super(context, 0, listNews);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // si la vue doit etre cree, sinon la vue se fait recyclee
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_news, parent, false);
        }

        NewsViewHolder newsViewHolder = (NewsViewHolder) convertView.getTag();
        // si la vue doit etre cree
        if (newsViewHolder == null) {
            newsViewHolder = new NewsViewHolder();
            newsViewHolder.title = convertView.findViewById(R.id.rowNewsTitle);
            newsViewHolder.description = convertView.findViewById(R.id.rowNewsDescription);
            newsViewHolder.image = convertView.findViewById(R.id.rowNewsImage);

            convertView.setTag(newsViewHolder);
        }

        News news = getItem(position);
        if (news != null) {
            newsViewHolder.title.setText(news.getTitle());
            newsViewHolder.description.setText(news.getDescription());
            new DownloadImageTask(newsViewHolder.image).execute(news.getUrlImage());

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getItem(position).getUrl())));
            }
        });


        return convertView;
    }

    private class NewsViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
    }
}
