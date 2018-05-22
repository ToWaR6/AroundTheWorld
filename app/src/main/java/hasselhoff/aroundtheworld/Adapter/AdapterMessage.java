package hasselhoff.aroundtheworld.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import hasselhoff.aroundtheworld.Model.Message;
import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.MyViewHolder> {
    private ArrayList<Message> messages;
    private Context context;
    public AdapterMessage(Context context, ArrayList<Message> messages){
        this.context = context;
        this.messages = messages;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_message, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(messages.get(position));
    }

    @Override
    public int getItemCount() {
            return this.messages.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTextView;
        protected MyViewHolder(final View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.item);
        }
        protected void display(Message item){
            itemTextView.setText(item.toString());
        }
    }
}