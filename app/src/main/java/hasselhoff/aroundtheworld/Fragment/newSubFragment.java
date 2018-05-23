package hasselhoff.aroundtheworld.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hasselhoff.aroundtheworld.Adapter.AdapterLikeList;
import hasselhoff.aroundtheworld.Adapter.AdapterNetwork;
import hasselhoff.aroundtheworld.Model.Network;
import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNetwork;

import static android.content.Context.MODE_PRIVATE;


public class newSubFragment extends DialogFragment{


    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater =  getActivity().getLayoutInflater();
        final View viewInflated = inflater.inflate(R.layout.create_sub_dialog,null);
        final Handler handler = new Handler();
        final RecyclerView recyclerView = getActivity().findViewById(R.id.list);
        builder.setView(viewInflated)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = viewInflated.findViewById(R.id.subName);
                        CheckBox c = viewInflated.findViewById(R.id.checkBox);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
                        final String nom = editText.getText().toString();
                        final int prive = (c.isChecked()? 1 : 0);
                        final int id = sharedPreferences.getInt(Preferences.ID,-1);
                        final String city = sharedPreferences.getString(Preferences.CITY,"");

                        try {
                            final JSONObject postData = new JSONObject()
                                    .put("id_user",id)
                                    .put("name",nom)
                                    .put("city",city)
                                    .put("private",prive);

                            new Thread(){
                                public void run(){
                                    final JSONObject json = RemoteFetchNetwork.createNetwork(postData);
                                    if(json != null){
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Network network = new Network(1,""+prive,nom,city,json.getInt("id"),""+id);
                                                    ((AdapterNetwork) recyclerView.getAdapter()).add(network);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                    }
                                }
                            }.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newSubFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
