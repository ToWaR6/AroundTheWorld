package hasselhoff.aroundtheworld;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.Model.Network;
public class AdapterNetwork extends RecyclerView.Adapter<AdapterNetwork.MyViewHolder> {
    private ArrayList<Network> networks;
    private Context context;

    public AdapterNetwork(Context context, ArrayList<Network> networks){
        this.context = context;
        this.networks = networks;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_network, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(networks.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.networks.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTextView;
        private ImageView sub;
        protected MyViewHolder(final View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.item);
            sub = itemView.findViewById(R.id.add);
        }
        protected void display(String item){
            itemTextView.setText(item);
        }
    }
}