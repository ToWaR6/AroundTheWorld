package hasselhoff.aroundtheworld.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hasselhoff.aroundtheworld.Activities.ChatActivity;
import hasselhoff.aroundtheworld.Model.Network;
import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;

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
        holder.display(networks.get(position));
    }
    public void sub(int position){
        Network network = networks.get(position);
        ArrayList<String> idNetwork = Preferences.getStringArrayPref(context,Preferences.LIKED_NETWORKS);
        idNetwork.add("" + network.getIdNetwork());
        Preferences.setStringArrayPref(context,Preferences.LIKED_NETWORKS,idNetwork);

    }

    public void unsub(int position){
        Network network = networks.get(position);
        ArrayList<String> idNetwork = Preferences.getStringArrayPref(context,Preferences.LIKED_NETWORKS);
        idNetwork.remove(""+network.getIdNetwork());
        Preferences.setStringArrayPref(context,Preferences.LIKED_NETWORKS,idNetwork);
    }
    @Override
    public int getItemCount() {
        return this.networks.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTextView;
        private ImageView sub;
        private boolean checked;
        protected MyViewHolder(final View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.item);
            sub = itemView.findViewById(R.id.add);
            checked = false;
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        sub(getAdapterPosition());
                        checked =true;
                        sub.setImageDrawable(context.getResources().getDrawable(R.mipmap.checked));
                    }else{
                        unsub(getAdapterPosition());
                        checked = false;
                        sub.setImageDrawable(context.getResources().getDrawable(R.mipmap.add_network));
                    }
                }
            });
        }
        protected void display(Network item){
            final Network network = item;
            itemTextView.setText(item.toString());
            itemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checked){
                        Intent intent = new Intent(context,ChatActivity.class);
                        intent.putExtra("ID_SERVER",network.getIdNetwork());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                }
            });
            ArrayList<String> id_networks = Preferences.getStringArrayPref(context,Preferences.LIKED_NETWORKS);
            if(id_networks.contains(""+item.getIdNetwork())){
                sub.setImageDrawable(context.getResources().getDrawable(R.mipmap.checked));
                checked=true;
            }
        }
    }
}