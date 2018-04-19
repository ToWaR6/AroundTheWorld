package hasselhoff.aroundtheworld;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import hasselhoff.aroundtheworld.database.KeyPreferences;

public class AdapterLikeList extends RecyclerView.Adapter<AdapterLikeList.MyViewHolder> {
    private ArrayList<String> likedItems;
    private SharedPreferences sharedPreferences;
    public AdapterLikeList(Activity activity){
        this.likedItems = new ArrayList<>();
        this.sharedPreferences = activity.getBaseContext().getSharedPreferences(KeyPreferences.PREFS, Context.MODE_PRIVATE);
        Set<String> likedItems = sharedPreferences.getStringSet(KeyPreferences.LIKED_ITEMS, new TreeSet<String>());
        this.likedItems.addAll(likedItems);
        Collections.sort(this.likedItems);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_liked_items, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(likedItems.get(position));
    }

    @Override
    public int getItemCount() {
        return likedItems.size();
    }
    public void remove(int position){
        String itemRemoved = likedItems.remove(position);
        Set<String> likedItems = sharedPreferences.getStringSet(KeyPreferences.LIKED_ITEMS, new TreeSet<String>());
        likedItems.remove(itemRemoved);
        sharedPreferences.edit().clear().putStringSet(KeyPreferences.LIKED_ITEMS,likedItems).commit();
        notifyItemRemoved(position);
    }
    protected class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTextView;
        private ImageView dustbin;
        protected MyViewHolder(final View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.item);
            dustbin = itemView.findViewById(R.id.dustbin);
            dustbin.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                   remove(getAdapterPosition());
                   return true;
                }
            });
        }
        protected void display(String item){
            itemTextView.setText(item);
        }
    }
}