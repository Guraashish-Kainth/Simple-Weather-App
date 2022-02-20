package com.example.simpleweather;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service {

    public static Weather getData(Context applicationContext){
        Weather displayData = new Weather();

        //Used to ensure that the main thread does not call for the data
        //Before the API call has gone through
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        try {
            HttpsClient.dataRetriever(applicationContext, latch);

            //Waiting for the API call thread's completion
            executor.shutdown();
            try {
                latch.await();
            } catch (InterruptedException ex) {
                Log.i("Error", "Interrupted Exception\n");
            }
            JSONObject inputData = HttpsClient.getData();

            displayData = Domain.dataParser(inputData);

        } catch (JSONException j){
            Log.i("Error", "JSON Exception\n");
            j.printStackTrace();
        }
        finally {
            return displayData;
        }
    }

    public static boolean internetAccessCheck(Context applicationContext){
        return Domain.hasInternet(applicationContext);
    }

    public static void changeUnitsManager(String newUnits){
        Domain.changeUnits(newUnits);
    }

    public static boolean changeLocationManager(String location, Context applicationContext){
        //Used to ensure that the main thread does not call for the data
        //Before the API call has gone through
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(1);

        HttpsClient.checkValidLocation(location, applicationContext, latch);

        //Waiting for the API call thread's completion
        executor.shutdown();
        try {
            latch.await();
        }catch(InterruptedException i){
            Log.i("Error", "Interrupted Exception\n");
        }

        if(!HttpsClient.getBoolVarOfLocation()){
            return false;
        }
        else{
            Domain.changeLocation(location);
            return true;
        }
    }

    public static void initialSetup(Context applicationContext) {
        Util.onCreateSharedPreferences(applicationContext);
    }

    public static void setBackground(ImageView background, String description, String icon){
        Domain.setBackground(background, description, icon);
    }
}
