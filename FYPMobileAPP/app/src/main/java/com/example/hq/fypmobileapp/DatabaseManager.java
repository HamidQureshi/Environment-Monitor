package com.example.hq.fypmobileapp;

import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class DatabaseManager {

    static DatabaseManager instance;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String TAG = "Mobile APP";

    Set<String> keys;
    Map<String,Object> dataset ;




    public List<Entry> entriesTemp ;
    public List<Entry> entriesCo2 ;
    public List<Entry> entriesAmbientLight ;
    public List<Entry> entriesHumidity ;
    public List<Entry> entriesPressure ;
    public List<Entry> entriesSound ;
    public List<Entry> entriesUVIndex ;
    public List<Entry> entriesVOC ;


    public static synchronized DatabaseManager getInstance(){

        if(instance == null){
            instance = new DatabaseManager();
        }
            return instance;
    }


    public void initDB(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference("environment");

    }

    public void writeDB(){

        myRef.setValue("Hello, World!");
    }

    public void readDB(){

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();

                keys = (Set<String>) value.keySet();
                dataset = value;

                createDatasetForChart();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }




    public void createDatasetForChart() {

        entriesTemp = new ArrayList<Entry>();
        entriesCo2 = new ArrayList<Entry>();
        entriesAmbientLight = new ArrayList<Entry>();
        entriesHumidity = new ArrayList<Entry>();
        entriesPressure = new ArrayList<Entry>();
        entriesSound = new ArrayList<Entry>();
        entriesUVIndex = new ArrayList<Entry>();
        entriesVOC = new ArrayList<Entry>();



        if (dataset == null) {
            return;
        }

        String[] stringKeys = keys.toArray(new String[0]);
        int k = 0;
        for (int i = 0; i < dataset.size(); i++) {
            String key = stringKeys[i];

            Map<String, Object> data = (Map<String, Object>) dataset.get(key);

            String temp = "30.0";
            String co2 = "400.0";
            String ambientLight = "0.0";
            String humidity = "40.0";
            String pressure = "116.0";
            String sound = "50.0";
            String uvIndex = "0.0";
            String voc = "0.0";


            if (data.containsKey("temperature")) {
                temp = "" + data.get("temperature");
            }
            if (data.containsKey("co2")) {
                co2 = "" + data.get("co2");
            }
            if (data.containsKey("ambientLight")) {
                ambientLight = "" + data.get("ambientLight");
            }
            if (data.containsKey("humidity")) {
                humidity = "" + data.get("humidity");
            }
            if (data.containsKey("pressure")) {
                pressure = "" + data.get("pressure");
            }
            if (data.containsKey("sound")) {
                sound = "" + data.get("sound");
            }
            if (data.containsKey("uvIndex")) {
                uvIndex = "" + data.get("uvIndex");
            }
            if (data.containsKey("voc")) {
                voc = "" + data.get("voc");
            }


            Log.e("key = ", key);
            Log.e("temp = ", temp);

            if (!key.equalsIgnoreCase("null")) {
                if (!temp.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(temp);
                    entriesTemp.add(new Entry(k, y));
                }
                if (!co2.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(co2);
                    entriesCo2.add(new Entry(k, y));
                }
                if (!ambientLight.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(ambientLight);
                    entriesAmbientLight.add(new Entry(k, y));
                }
                if (!humidity.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(humidity);
                    entriesHumidity.add(new Entry(k, y));
                }
                if (!pressure.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(pressure);
                    entriesPressure.add(new Entry(k, y));
                }
                if (!sound.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(sound);
                    entriesSound.add(new Entry(k, y));
                }
                if (!uvIndex.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(uvIndex);
                    entriesUVIndex.add(new Entry(k, y));
                }
                if (!voc.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(voc);
                    entriesVOC.add(new Entry(k, y));
                }
                k = k + 2;
            }

        }






    }


}
