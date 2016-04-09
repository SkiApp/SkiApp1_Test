//Activity der einzelnen Detailinformationen zu jedem Tag

package com.dhbw.studienarbeit.skiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class WetterDetailActivity extends ActionBarActivity {

    public static Bundle extras;
    public String swipe = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Up-Buttons in der Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_vorhersage_wetter);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WetterdetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static class WetterdetailFragment extends Fragment {

        public WetterdetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_wetter_detail, container, false);

            // Die WetterdetailActivity wurde ueber einen Intent aufgerufen
            // Wir lesen aus dem empfangenen Intent die uebermittelten Daten aus
            Intent empfangenerIntent = getActivity().getIntent();
            extras=empfangenerIntent.getExtras();
            String wStaerke=extras.getString("wStaerke");
            String wRichtung=extras.getString("wRichtung");
            String sAufgang=extras.getString("sAufgang");
            //Umrechnen der Weltzeit auf Mitteleuropäische Zeit
            Integer mezA=99;
            try {
                mezA = Integer.parseInt(sAufgang.substring(11, 13));
            } catch(NumberFormatException e) {
                System.out.println("parse value is not valid : " + e);
            }
            mezA=mezA+1;
            String nZeitA= String.valueOf(mezA);
            sAufgang="0"+nZeitA+sAufgang.substring(13,sAufgang.length()-3)+" Uhr (MEZ)";
            String sUntergang=extras.getString("sUntergang");
            //Umrechnen der Weltzeit auf Mitteleuropäische Zeit
            Integer mezU=99;
            try {
                mezU = Integer.parseInt(sUntergang.substring(11, 13));
            } catch(NumberFormatException e) {
                System.out.println("parse value is not valid : " + e);
            }
            mezU=mezU+1;
            String nZeitU= String.valueOf(mezU);
            sUntergang=nZeitU+sUntergang.substring(13,sUntergang.length()-3)+" Uhr (MEZ)";
            String aBild="wi"+extras.getString("aIcon");
            String aTemp=extras.getString("aTemp");

            ((TextView) rootView.findViewById(R.id.tWindrichtung)).setText(wRichtung);
            ((TextView) rootView.findViewById(R.id.tWindstaerke)).setText(wStaerke+" m/s");
            ((TextView) rootView.findViewById(R.id.tSonnenaufgang)).setText(sAufgang);
            ((TextView) rootView.findViewById(R.id.tSonnenuntergang)).setText(sUntergang);
            ((TextView) rootView.findViewById(R.id.tTemp)).setText(aTemp);
            ((ImageView) rootView.findViewById(R.id.iIcon)).setImageResource(
                    getActivity().getResources().getIdentifier(
                            aBild, "drawable","com.dhbw.studienarbeit.skiapp"));
            return rootView;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if((e1.getX() - e2.getX()) > sensitvity){
                swipe += "Swipe Left\n";
                onSwipeLeft();
            }else if((e2.getX() - e1.getX()) > sensitvity){
                swipe += "Swipe Right\n";
                onSwipeRight();
            }else{
                swipe += "\n";
            }
            if((e1.getY() - e2.getY()) > sensitvity){
                swipe += "Swipe Up\n";
                onSwipeUp();
            }else if((e2.getY() - e1.getY()) > sensitvity){
                swipe += "Swipe Down\n";
                onSwipeDown();
            }else{
                swipe += "\n";
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);

    public void onSwipeRight() {
        Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
        Intent naviDrawer = new Intent(WetterDetailActivity.this, NavigationMenu.class);
        startActivity(naviDrawer);
    }

    public void onSwipeLeft() {
        Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
        Intent sosCall = new Intent(WetterDetailActivity.this, SOSAnruf.class);
        startActivity(sosCall);
    }

    public void onSwipeUp() {
        return;
    }

    public void onSwipeDown() {
        return;
    }
}
