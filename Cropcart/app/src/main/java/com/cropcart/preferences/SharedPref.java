package com.cropcart.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.cropcart.R;

/**
 * Created by BHUSRI on 10/6/2017.
 */


public class SharedPref {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean issignedin;
    private String userid;
    private boolean isfarmer;

    public void setIssignedin(boolean issignedin) {
        editor.putBoolean("issignedin", issignedin);
        editor.apply();
    }

    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean issignedin() {
        return preferences.getBoolean("issignedin", false);
    }

    public void setUserid(String userid) {
        editor.putString("userid", userid);
        editor.apply();
    }

    public String getUserid() {
        return preferences.getString("userid", "0");
    }


    public boolean isfarmer() {
        return preferences.getBoolean("isfarmer", true);
    }

    public void setIsfarmer(boolean isfarmer) {
        editor.putBoolean("isfarmer", isfarmer);
        editor.apply();
    }
}

