package com.dhbw.studienarbeit.skiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class VorhersageWetter extends ActionBarActivity {

    public String swipe = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vorhersage_wetter);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new VorhersageWetterFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_HOME |
                        ActionBar.DISPLAY_USE_LOGO |
                        ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
        Intent naviDrawer = new Intent(VorhersageWetter.this, NavigationMenu.class);
        startActivity(naviDrawer);
    }

    public void onSwipeLeft() {
        Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
        Intent sosCall = new Intent(VorhersageWetter.this, SOSAnruf.class);
        startActivity(sosCall);
    }

    public void onSwipeUp() {
        return;
    }

    public void onSwipeDown() {
        return;
    }
}
