package com.example.simpleweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
    protected static SharedPreferences myPrefSettings;
    protected static SharedPreferences.Editor settingsEditor;

    public static String getProperty(String key,Context context) {
        Properties properties = new Properties();

        try {

            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
        }
        catch (IOException i) {
            Log.i("Error","IO Exception 1\n");
            i.printStackTrace();
        }
        finally {
            return properties.getProperty(key);
        }
    }

    public static String getLocation(){
        return myPrefSettings.getString(String.valueOf((R.string.Location)), "Orlando");
    }

    public static void setLocation(String newLocation){
        settingsEditor.putString(String.valueOf(R.string.Location), newLocation);
        settingsEditor.commit();
    }

    public static String getUnits(){
        return myPrefSettings.getString(String.valueOf((R.string.Units)), "imperial");
    }

    public static void setUnits(String newUnits){
        settingsEditor.putString(String.valueOf(R.string.Units), newUnits);
        settingsEditor.commit();
    }

    public static void onCreateSharedPreferences(Context applicationContext) {
        myPrefSettings = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        settingsEditor = myPrefSettings.edit();
    }

}
