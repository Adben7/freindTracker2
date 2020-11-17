package com.example.tracker_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    int id;
    String name;
    String email;
    String username;
    String password;
    double atitude = 0.0   ;
    double longtitude = 0.0;
    String id_cercle;
    String issharing;
    Context context;

    Intent intent1;



    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;


    int PERMISSION_ID = 1;

    private FusedLocationProviderClient fusedLocationClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDeviceLocation();

    }


    public void anmelden(View view) {
        Intent in = new Intent(this, RegisterActivity.class);
        startActivity(in);
    }
    public void einloggen(View view) {

        initViews();
        ConnectionDB conndb = new ConnectionDB(this);

        if (validate() == true) {
            conndb.execute("getUser", username, password);
            String s = "";
            try { s = conndb.get(); }
            catch (Exception e) {e.printStackTrace();}
            if (s.equals("false")) {
                Toast.makeText(this, "Username or Password not Correct !!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Toast.makeText(this, " Login Success ", Toast.LENGTH_LONG).show();
                    getDeviceLocation();
                    JSONObject obj = new JSONObject(s);
                    JSONArray users = obj.getJSONArray("users");
                    JSONObject jsonobject = users.getJSONObject(0);

                    id = jsonobject.getInt("id");
                    name = jsonobject.getString("name");
                    email = jsonobject.getString("email");
                    id_cercle = jsonobject.getString("id_cercle");
                    issharing = jsonobject.getString("issharing");

                    Intent in = new Intent(this, ProfileActivity.class);

                    in.putExtra("username", username);
                    in.putExtra("email", email);
                    in.putExtra("pass", password);
                    in.putExtra("name", name);
                    in.putExtra("id_cercle", id_cercle);
                    in.putExtra("id", id);
                    in.putExtra("issharing", issharing);

                    setUserLocation();

                    in.putExtra("longtitude", longtitude);
                    in.putExtra("atitude", atitude);

                    startActivity(in);

                } catch (JSONException e) {
                    e.printStackTrace();
            }}
        }
    }



    public boolean validate() {

        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();

        boolean valid = true;


        if (username.isEmpty() || password.isEmpty()) {
            //textInputLayoutPassword.setError("Please enter valid password!");
            Toast.makeText(this, "Please enter valid Email und password!", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }


    private void initViews() {
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }



    private Boolean mLocationPermissionsGranted = false;



    private FusedLocationProviderClient mFusedLocationProviderClient;
    public void getDeviceLocation(){
        if (checkPermissions()) {
                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                try {
                    final Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(Task task) {
                            if (task.isSuccessful()) {
                                Location currentLocation = (Location) task.getResult();

                                atitude = currentLocation.getLongitude();
                                longtitude = currentLocation.getLatitude();
                            } else {
                                Toast.makeText(MainActivity.this, " Der aktuelle Standort kann nicht abgerufen werden ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (SecurityException e) {
                }

    }
    else {
        requestPermissions();}
    }


    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }


    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }


    public void setUserLocation(){
        ConnectionDB conndb = new ConnectionDB(this);
        conndb.execute("setUserLocation",Integer.toString(id),Double.toString(atitude),Double.toString(longtitude));

    }

}