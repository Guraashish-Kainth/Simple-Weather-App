package com.example.simpleweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Settings_View extends AppCompatActivity {

    ImageButton back;
    Button metric;
    Button imperial;
    Button changeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_view);

        back = findViewById(R.id.Back_Button);
        metric = findViewById(R.id.Metric);
        imperial = findViewById(R.id.Imperial);
        changeLocation = findViewById(R.id.changeLocation);

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        metric.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Service.changeUnitsManager("metric");
                        Toast.makeText(getApplicationContext(), "Units changed to Metric", Toast.LENGTH_SHORT).show();
                    }
                });

        imperial.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Service.changeUnitsManager("imperial");
                        Toast.makeText(getApplicationContext(), "Units changed to Imperial", Toast.LENGTH_SHORT).show();
                    }
                });

        changeLocation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView input = findViewById(R.id.LocationInput);
                        String newLocation = input.getText().toString();
                        if(Service.changeLocationManager(newLocation, getApplicationContext())) {
                            Toast.makeText(getApplicationContext(), "Updated Location: " + newLocation, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Invalid Location, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}