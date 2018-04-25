package hasselhoff.aroundtheworld;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.database.Preferences;

public class LikeActivity extends SubActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ArrayList<String> likedItems = Preferences.getStringArrayPref(LikeActivity.this,Preferences.LIKED_ITEMS);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(LikeActivity.this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(LikeActivity.this, R.drawable.divider));
        recyclerView.addItemDecoration(myDivider );
        recyclerView.setAdapter(new AdapterLikeList(this,likedItems));
    }

    public void addALikedItem(View view){
        DialogFragment newFragment = new NewLikedItemDialogFragment();
        newFragment.show(getFragmentManager(),"New item");
    }
}
