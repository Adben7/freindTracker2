package com.example.tracker_1;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivityFriend extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String id_user ;

    int    id ;
    String name2  ;
    String name1  ;
    String email ;
    String username ;
    String password ;
    double atitude1 ;
    double longtitude1 ;
    double atitude2  ;
    double longtitude2 ;
    String id_cercle ;
    String issharing ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_friend);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent in = getIntent();
        id_user = in.getStringExtra("id_user");

        name1 =  in.getStringExtra("name");
        atitude1 =   in.getDoubleExtra("atitude",0);
        longtitude1 = in.getDoubleExtra("longtitude",0);

        Toast.makeText(this,"id_user : "+id_user,Toast.LENGTH_LONG).show();

        ConnectionDB conndb = new ConnectionDB(this);
        conndb.execute("getUserPerId",id_user);

        String s = "";
        try { s = conndb.get(); }
        catch (Exception e) {e.printStackTrace();}

        try {
            JSONObject obj = new JSONObject(s);
            JSONArray users = obj.getJSONArray("users");
            JSONObject jsonobject = users.getJSONObject(0);

            id = jsonobject.getInt("id");
            name2 = jsonobject.getString("name");
            username = jsonobject.getString("username");
            email = jsonobject.getString("email");
            atitude2 = jsonobject.getDouble("atitude");
            longtitude2 = jsonobject.getDouble("longtitude");
            id_cercle = jsonobject.getString("id_cercle");
            issharing = jsonobject.getString("issharing");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng2 = new LatLng(longtitude2,atitude2);
        markerOptions.position(latLng2);
        markerOptions.title(name2);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 15f));

        LatLng latLng1 = new LatLng(longtitude1,atitude1);
        markerOptions.position(latLng1);
        markerOptions.title(name1);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker((float) 240.0));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15f));
    }
}
