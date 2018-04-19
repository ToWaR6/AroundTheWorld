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

import hasselhoff.aroundtheworld.database.Preferences;

public class AdapterLikeList extends RecyclerView.Adapter<AdapterLikeList.MyViewHolder> {
    private ArrayList<String> likedItems;
    private Context context;
    public AdapterLikeList(Context context,ArrayList<String> likedItems){
        this.context = context;
        this.likedItems = likedItems;
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
        this.likedItems.remove(position);
        Preferences.setStringArrayPref(context,Preferences.LIKED_ITEMS,this.likedItems);
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