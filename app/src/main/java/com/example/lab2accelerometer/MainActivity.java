package com.example.lab2accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView myTextView1;//acceleration on X-axis
    TextView myTextView2;//acceleration on Y-axis
    TextView myTextView3;//acceleration on Z-axis
    TextView myTextView4;//whether device is stationary
    SensorManager mySensorManager;
    float accX=0,accY=0,accZ=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView1=findViewById(R.id.myTextView1);
        myTextView2=findViewById(R.id.myTextView2);
        myTextView3=findViewById(R.id.myTextView3);
        myTextView4=findViewById(R.id.myTextView4);
        mySensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    }
    private SensorListener mySensorLinstener=new SensorListener() {
        @Override
        public void onSensorChanged(int sensor, float[] val) {
            if (sensor == SensorManager.SENSOR_ACCELEROMETER) {//only for accelerometer
                myTextView1.setText("acceleration on x-axis direction is: " + val[0]);
                myTextView2.setText("acceleration on y-axis direction is: " + val[1]);
                myTextView3.setText("acceleration on z-axis direction is: " + val[2]);
            }
            double eps = 0.15;//measurement accuracy.
            if (Math.abs(val[0] - accX) >= eps ||
                    Math.abs(val[1] - accY) >= eps ||
                    Math.abs(val[2] - accZ) >= eps) {
                myTextView4.setText("whether the device is stationary: yes");
            } else myTextView4.setText("whether the device is stationary: no");
            accX=val[0];
            accY=val[1];
            accZ=val[2];
        }
        @Override
        public void onAccuracyChanged(int sensor, int acc) {}
    };
    @Override
    protected void onResume(){
        mySensorManager.registerListener(
                mySensorLinstener,//listener obj
                SensorManager.SENSOR_ACCELEROMETER,//accelerometer only
                SensorManager.SENSOR_DELAY_UI//freq that sensor pass events.
        );
        super.onResume();
    }
    @Override
    protected void onPause(){
        mySensorManager.unregisterListener(mySensorLinstener);
        super.onPause();
    }
}