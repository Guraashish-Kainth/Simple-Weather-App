package com.example.simpleweather;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    ImageButton refresh;
    TextView curTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView flTemp;
    TextView curLocation;
    TextView description;
    Button settings;
    ImageView background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Service.internetAccessCheck(getApplicationContext())){

            Toast.makeText(getApplicationContext(), "Internet Status: Connected", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Internet Status: Not Connected", Toast.LENGTH_SHORT).show();
        }

        Service.initialSetup(getApplicationContext());

        setContentView(R.layout.activity_main);
        curTemp = findViewById(R.id.Current_Temperature);
        curLocation = findViewById(R.id.Current_Location);
        minTemp = findViewById(R.id.Min_Temp);
        maxTemp = findViewById(R.id.Max_Temp);
        flTemp = findViewById(R.id.FL_Temp);
        refresh = findViewById(R.id.Refresh);
        settings = findViewById(R.id.Settings);
        background = findViewById(R.id.Background);
        description = findViewById(R.id.Description);




        refresh.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weather output = Service.getData(getApplicationContext());
                curTemp.setText((int) output.curTemp + "°");
                curLocation.setText(Util.getLocation());
                description.setText(output.getDescription());
                minTemp.setText((int) output.minTemp + "°");
                maxTemp.setText((int) output.maxTemp + "°");
                flTemp.setText((int) output.feelsLike + "°");
                Service.setBackground(background, output.getDescription(), output.getIcon());
            }
        });

        settings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Settings_View.class);
                        startActivity(intent);
                    }
        });
    }

}