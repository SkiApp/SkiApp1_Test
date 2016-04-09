package com.dhbw.studienarbeit.skiapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    public double latitude, longitude;
    public int m = 0;
    public String extra1, extra2;
    public static String suchAnfrage;
    public LatLng latidtudeLongitude;
    public MarkerOptions markerOptionen;
    public Bundle extras;
    public static Marker mLifte, mRestaurants, mParkplaetze, mSportgeschaefte, mBushalten;
    public ArrayList<Marker> lifteMarkers, restaurantMarkers,
            sportgeschaefteMarkers, parkplaetzeMarkers, bushaltestellenMarkers;
    public String name, lat, lng, snip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer);

            extras = getIntent().getExtras();
            if (extras != null) {
                extra1 = extras.getString("latitudefrom");
                extra2 = extras.getString("longitudefrom");
                setUpMapIfNeeded();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(Double.parseDouble(extra1), Double.parseDouble(extra2)), 12.0f), 5000, null);
            } else {
                setUpMapIfNeeded();
            }
    }



    //Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menü aufsetzen
        getMenuInflater().inflate(R.menu.actionbaractions, menu);
        //Search-View aufsetzen
        MenuItem miSuchen = menu.findItem(R.id.actionbar_search);
        SearchView svSuchen = (SearchView) MenuItemCompat.getActionView(miSuchen);
        SearchManager smSuchen = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (null != smSuchen) {
            svSuchen.setSearchableInfo(smSuchen.getSearchableInfo(getComponentName()));
        }
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            suchAnfrage = intent.getStringExtra(SearchManager.QUERY);
            if (suchAnfrage != null && !suchAnfrage.equals("")) {
                new GeocoderTask().execute(suchAnfrage);
            }
        }
        svSuchen.setIconifiedByDefault(false);
        MenuItemCompat.setOnActionExpandListener(
                miSuchen, new MenuItemCompat.OnActionExpandListener() {

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        return true;  // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true;  // Return true to expand action view
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    public void addMarkers() {

        parkplaetzeMarkers = new ArrayList<Marker>();
        String[] parkplätze = getResources().getStringArray(R.array.parkplätzeArray);
        for (int i = 0; i < parkplätze.length; i++) {
            String park = parkplätze[i];
            int j = park.indexOf(';');
            int k = park.indexOf(',');
            int l = park.indexOf(':');
            if (j >= 0) {
                name = park.substring(0, j);
                lat = park.substring(j + 1, k);
                lng = park.substring(k + 1, l);
                snip = park.substring(l + 1, park.length());
                mParkplaetze = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                        .title(name)
                        .visible(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking)));
                parkplaetzeMarkers.add(mParkplaetze);
            } else {
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG)
                        .show();
            }
        }

        restaurantMarkers = new ArrayList<Marker>();
        String[] restaurants = getResources().getStringArray(R.array.restaurantsArray);
        for (int i = 0; i < restaurants.length; i++) {
            String srestaurant = restaurants[i];
            int j = srestaurant.indexOf(';');
            int k = srestaurant.indexOf(',');
            int l = srestaurant.indexOf(':');
            if (j >= 0) {
                name = srestaurant.substring(0, j);
                lat = srestaurant.substring(j + 1, k);
                lng = srestaurant.substring(k + 1, l);
                snip = srestaurant.substring(l + 1, srestaurant.length());
                mRestaurants = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                        .title(name)
                        .visible(true)
                        .snippet(snip)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)));
                restaurantMarkers.add(mRestaurants);
            } else {
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG)
                        .show();
            }
        }

        sportgeschaefteMarkers = new ArrayList<Marker>();
        String[] sportgeschäfte = getResources().getStringArray(R.array.sportGeschäfteArray);
        for (int i = 0; i < sportgeschäfte.length; i++) {
            String ssportgeschäfte = sportgeschäfte[i];
            int j = ssportgeschäfte.indexOf(';');
            int k = ssportgeschäfte.indexOf(',');
            int l = ssportgeschäfte.indexOf(':');
            if (j >= 0) {
                name = ssportgeschäfte.substring(0, j);
                lat = ssportgeschäfte.substring(j + 1, k);
                lng = ssportgeschäfte.substring(k + 1, l);
                snip = ssportgeschäfte.substring(l + 1, ssportgeschäfte.length());
                mSportgeschaefte = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                        .title(name)
                        .visible(true)
                        .snippet(snip)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sportshop)));
                sportgeschaefteMarkers.add(mSportgeschaefte);
            } else {
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Action Bar & Menü-Button Aktionen
        switch (item.getItemId()) {

            case R.id.sos:
                Intent sos = new Intent(this, SOSAnruf.class);
                startActivity(sos);
                return true;

            case R.id.hilfe:
                Intent hilfe = new Intent(this, Hilfe.class);
                startActivity(hilfe);
                return true;

            case R.id.übersicht:
                Intent übersicht = new Intent(this, Uebersicht.class);
                startActivity(übersicht);
                return true;

            case R.id.navi:
                Intent navi = new Intent(this, SkiGebietNavigation.class);
                startActivity(navi);
                return true;

            case R.id.wetter:
                Intent wetter = new Intent(this, VorhersageWetter.class);
                startActivity(wetter);
                return true;

            case R.id.Liftstationen:
                if (item.isChecked() != true) {
                    item.setChecked(true);
                    lifteMarkers = new ArrayList<Marker>();
                    String[] lifte = getResources().getStringArray(R.array.lifteArray);
                    for (int i = 0; i < lifte.length; i++) {
                        String slifte = lifte[i];
                        int j = slifte.indexOf(';');
                        int k = slifte.indexOf(',');
                        int l = slifte.indexOf(':');
                        if (j >= 0) {
                            name = slifte.substring(0, j);
                            lat = slifte.substring(j + 1, k);
                            lng = slifte.substring(k + 1, l);
                            snip = slifte.substring(l + 1, slifte.length());
                            mLifte = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(
                                            Double.parseDouble(lat),
                                            Double.parseDouble(lng)))
                                    .title(name)
                                    .visible(true)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.lift)));
                            lifteMarkers.add(mLifte);
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Daten verfügbar",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (item.isChecked() == true) {
                    item.setChecked(false);
                    for (int i = 0; i < lifteMarkers.size(); i++) {
                        lifteMarkers.get(i).remove();
                    }
                }
                return true;

            case R.id.Bushaltestellen:
                if (item.isChecked() != true) {
                    item.setChecked(true);
                    bushaltestellenMarkers = new ArrayList<Marker>();
                    String[] bushalten = getResources().getStringArray(R.array.bushaltenArray);
                    for (int i = 0; i < bushalten.length; i++) {
                        String bus = bushalten[i];
                        int j = bus.indexOf(';');
                        int k = bus.indexOf(',');
                        int l = bus.indexOf(':');
                        if (j >= 0) {
                            name = bus.substring(0, j);
                            lat = bus.substring(j + 1, k);
                            lng = bus.substring(k + 1, l);
                            snip = bus.substring(l + 1, bus.length());
                            mBushalten = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(lat),
                                            Double.parseDouble(lng)))
                                    .title(name)
                                    .visible(true)
                                    .icon(BitmapDescriptorFactory.fromResource(
                                            R.drawable.busstop)));
                            bushaltestellenMarkers.add(mBushalten);
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Daten verfügbar",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (item.isChecked() == true) {
                    item.setChecked(false);
                    for (int i = 0; i < bushaltestellenMarkers.size(); i++) {
                        bushaltestellenMarkers.get(i).remove();
                    }
                }
                return true;

            case R.id.Parkplätze:
                if (item.isChecked() != true) {
                    item.setChecked(true);
                    parkplaetzeMarkers = new ArrayList<Marker>();
                    String[] parkplätze = getResources().getStringArray(R.array.parkplätzeArray);
                    for (int i = 0; i < parkplätze.length; i++) {
                        String park = parkplätze[i];
                        int j = park.indexOf(';');
                        int k = park.indexOf(',');
                        int l = park.indexOf(':');
                        if (j > 0) {
                            name = park.substring(0, j);
                            lat = park.substring(j + 1, k);
                            lng = park.substring(k + 1, l);
                            snip = park.substring(l + 1, park.length());
                            mParkplaetze = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(lat),
                                            Double.parseDouble(lng)))
                                    .title(name)
                                    .visible(true)
                                    .icon(BitmapDescriptorFactory.fromResource(
                                            R.drawable.parking)));
                            parkplaetzeMarkers.add(mParkplaetze);
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Daten verfügbar",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (item.isChecked() == true) {
                    item.setChecked(false);
                    for (int i = 0; i < parkplaetzeMarkers.size(); i++) {
                        parkplaetzeMarkers.get(i).remove();
                    }
                }
                return true;

            case R.id.Gaststätten:
                if (item.isChecked() != true) {
                    item.setChecked(true);
                    restaurantMarkers = new ArrayList<Marker>();
                    String[] restaurants = getResources().getStringArray(R.array.restaurantsArray);
                    for (int i = 0; i < restaurants.length; i++) {
                        String srestaurant = restaurants[i];
                        int j = srestaurant.indexOf(';');
                        int k = srestaurant.indexOf(',');
                        int l = srestaurant.indexOf(':');
                        if (j >= 0) {
                            name = srestaurant.substring(0, j);
                            lat = srestaurant.substring(j + 1, k);
                            lng = srestaurant.substring(k + 1, l);
                            snip = srestaurant.substring(l + 1, srestaurant.length());
                            mRestaurants = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(lat),
                                            Double.parseDouble(lng)))
                                    .title(name)
                                    .visible(true)
                                    .snippet(snip)
                                    .icon(BitmapDescriptorFactory.fromResource(
                                            R.drawable.restaurant)));
                            restaurantMarkers.add(mRestaurants);
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Daten verfügbar",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (item.isChecked() == true) {
                    item.setChecked(false);
                    for (int i = 0; i < restaurantMarkers.size(); i++) {
                        restaurantMarkers.get(i).remove();
                    }
                }
                return true;

            case R.id.SkiSportGeschäfte:
                if (item.isChecked() != true) {
                    item.setChecked(true);
                    sportgeschaefteMarkers = new ArrayList<Marker>();
                    String[] sportgeschäfte = getResources().getStringArray(
                            R.array.sportGeschäfteArray);
                    for (int i = 0; i < sportgeschäfte.length; i++) {
                        String ssportgeschäfte = sportgeschäfte[i];
                        int j = ssportgeschäfte.indexOf(';');
                        int k = ssportgeschäfte.indexOf(',');
                        int l = ssportgeschäfte.indexOf(':');
                        if (j >= 0) {
                            name = ssportgeschäfte.substring(0, j);
                            lat = ssportgeschäfte.substring(j + 1, k);
                            lng = ssportgeschäfte.substring(k + 1, l);
                            snip = ssportgeschäfte.substring(l + 1, ssportgeschäfte.length());
                            mSportgeschaefte = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(lat),
                                            Double.parseDouble(lng)))
                                    .title(name)
                                    .visible(true)
                                    .snippet(snip)
                                    .icon(BitmapDescriptorFactory.fromResource(
                                            R.drawable.sportshop)));
                            sportgeschaefteMarkers.add(mSportgeschaefte);
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Daten verfügbar",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (item.isChecked() == true) {
                    item.setChecked(false);
                    for (int i = 0; i < sportgeschaefteMarkers.size(); i++) {
                        sportgeschaefteMarkers.get(i).remove();
                    }
                }
                return true;
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                addMarkers();
            }
        }
    }

    private void setUpMap() {
        try {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);

                //Referenz System Location Manager
                LocationManager lm = (LocationManager) this.getSystemService(
                        Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {

                    public void onLocationChanged(Location location) {
                        Toast.makeText(getApplicationContext(),
                                "Ihr Standort wird geladen",
                                Toast.LENGTH_SHORT).show();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        if (extras == null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                                    longitude), 12.0f));
                        }
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                        Toast.makeText(getApplicationContext(),
                                "Ihr Standortservice ist aktiviert",
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onProviderDisabled(String provider) {
                        Toast.makeText(getApplicationContext(),
                                "Ihr Standortservice ist deaktiviert",
                                Toast.LENGTH_SHORT).show();
                    }
                };
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 100,
                        locationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 100,
                        locationListener);
            }
        } catch (NullPointerException exception) {
            Log.e("mapAppError", exception.toString());
        }
    }

    //Suchanfrage von Orten
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {
        //Per AsyncTask auf Geocoding WebService zugreifen
        @Override
        protected List<Address> doInBackground(String... locationName) {

            //Neue Geocoder Klasse mit Adressen Liste
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            //Maximal 3 Ergebnisse bekommen
            try {
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getApplicationContext(), "Ihre Suchanfrage liefert keine Ergebnisse",
                        Toast.LENGTH_SHORT).show();
            }

            //Neue Marker für Suchanfrage sowie neue Positionierung
            for (int i = 0; i < addresses.size(); i++) {

                Address address = addresses.get(i);

                //Neuen Geopunkt erstellen (Koordinaten)
                latidtudeLongitude = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptionen = new MarkerOptions();
                markerOptionen.position(latidtudeLongitude);
                markerOptionen.title(addressText);
                mMap.addMarker(markerOptionen);
                //Auf ersten Treffer positionieren

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latidtudeLongitude, 12.0f));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latidtudeLongitude));
            }
        }
    }
    //###########################################################################################################################################################################################################################################
}
