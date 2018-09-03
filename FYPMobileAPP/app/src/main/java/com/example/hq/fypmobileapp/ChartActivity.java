package com.example.hq.fypmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChartActivity extends AppCompatActivity {

    DatabaseReference myRef;
    String TAG = "Mobile APP";

    LineChart chart ;


    public static final String KEY = "type";
    public static final String TEMP = "Temperature";
    public static final String CO2 = "CO2";
    public static final String HUMIDITY = "Humidity";
    public static final String PRESSURE = "Pressure";
    public static final String AMBIENTLIGHT = "Ambient Light";
    public static final String UVINDEX = "UV Index";
    public static final String VOC = "VOC";
    public static final String SOUND = "Sound";

    String type = null;

    ValueEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey(ChartActivity.KEY)){
                type = bundle.getString(ChartActivity.KEY);
                setTitle(type);

            }
        }

        Log.e("-----> Key is ",type);
//        initDB();
//        readDB();
        initViews();


    }

    public void initViews(){
        chart = (LineChart) findViewById(R.id.chart);
    }

    public void initDB(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference("environment");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("Main Activity","onResume");
        initDB();
        readDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Main Activity","onPause");

        myRef.removeEventListener(mListener);
    }

    public void writeDB(){

        myRef.setValue("Hello, World!");
    }

    public synchronized void createDatasetForChart(Set<String> keys, Map<String,Object> dataset ){

        //MpAndroidChart

        List<Entry> entriesTemp = new ArrayList<Entry>();
        List<Entry> entriesCo2 = new ArrayList<Entry>();
        List<Entry> entriesAmbientLight = new ArrayList<Entry>();
        List<Entry> entriesHumidity = new ArrayList<Entry>();
        List<Entry> entriesPressure = new ArrayList<Entry>();
        List<Entry> entriesSound = new ArrayList<Entry>();
        List<Entry> entriesUVIndex = new ArrayList<Entry>();
        List<Entry> entriesVOC = new ArrayList<Entry>();


        if(dataset == null){
            return;
        }

        String[] stringKeys = keys.toArray(new String[0]);
        Arrays.sort(stringKeys);

        int k=0;
        for(int i=0; i<dataset.size() ; i++){
            String key = stringKeys[i];

            Map<String,Object> data = (Map<String,Object>) dataset.get(key);

            String temp = "30.0";
            String co2 = "400.0";
            String ambientLight = "0.0";
            String humidity = "40.0";
            String pressure = "116.0";
            String sound = "50.0";
            String uvIndex = "0.0";
            String voc = "0.0";


            if(data.containsKey("temperature")){
                temp = ""+ data.get("temperature");
            }
            if(data.containsKey("co2")){
                co2 = ""+ data.get("co2");
            }
            if(data.containsKey("ambientLight")){
                ambientLight = ""+ data.get("ambientLight");
            }
            if(data.containsKey("humidity")){
                humidity = ""+ data.get("humidity");
            }
            if(data.containsKey("pressure")){
                pressure = ""+ data.get("pressure");
            }
            if(data.containsKey("sound")){
                sound = ""+ data.get("sound");
            }
            if(data.containsKey("uvIndex")){
                uvIndex = ""+ data.get("uvIndex");
            }
            if(data.containsKey("voc")){
                voc = ""+ data.get("voc");
            }


//            Log.e("key = ",key);
//            Log.e("temp = ",temp);

            if(!key.equalsIgnoreCase("null")) {
                if (!temp.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(temp);
                    entriesTemp.add(new Entry(k, y));
                }
                if (!co2.equalsIgnoreCase("null")) {
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(co2);
                    entriesCo2.add(new Entry(k, y));
                }
                if (!ambientLight.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(ambientLight);

                    if(y<0){
                        y = Float.parseFloat("0.0") ;
                    }

                    entriesAmbientLight.add(new Entry(k, y));
                }
                if (!humidity.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(humidity);
                    entriesHumidity.add(new Entry(k, y));
                }
                if (!pressure.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(pressure);
                    entriesPressure.add(new Entry(k, y));
                }
                if (!sound.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(sound);
                    entriesSound.add(new Entry(k, y));
                }
                if (!uvIndex.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(uvIndex);
                    entriesUVIndex.add(new Entry(k, y));
                }
                if (!voc.equalsIgnoreCase("null")){
                    Float x = Float.parseFloat(key);
                    Float y = Float.parseFloat(voc);
                    entriesVOC.add(new Entry(k, y));
                }
                k = k + 2;
            }



        }

        LineDataSet dataSet;
        if(type.equalsIgnoreCase(TEMP)) {
            dataSet = new LineDataSet(entriesTemp, "Temperature");
        }
        else if(type.equalsIgnoreCase(CO2)) {
            dataSet = new LineDataSet(entriesCo2, "CO2");
        }
        else if(type.equalsIgnoreCase(AMBIENTLIGHT)) {
            dataSet = new LineDataSet(entriesAmbientLight, "Ambient Light");
        }
        else if(type.equalsIgnoreCase(HUMIDITY)) {
            dataSet = new LineDataSet(entriesHumidity, "Humidity");
        }
        else if(type.equalsIgnoreCase(PRESSURE)) {
            dataSet = new LineDataSet(entriesPressure, "Pressure");
        }
        else if(type.equalsIgnoreCase(SOUND)) {
            dataSet = new LineDataSet(entriesSound, "Sound");
        }
        else if(type.equalsIgnoreCase(UVINDEX)) {
            dataSet = new LineDataSet(entriesUVIndex, "UV Index");
        }
        else {
            dataSet = new LineDataSet(entriesVOC, "VOC");
        }


        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);

        chart.invalidate();

    }

    public void readDB(){

        // Read from the database
         mListener = myRef.limitToLast(200).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.e("Main Activity","on Data Changed");
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                Set<String> keys;
                Map<String,Object> dataset ;

                keys = (Set<String>) value.keySet();

                dataset = value;

                createDatasetForChart(keys , dataset);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void populateChart(){
//        As a last step, you need to add the LineDataSet object (or objects) you created to a LineData object. This object holds all data that is represented by a Chart instance and allows further styling. After creating the data object, you can set it to the chart and refresh it:

//        LineData lineData = new LineData(dataSet);
//        chart.setData(lineData);
//        chart.invalidate(); // refresh
    }


}
