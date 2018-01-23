package org.androidtown.myapplication;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private final int MY_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment supportMapFragment;
    private GoogleMap gmap;
    private Location location;
    private EditText searchEditText;
    private TextView locationTextView;
    private Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationTextView = (TextView)findViewById(R.id.locationTextView);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        Button refreshBtn = (Button) findViewById(R.id.refreshBtn);
        geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchEditText.getText().toString();


                if(text != null && !text.equals("")) {
                    try {
                        StringBuilder builder = new StringBuilder();
                        List<Address> list = geocoder.getFromLocationName(text, 10);
                        for(int i=0; i<list.size(); i++) {
                            Address address = list.get(i);
                            builder.append("location : " + address.getCountryName() + " " + address.getLocality() + " " + address.getFeatureName() + " " + address.getPostalCode() + "\n");
                            //locationTextView.setText("location : " + address.getCountryName() + " " + address.getLocality() + " " + address.getFeatureName() + " " + address.getPostalCode());
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            //gmap.addMarker(new MarkerOptions().position(latLng).title(text));
                            if(i==0)
                                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }
                        locationTextView.setText(builder.toString());
                    }
                    catch (Exception e){
                        e.getStackTrace();
                    }
                }
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
      /*  LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
//        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getAltitude(), location.getLongitude()), 15));
        gmap.setOnMarkerClickListener(this);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(MY_REQUEST_CODE)
    public void checkPermission(){
        String perms[] = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this,perms)){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            location = task.getResult();
                            Log.i("kkkkk",location.getLatitude()+ " " + location.getLongitude());
                            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                            
                            try {
                                List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                Address address = list.get(0);
                                locationTextView.setText("location : " + address.getCountryName() + " " +address.getLocality()  + " " + address.getFeatureName() + " " + address.getPostalCode());
                            }catch (Exception e){
                                e.getStackTrace();
                            }

                        }
                    }
                });
        }
        else{
            EasyPermissions.requestPermissions(this, "Need Location Permission", MY_REQUEST_CODE, perms);
        }
    }
}
