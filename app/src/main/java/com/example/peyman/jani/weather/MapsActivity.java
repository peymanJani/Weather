package com.example.peyman.jani.weather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peyman.jani.weather.Samples.MainJason;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private Location location;
    List<LatLng> ll;
    List<Address> addresses;
    private SupportMapFragment mapFragment;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ShowMiniFragment showMiniFragment= new ShowMiniFragment();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        if(Geocoder.isPresent()){
            try {
                Intent intent = getIntent();
                String u=intent.getStringExtra("city");
                String location = "u";
                Geocoder gc = new Geocoder(this);
                 addresses= gc.getFromLocationName(u, 1); // get the found Address Objects
            } catch (IOException e) {
                // handle the exception
            }
        }



       // Toast.makeText(this, addresses.get(0).getLatitude()+"", Toast.LENGTH_LONG).show();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //final String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(SELECT%20woeid%20FROM%20geo.places%20WHERE%20text%3D%22("+addresses.get(0).getLatitude()+"%2C"+(addresses.get(0).getLongitude()+")%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        LatLng sydney = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
        mMap.setTrafficEnabled(true);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                double latitude = mMap.getCameraPosition().target.latitude;
                double longitude = mMap.getCameraPosition().target.longitude;
                String url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(SELECT%20woeid%20FROM%20geo.places%20WHERE%20text%3D%22("+longitude+"%2C"+latitude+")%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(MapsActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Gson gson = new Gson();
                        MainJason mainJason= gson.fromJson(responseString, MainJason.class);
                        String temp=mainJason.getQuery().getResults().getChannel().getItem().getCondition().getTemp().toString();
                        String statust=mainJason.getQuery().getResults().getChannel().getItem().getCondition().getText().toString();
                        temp=convetDegree(temp)+"";
                        //Toast.makeText(MapsActivity.this, s, Toast.LENGTH_SHORT).show();
                        //ShowMiniFragment showMiniFragment=new ShowMiniFragment();
                        linearLayout=(LinearLayout)findViewById(R.id.show_mini_fragment);
                        TextView textView =(TextView)findViewById(R.id.temp_mini);
                        TextView main_status =(TextView)findViewById(R.id.textStatus);
                        ImageView main_status_image=(ImageView)findViewById(R.id.Image_status);
                        //textStatus.setText(statust);
                        textView.setText(temp+"");
                        //linearLayout.setVisibility(View.GONE);
                        //Toast.makeText(MapsActivity.this, temp+"", Toast.LENGTH_SHORT).show();

                        switch (statust){
                            case "Mostly Cloudy":
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.cloudy_mostly_cloudy));
                                main_status.setText("احتمالا ابری");
                                break;
                            case "Cloudy":
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.cloudy_mostly_cloudy));
                                main_status.setText("ابری");
                                break;
                            case "Sunny":
                                main_status.setText("آفتابی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                                break;
                            case "Clear":
                                main_status.setText("آفتابی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                                break;
                            case "Breezy":
                                main_status.setText("آفتابی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                                break;
                            case "Partly Cloudy":
                                main_status.setText("نیمه ابری");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.partly_cloudy));
                                break;
                            case "Rain":
                                main_status.setText("بارانی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.rain));
                                break;
                            case "Scattered Showers":
                                main_status.setText("رعد و برق پراکنده");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.scattered_showers));
                                break;
                            case "windy":
                                main_status.setText("بادی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.windy_burned));
                                break;
                            case "Mostly Sunny":
                                main_status.setText("نیمه افتابی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.partly_cloudy));
                                break;
                            default:
                                main_status.setText("افتابی");
                                main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));

                        }
                    }
                });
            }

        });

        // Add a marker in Sydney and move the camera



    }
    private int convetDegree(String f) {
        int m =(int)Integer.parseInt(f);
        int c = (int) Math.floor((m-32)/1.8);
        return c;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
