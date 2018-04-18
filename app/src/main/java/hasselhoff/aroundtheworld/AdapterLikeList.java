package hasselhoff.aroundtheworld;


import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterLikeList extends RecyclerView.Adapter<AdapterLikeList.MyViewHolder>{
    private ArrayList<String> likedItems;

    public AdapterLikeList(ArrayList<String> likedItems){
        this.likedItems = likedItems;
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

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTextView;
        protected MyViewHolder(View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.item);
        }
        protected void display(String item){
            itemTextView.setText(item);
        }
    }
}