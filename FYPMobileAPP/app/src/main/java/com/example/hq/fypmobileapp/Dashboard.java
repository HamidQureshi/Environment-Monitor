package com.example.hq.fypmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Set;

import static com.example.hq.fypmobileapp.ChartActivity.SOUND;

public class Dashboard extends AppCompatActivity {

    DatabaseReference myRef;
    DatabaseReference myRefLights;

    // Map<String,Object> dataset ;
  //  Set<String> keys;

    TextView temperature_TV;
    TextView co2_TV;
    TextView humidity_TV;
    TextView pressure_TV;
    TextView ambient_TV;
    TextView uvindex_TV;
    TextView sound_TV;
    TextView voc_TV;

    Button lightbtn;


    private ChildEventListener mListener;
    ValueEventListener mListenerLights;

    Boolean lightsStatus = false;
    String status = "OFF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();

        initDB();
        readDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Dashboard Activity","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Dashboard Activity","onDestroy");

        myRef.removeEventListener(mListener);
        myRefLights.removeEventListener(mListenerLights);

    }

    private void initViews() {

        temperature_TV = findViewById(R.id.temperature_TV);
        co2_TV = findViewById(R.id.co2_TV);
        humidity_TV = findViewById(R.id.humidity_TV);
        pressure_TV = findViewById(R.id.pressure_TV);
        ambient_TV = findViewById(R.id.ambient_TV);
        uvindex_TV = findViewById(R.id.uvindex_TV);
        voc_TV = findViewById(R.id.voc_TV);
        sound_TV = findViewById(R.id.sound_TV);

        lightbtn = (Button) findViewById(R.id.lightbtn);

    }

    public void initDB(){

        //firebase database
        myRef = FirebaseDatabase.getInstance().getReference("environment");
        myRefLights = FirebaseDatabase.getInstance().getReference("Lights");

    }

    public void readDB(){

        mListenerLights = myRefLights.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);

                if (value.equalsIgnoreCase("off")) {
                    lightsStatus = false;
                    lightbtn.setText("Turn Lights ON");

                } else {
                    lightsStatus = true;
                    lightbtn.setText("Turn Lights OFF");

                }

                Log.d("lights", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("lights", "Failed to read value.", error.toException());
            }
        });


       mListener =  myRef.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                Log.e("Dashboard","on Data Changed");

                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();


                 Map<String,Object> dataset ;
                  Set<String> keys;

                keys = (Set<String>) value.keySet();
                dataset = value;


                createDatasetForChart(keys, dataset);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }


    public synchronized void createDatasetForChart(Set<String> keys, Map<String, Object> dataset) {

        String[] stringKeys = keys.toArray(new String[0]);

        Log.e("stringKeys",stringKeys.toString());


        if (dataset == null) {
            return;
        }


            String temp = "30.0";
            String co2 = "400.0";
            String ambientLight = "0.0";
            String humidity = "40.0";
            String pressure = "116.0";
            String sound = "50.0";
            String uvIndex = "0.0";
            String voc = "0.0";


            if (dataset.containsKey("temperature")) {
                temp = "" + dataset.get("temperature");
            }
            if (dataset.containsKey("co2")) {
                co2 = "" + dataset.get("co2");
            }
            if (dataset.containsKey("ambientLight")) {
                ambientLight = "" + dataset.get("ambientLight");
            }
            if (dataset.containsKey("humidity")) {
                humidity = "" + dataset.get("humidity");
            }
            if (dataset.containsKey("pressure")) {
                pressure = "" + dataset.get("pressure");
            }
            if (dataset.containsKey("sound")) {
                sound = "" + dataset.get("sound");
            }
            if (dataset.containsKey("uvIndex")) {
                uvIndex = "" + dataset.get("uvIndex");
            }
            if (dataset.containsKey("voc")) {
                voc = "" + dataset.get("voc");
            }

//            Log.e("the temp",temp);
//            Log.e("the co2",co2);
//            Log.e("the ambientLight",ambientLight);
//            Log.e("the humidity",humidity);
//            Log.e("the pressure",pressure);
//            Log.e("the uvIndex",uvIndex);
//            Log.e("the sound",sound);
//            Log.e("the voc",voc);

            Float float_temp = Float.parseFloat(temp);
            Float float_co2 = Float.parseFloat(co2);
            Float float_ambientLight = Float.parseFloat(ambientLight);
            Float float_humidity = Float.parseFloat(humidity);
            Float float_pressure = Float.parseFloat(pressure);
            Float float_uvIndex = Float.parseFloat(uvIndex);
            Float float_sound = Float.parseFloat(sound);
            Float float_voc = Float.parseFloat(voc);

            if(float_ambientLight<0){
                ambientLight = "0.0 ";
            }

            temp = String.format("%.2f", float_temp);
             pressure = String.format("%.2f", float_pressure);
        sound = String.format("%.2f", float_sound);


        if( temperature_TV != null && co2_TV != null && ambient_TV != null && humidity_TV != null && pressure_TV != null && uvindex_TV != null && sound_TV != null && voc_TV != null  ) {

            temperature_TV.setText(temp + " °C");
            co2_TV.setText(co2 + " ppm");
            ambient_TV.setText(ambientLight + "lx");
            humidity_TV.setText(humidity + " %");
            pressure_TV.setText(pressure + " mbar");
            uvindex_TV.setText(uvIndex + "");
            sound_TV.setText(sound + " db");
            voc_TV.setText(voc + " ppb");
        }
        else{
            readDB();
        }



    }



    public void startActivityTemperature(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.TEMP);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void startActivityCo2(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.CO2);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityHumidity(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.HUMIDITY);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityPressure(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.PRESSURE);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityAmbientLight(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.AMBIENTLIGHT);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityUVIndex(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.UVINDEX);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityVOC(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, ChartActivity.VOC);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivitySound(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChartActivity.KEY, SOUND);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deleteData(View view) {

Log.e("Dashboard", "Data Deleted");
        myRef.removeValue();
        Toast.makeText(this,"Database Cleared",Toast.LENGTH_SHORT).show();

    }

    public void turnlights(View view) {


        if(lightsStatus){
            status = "ON";
            myRefLights.setValue("Off");
        }else{
            status = "OFF";
            myRefLights.setValue("On");
        }


        lightbtn.setText("Turn Lights "+status);

        lightsStatus = !lightsStatus;


    }
}
