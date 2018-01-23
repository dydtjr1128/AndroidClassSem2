package org.androidtown.myapplication;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    ContactsAdapter adapter;
    RecyclerView rvContacts;
    protected LocationCallback mLocationCallback;
    private static final int RC_LOCATION = 1;
    private static final int RC_LOCATION_UPDATE = 2;
    Button searchbtn;
    EditText edittext;
    String searchaddress;


    protected static final String TAG = "MainActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */


    protected Location mLastLocation;

    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        edittext = (EditText)findViewById(R.id.edittext);
        searchbtn = (Button)findViewById(R.id.btn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchaddress = edittext.getText().toString();
                if(searchaddress.equals("현재위치")){
                    getLastLocation();
                }
                else if(searchaddress.equals("위치추적")){
                    startLocationUpdate();
                }
                else {
                    toAddress(v, searchaddress);
                }
            }
        });



        rvContacts = (RecyclerView)findViewById(R.id.rvContact);
        contacts = Contact.createContactsList(2);

        adapter = new ContactsAdapter(this, contacts);

        rvContacts.setAdapter(adapter);

      /*
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvContacts.setLayoutManager(gridLayoutManager);
        */

        adapter.notifyDataSetChanged();
        // getLastLocation(mGoogleApiClient);
        // buildGoogleApiClient();
    }
    ArrayList<Contact> contacts;



    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION_UPDATE)
    public void startLocationUpdate() {
        LocationRequest locRequest = new LocationRequest();
        locRequest.setInterval(3000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //mLastLocation = locationResult.getLastLocation();
                getLastLocation();

            }
        };


        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mFusedLocationClient.requestLocationUpdates(locRequest, mLocationCallback, Looper.myLooper());
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,
                    "This app needs access to your location to know where you are.",
                    RC_LOCATION_UPDATE, perms);
        }
    }

    public void stopLocationUpdate() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
    /*
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel, mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel, mLastLocation.getLongitude()));
        } else {
            //Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }
*/
    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    public void getLastLocation(){
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)){
            mFusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override

                public void onComplete(@NonNull Task<Location> task) {
                    if(task.isSuccessful() && task.getResult() != null){
                        mLastLocation = task.getResult();
                        try {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                            List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
                            if (addresses.size() >0) {
                                Address address = addresses.get(0);
                                contacts.add(new Contact(address.getLongitude(), address.getLatitude(),
                                        address.getCountryName() + " " +address.getLocality()+ " " + address.getFeatureName()));
                                if(contacts.size()>10){
                                    adapter.removeItem(0);
                                }
                                adapter.notifyDataSetChanged();
                            }


                        } catch (IOException e) {
                            Log.e(TAG, "Failed in using Geocoder",e);
                        }


                    }
                }
            });
        }
        else{
            EasyPermissions.requestPermissions(this, "This app needs access to your location to know where you are", RC_LOCATION, perms);
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    /*
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    */

    @Override
    protected void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */


/*
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
*/

    public void toAddress(View view, String str) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            //List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
            List<Address> addresses = geocoder.getFromLocationName(str, 1); // str=지역이름
            if (addresses.size() >0) {
                Address address = addresses.get(0);
                contacts.add(new Contact(address.getLongitude(), address.getLatitude(),
                        address.getCountryName() + " " +address.getLocality()+ " " + address.getFeatureName()));
                adapter.notifyDataSetChanged();
            }


        } catch (IOException e) {
            Log.e(TAG, "Failed in using Geocoder",e);
        }
    }

    public void toAddress2(View view) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
            if (addresses.size() >0) {
                Address address = addresses.get(0);
                contacts.add(new Contact(address.getLongitude(), address.getLatitude(),
                        address.getCountryName() + " " +address.getLocality()+ " " + address.getFeatureName()));
                adapter.notifyDataSetChanged();
            }


        } catch (IOException e) {
            Log.e(TAG, "Failed in using Geocoder",e);
        }
    }
}