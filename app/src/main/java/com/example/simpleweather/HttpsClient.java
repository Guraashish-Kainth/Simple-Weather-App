package com.example.simpleweather;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

public class HttpsClient {
    private static HttpsURLConnection connection;
    private static JSONObject data;
    private static boolean validLocation;

    public static void dataRetriever(Context applicationContext, CountDownLatch latch) {
        //New thread for API call, cannot be done on main thread in Android Studio
        Thread sendHttpRequestThread = new Thread() {
            @Override
            public void run() {

                BufferedReader reader;
                String line;
                String input = "";


                try {
                    String fullURL = Util.getProperty("Base_URL", applicationContext) + Util.getLocation() +
                            Util.getProperty("Units_Declaration", applicationContext) + Util.getUnits() +
                            Util.getProperty("ID_Request", applicationContext) + BuildConfig.API_KEY;

                    URL url = new URL(fullURL);


                    connection = (HttpsURLConnection) url.openConnection();


                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);


                    int status = connection.getResponseCode();


                    if (status > 299) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }
                    while ((line = reader.readLine()) != null) {
                        input += line;
                    }
                    reader.close();
                } catch (MalformedURLException e) {
                    Log.i("Error", "Bad URL\n");
                    e.printStackTrace();
                } catch (IOException i) {
                    Log.i("Error", "IO Exception 2\n");
                    i.printStackTrace();
                } finally {
                    connection.disconnect();
                }
                try {
                    data = new JSONObject(input);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        };
        sendHttpRequestThread.start();
    }

    //Way to update data on a different page without new API call
    public static JSONObject getData(){
        return data;
    }

    public static void checkValidLocation(String newLocation, Context applicationContext, CountDownLatch latch){
        //New thread for API call, cannot be done on main thread in Android Studio
        Thread checkValidLocationThread = new Thread() {
            @Override
            public void run() {
                try {
                    String fullURL = Util.getProperty("Base_URL", applicationContext) + newLocation +
                            Util.getProperty("Units_Declaration", applicationContext) + Util.getProperty("Units", applicationContext) +
                            Util.getProperty("ID_Request", applicationContext) + BuildConfig.API_KEY;

                    URL url = new URL(fullURL);


                    connection = (HttpsURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);


                    int status = connection.getResponseCode();


                    if (status > 299) {
                        validLocation = false;
                    } else {
                        validLocation = true;
                    }
                }
                catch (MalformedURLException e) {
                    Log.i("Error", "Bad URL\n");
                    e.printStackTrace();
                }
                catch (IOException i) {
                    Log.i("Error", "IO Exception 2\n");
                    i.printStackTrace();
                }
                finally {
                    connection.disconnect();
                }
                latch.countDown();
            }
        };
        checkValidLocationThread.start();
    }

    public static Boolean getBoolVarOfLocation(){
        return validLocation;
    }
}
