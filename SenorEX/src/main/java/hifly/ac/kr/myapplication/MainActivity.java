package hifly.ac.kr.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final static int MIN_TIME = 50;
    private final static int MIN_DIST = 100;

    private int shakeCount=0;
    private TextView mAccel;
    private TextView mGyro;
    private TextView mMagnet;
    private TextView mSensorList;

    SensorManager mSm;
    int key=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccel = (TextView) findViewById(R.id.tvAccelerometer);
        mGyro = (TextView) findViewById(R.id.tvGyroscope);
        mMagnet = (TextView) findViewById(R.id.tvMagnetic);
        mSensorList = (TextView) findViewById(R.id.tvSensorList);

        mSm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = mSm.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sb = new StringBuilder();
        for (Sensor s : sensors)
            sb.append(s.getName()).append(":").append(s.getType()).append("\n");
        mSensorList.setText(sb);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    @SuppressWarnings("MissingPermission")
    protected void onStart() {
        super.onStart();


        Sensor accelSensor = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroSensor = mSm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor magnetSensor = mSm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSm.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSm.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                if(event.values[1] < 9)
                    key = 1;
                if(event.values[1] > 10.5 && key == 1) {
                    shakeCount++;
                    key = -1;
                }
                if(shakeCount > 3){
                    Toast.makeText(getApplicationContext(), "3번 흔들었어요!!", Toast.LENGTH_SHORT).show();
                    shakeCount=0;
                }
                mAccel.setText("Force on x: " + event.values[0] + " on y " + event.values[1] + " on z:" + event.values[2]);
                break;
          /*  case Sensor.TYPE_GYROSCOPE:
                mGyro.setText("Rate of rotation on x: " + event.values[0] + " on y " + event.values[1] + " on z:" + event.values[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnet.setText("Strength on x: " + event.values[0] + " on y " + event.values[1] + " on z:" + event.values[2]);
                break;*/
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


}