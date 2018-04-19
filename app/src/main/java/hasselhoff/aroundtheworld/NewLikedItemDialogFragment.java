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

import java.util.ArrayList;

import hasselhoff.aroundtheworld.database.Preferences;


public class NewLikedItemDialogFragment  extends DialogFragment{
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater =  getActivity().getLayoutInflater();
        final View viewInflated = inflater.inflate(R.layout.liked_item_dialog,null);
        builder.setView(viewInflated)
            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   EditText editText = viewInflated.findViewById(R.id.itemEdit);
                   String newItem = editText.getText().toString().trim();
                   ArrayList<String> likedItems = Preferences.getStringArrayPref(getActivity(),Preferences.LIKED_ITEMS);
                   likedItems.add(newItem);
                   Preferences.setStringArrayPref(getActivity(),Preferences.LIKED_ITEMS,likedItems);
                   RecyclerView recyclerView = getActivity().findViewById(R.id.list);
                   recyclerView.setAdapter(new AdapterLikeList(getActivity(),likedItems));
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
