package com.dhbw.studienarbeit.skiapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Uebersicht extends GestureHandler {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public String name, lat, lng, snip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_uebersicht);
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.exp_uebersicht_list);

        // preparing list data
        prepareListData();

        listAdapter = new com.dhbw.studienarbeit.skiapp.ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Lifte");
        listDataHeader.add("Parktplätze");
        listDataHeader.add("Sportgeschäfte");
        listDataHeader.add("Restaurants / Hütten");

        // Adding child data
        List<String> Lifte = new ArrayList<String>();
        String[] lifte =getResources().getStringArray(R.array.lifteArray);
        for (int i = 0; i < lifte.length; i++) {
            String slifte = lifte[i];
            int j = slifte.indexOf(';');
            int k = slifte.indexOf(',');
            int l = slifte.indexOf(':');
            if (j >= 0) {
                name = slifte.substring(0, j);
                lat = slifte.substring(j+1,k);
                lng = slifte.substring(k+1,l);
                snip = slifte.substring(l+1, slifte.length());
            }else{
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG).show();
            }
            Lifte.add(name);
        }

        List<String> Parkplätze = new ArrayList<String>();
        String[] parkplätze =getResources().getStringArray(R.array.parkplätzeArray);
        for (int i = 0; i < parkplätze.length; i++) {
            String spark = parkplätze[i];
            int j = spark.indexOf(';');
            int k = spark.indexOf(',');
            int l = spark.indexOf(':');
            if (j >= 0) {
                name = spark.substring(0, j);
                lat = spark.substring(j+1,k);
                lng = spark.substring(k+1,l);
                snip = spark.substring(l+1, spark.length());
            }else{
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG).show();
            }
            Parkplätze.add(name);
        }

        List<String> Sportgeschäfte = new ArrayList<String>();
        String[] sportgeschäfte =getResources().getStringArray(R.array.sportGeschäfteArray);
        for (int i = 0; i < sportgeschäfte.length; i++) {
            String ssportgeschaefte = parkplätze[i];
            int j = ssportgeschaefte.indexOf(';');
            int k = ssportgeschaefte.indexOf(',');
            int l = ssportgeschaefte.indexOf(':');
            if (j >= 0) {
                name = ssportgeschaefte.substring(0, j);
                lat = ssportgeschaefte.substring(j+1,k);
                lng = ssportgeschaefte.substring(k+1,l);
                snip = ssportgeschaefte.substring(l+1, ssportgeschaefte.length());
            }else{
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG).show();
            }
            Sportgeschäfte.add(name);
        }

        List<String> Restaurants = new ArrayList<String>();
        String[] restaurants =getResources().getStringArray(R.array.restaurantsArray);
        for (int i = 0; i < restaurants.length; i++) {
            String srestaurants = restaurants[i];
            int j = srestaurants.indexOf(';');
            int k = srestaurants.indexOf(',');
            int l = srestaurants.indexOf(':');
            if (j >= 0) {
                name = srestaurants.substring(0, j);
                lat = srestaurants.substring(j+1,k);
                lng = srestaurants.substring(k+1,l);
                snip = srestaurants.substring(l+1, srestaurants.length());
            }else{
                Toast.makeText(getApplicationContext(), "Keine Daten verfügbar", Toast.LENGTH_LONG).show();
            }
            Restaurants.add(name);
        }
        listDataChild.put(listDataHeader.get(0), Lifte); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Parkplätze);
        listDataChild.put(listDataHeader.get(2), Sportgeschäfte);
        listDataChild.put(listDataHeader.get(3), Restaurants);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}

