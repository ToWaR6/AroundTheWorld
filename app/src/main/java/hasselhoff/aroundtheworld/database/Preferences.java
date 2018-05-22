package hasselhoff.aroundtheworld.database;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Preferences {
    public static final String PREFS = "PREFS_APP";
    public static final String ID = "PREFS_ID";
    public static final String NAME = "PREFS_NAME_APP";
    public static final String FIRSTNAME = "PREFS_FIRSTNAME_APP";
    public static final String CITY = "PREFS_CITY_APP";
    public static final String BIRTHDAY= "PREFS_BIRTHDAY_APP";
    public static final String GENDER = "PREFS_GENDER_APP";
    public static final String WEIGHT = "PREFS_WEIGHT_APP";
    public static final String SIZE = "PREFS_SIZE_APP";
    public static final String LIKED_ITEMS = "PREF_LIKED_ITEMS";
    private Preferences(){

    }

    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.PREFS, Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}
