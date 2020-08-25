package com.example.accelermeterrecord;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.accelermeterrecord.R.id.chronometer;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Button btnView;
    SensorManager sensorManager;
    Sensor accelerometer;
    private Chronometer chronometer;
    private long pauseOffset;
    boolean running = false;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btnView = (Button) findViewById(R.id.viewentry);
        myDB = new DatabaseHelper(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ViewListContents.class);
                startActivity(intent);
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void startActivity(View v){
        if (!running) {
            running = true;
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
        }
    }

    public void pauseActivity(View v){
        if (running){
            running = false;
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        }
    }

    public void resetActivity(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        myDB.deleteData();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            if (running) {
                String newEntry_time = SystemClock.elapsedRealtime() + "";
                String newEntry_x = String.valueOf(sensorEvent.values[0]);
                String newEntry_y = String.valueOf(sensorEvent.values[1]);
                String newEntry_z = String.valueOf(sensorEvent.values[2]);

                AddData(newEntry_time, newEntry_x, newEntry_y, newEntry_z);
            }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void AddData(String newEntry_time, String newEntry_x, String newEntry_y, String newEntry_z) {

        myDB.addData(newEntry_time, newEntry_x, newEntry_y, newEntry_z);
    }

}

