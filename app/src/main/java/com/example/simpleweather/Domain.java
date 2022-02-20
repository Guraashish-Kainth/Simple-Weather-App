package com.example.simpleweather;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class Domain {


    public static Weather dataParser(JSONObject data) throws JSONException {
        Weather displayData;
        displayData = new Weather();
        JSONArray weather = data.getJSONArray("weather");
        JSONObject displayDescription = weather.getJSONObject(0);
        JSONObject main = data.getJSONObject("main");
        displayData.setCurTemp(main.getDouble("temp"));
        displayData.setMinTemp(main.getDouble("temp_min"));
        displayData.setMaxTemp(main.getDouble("temp_max"));
        displayData.setFeelsLike(main.getDouble("feels_like"));
        displayData.setDescription(displayDescription.getString("description"));
        displayData.setIcon((displayDescription.getString("icon")));

        return displayData;
    }

    public static boolean hasInternet(Context applicationContext) {

        ConnectivityManager manager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void changeUnits(String newUnits){
        Util.setUnits(newUnits);
    }

    public static void changeLocation(String location){
        Util.setLocation(location);
    }

    public static void setBackground(ImageView background, String description, String icon) {
        switch(description){
            case "clear sky":
                //Checking if night or day
                if(icon.charAt(2) == 'd') {
                    background.setImageResource(R.drawable.clear_sky);
                }
                else{
                    background.setImageResource(R.drawable.clear_sky_night);
                }
                break;

            case "few clouds":
            case "scattered clouds":
            case "broken clouds":
                if(icon.charAt(2) == 'd') {
                    background.setImageResource(R.drawable.cloudy);
                }
                else{
                    background.setImageResource(R.drawable.cloud_night);
                }
                break;

            case "shower rain":
                if(icon.charAt(2) == 'd') {
                    background.setImageResource(R.drawable.light_rain);
                }
                else{
                    background.setImageResource(R.drawable.light_rain_night);
                }
                break;

            case "rain":
                background.setImageResource((R.drawable.rain));
                break;

            case "thunderstorm":
                background.setImageResource((R.drawable.thunderstorm));
                break;

            case "snow":
                if(icon.charAt(2) == 'd') {
                    background.setImageResource(R.drawable.snow);
                }
                else{
                    background.setImageResource(R.drawable.snow_night);
                }
                break;

            case "mist":
                if(icon.charAt(2) == 'd') {
                    background.setImageResource(R.drawable.mist);
                }
                else{
                    background.setImageResource(R.drawable.mist_night);
                }
                break;
        }
    }
}
