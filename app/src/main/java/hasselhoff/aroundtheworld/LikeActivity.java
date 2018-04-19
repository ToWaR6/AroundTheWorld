package hasselhoff.aroundtheworld;


import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import hasselhoff.aroundtheworld.database.KeyPreferences;

public class LikeActivity extends SubActivity {

    private RecyclerView recyclerView;
    private  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(LikeActivity.this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(LikeActivity.this, R.drawable.divider));
        recyclerView.addItemDecoration(myDivider );
        recyclerView.setAdapter(new AdapterLikeList(LikeActivity.this));
    }

    public void addALikedItem(View view){
        DialogFragment newFragment = new NewLikedItemDialogFragment();
        newFragment.show(getFragmentManager(),"New item");
    }
}
