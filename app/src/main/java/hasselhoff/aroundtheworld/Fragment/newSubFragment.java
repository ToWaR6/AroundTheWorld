package hasselhoff.aroundtheworld.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import hasselhoff.aroundtheworld.R;
import hasselhoff.aroundtheworld.database.Preferences;
import hasselhoff.aroundtheworld.remote_fetch.RemoteFetchNetwork;

import static android.content.Context.MODE_PRIVATE;


public class newSubFragment extends DialogFragment{


    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater =  getActivity().getLayoutInflater();
        final View viewInflated = inflater.inflate(R.layout.create_sub_dialog,null);
        builder.setView(viewInflated)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = viewInflated.findViewById(R.id.subName);
                        CheckBox c = viewInflated.findViewById(R.id.checkBox);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Preferences.PREFS,MODE_PRIVATE);
                        String nom = editText.getText().toString();
                        int prive = (c.isChecked()? 1 : 0);
                        int id = sharedPreferences.getInt(Preferences.ID,-1);
                        String city = sharedPreferences.getString(Preferences.CITY,"");

                        try {
                            final JSONObject postData = new JSONObject()
                                    .put("id_user",id)
                                    .put("name",nom)
                                    .put("city",city)
                                    .put("private",prive);
                            new Thread(){
                                public void run(){
                                    RemoteFetchNetwork.createNetwork(postData);
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
