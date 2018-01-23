package org.androidtown.myapplication;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private final int MY_PERMISSION = 1001;
    private Button searchBtn;
    private EditText geoEditText;
    private MyRecyclerAdapter recyclerAdapter;
    private Geocoder geocoder = new Geocoder(this, Locale.KOREA);
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private LocationCallback locationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geoEditText = (EditText)findViewById(R.id.geoEditText);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geo = geoEditText.getText().toString();
                if(geo.equals("현재위치")){
                    toLastLocation();
                }
                else if(geo.equals("위치추적")){
                    toUpdateLocation();
                }
                else{
                    toAddress(geo);
                }

            }
        });
        RecyclerView recyclerView  = (RecyclerView)findViewById(R.id.geoRecyclerView);
        recyclerAdapter = new MyRecyclerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        getLastLocation();
    }
    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(MY_PERMISSION)
    public void getLastLocation(){
        String perms[] = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if(task.isSuccessful() & task.getResult() != null){
                        location = task.getResult();
                    }
                }
            });
        }
        else{
            EasyPermissions.requestPermissions(this,"We need Location Access", MY_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    public void  toLastLocation(){
        try {
            List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
            if(list.size()>0){
                for(int i=0; i<list.size(); i++) {
                    Address address = list.get(i);
                    recyclerAdapter.putItem(new MyRecyclerItem(address.getLatitude(),address.getLongitude(), address.getCountryName() + " " +address.getLocality()  + " " + address.getFeatureName() + " " + address.getPostalCode()));
                }
            }
            if(recyclerAdapter.getSize() > 10){
                recyclerAdapter.removeItem(0);
            }
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }
    @SuppressWarnings("MissingPermission")
    public void toUpdateLocation(){
        LocationRequest request = new LocationRequest();
        request.setInterval(5000);
        request.setFastestInterval(4000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                location = locationResult.getLastLocation();
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(request,locationCallback, Looper.myLooper());
    }
    public void toAddress(String geo){
        try {
            List<Address> list = geocoder.getFromLocationName(geo, 10);
            if(list.size()>0){
                for(int i=0; i<list.size(); i++) {
                    Address address = list.get(i);
                    recyclerAdapter.putItem(new MyRecyclerItem(address.getLatitude(),address.getLongitude(), address.getCountryName() + " " +address.getLocality()+ " " + address.getThoroughfare() + " " + address.getFeatureName()));
                }
            }
            if(recyclerAdapter.getSize() > 10){
                recyclerAdapter.removeItem(0);
            }
        }
        catch (Exception e){

        }
    }
}
