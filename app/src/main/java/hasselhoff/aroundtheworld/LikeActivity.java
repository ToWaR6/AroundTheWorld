package hasselhoff.aroundtheworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;

public class LikeActivity extends SubActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> likedItems = new ArrayList<>();
        for (int i= 0; i<20; i++)
            likedItems.add("Les aliments");
        likedItems.add("La pêche");
        Collections.sort(likedItems);
        recyclerView.setAdapter(new AdapterLikeList(likedItems));

    }
}
