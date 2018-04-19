package hasselhoff.aroundtheworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Set;
import java.util.TreeSet;

import hasselhoff.aroundtheworld.database.KeyPreferences;


public class NewLikedItemDialogFragment  extends DialogFragment{
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater =  getActivity().getLayoutInflater();
        final View viewInflated = inflater.inflate(R.layout.liked_item_dialog,null);
        builder.setView(viewInflated)
            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   SharedPreferences sharedPreferences =  getActivity().getBaseContext().getSharedPreferences(KeyPreferences.PREFS, Context.MODE_PRIVATE);
                   SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                   prefsEditor.clear();//Rien ne fonctionne sans clear avant Oo
                   Set<String> set = sharedPreferences.getStringSet(KeyPreferences.LIKED_ITEMS,new TreeSet<String>());
                   EditText editText = viewInflated.findViewById(R.id.itemEdit);
                   String newItem = editText.getText().toString().trim();
                   set.add(newItem);
                   prefsEditor.putStringSet(KeyPreferences.LIKED_ITEMS,set).apply();
                   RecyclerView recyclerView = getActivity().findViewById(R.id.list);
                   recyclerView.setAdapter(new AdapterLikeList(getActivity()));
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NewLikedItemDialogFragment.this.getDialog().cancel();
                }
            });
        return builder.create();
    }
}
