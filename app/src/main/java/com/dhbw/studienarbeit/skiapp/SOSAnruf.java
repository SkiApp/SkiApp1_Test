package com.dhbw.studienarbeit.skiapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SOSAnruf extends GestureHandler {

    public static double latitude;
    public static double longitude;
    public static double last_known_lng;
    public static double last_known_lat;
    public static double mlat00,mlat10,mlat01,mlat11,mlng00,mlng10,mlng01,mlng11;
    public static double slat00,slng00,slat10,slng10,slat01,slng01,slat11,slng11;
    public AktuelleLocation aktLoc;
    public LocationManager locMan;
    public TextView tvLat, tvLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosanruf);

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);

        locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        aktLoc = new AktuelleLocation();
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, aktLoc);

        getGPS();
        if (latitude != 0.0 && longitude != 0.0) {
            last_known_lat = latitude;
            last_known_lng = longitude;
        }
        tvLat = (TextView)findViewById(R.id.tvLat);
        tvLng = (TextView)findViewById(R.id.tvLong);
        tvLat.setText(String.valueOf(last_known_lat));
        tvLng.setText(String.valueOf(last_known_lng));
    }

    public void OnClick(View v) {

        mlat00 = 47.351296;
        mlng00 = 9.839708;
        mlat10 = 47.259804;
        mlng10 = 9.847605;
        mlat01 = 47.351296;
        mlng01 = 9.917299;
        mlat11 = 47.260037;
        mlng11 = 9.932062;
        slat00 = 46.983076;
        slng00 = 10.911459;
        slat10 = 46.911123;
        slng10 = 10.914549;
        slat01 = 46.985184;
        slng01 = 11.024755;
        slat11 = 46.912999;
        slng11 = 11.024069;

        if ((((mlat00 >= last_known_lat) && (last_known_lat >= mlat10)) ||
                ((mlat01 >= last_known_lat) && (last_known_lat >= mlat11))) &&
                (((mlng00 <= last_known_lng) && (last_known_lng <= mlng01)) ||
                        ((mlng10 <= last_known_lng) && (last_known_lng <= mlng11)))) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+43551820048"));
            startActivity(callIntent);
        }else if((((slat00 >= last_known_lat) && (last_known_lat >= slat10)) ||
                ((slat01 >= last_known_lat) && (last_known_lat >= slat11))) &&
                (((slng00 <= last_known_lng) && (last_known_lng <= slng01)) ||
                        ((slng10 <= last_known_lng) && (last_known_lng <= slng11)))){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:144"));
            startActivity(callIntent);
    }else{
            Toast.makeText(getApplication(), "Kein registriertes Gebiet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void Zurück(View view) {
        Intent toMaps = new Intent(this, MainMapsActivity.class);
        startActivity(toMaps);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public class AktuelleLocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }

    public void getGPS() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;

        for (int i = providers.size() - 1; i >= 0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }
        double[] gps = new double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        last_known_lat = gps[0];
        last_known_lng = gps[1];
    }
}
